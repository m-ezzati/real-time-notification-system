package com.maryam.notificationSystem.stream;

import com.maryam.notificationSystem.config.NotificationSysProperties;
import com.maryam.notificationSystem.constants.KafkaTopics;
import com.maryam.notificationSystem.dto.RuleContext;
import com.maryam.notificationSystem.kafka.event.NotificationEvent;
import com.maryam.notificationSystem.kafka.event.PriceEvent;
import com.maryam.notificationSystem.kafka.event.RuleEvent;
import com.maryam.notificationSystem.entity.enums.NotificationStatus;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.state.WindowStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDateTime;

/**
 * @author M.Ezati
 * 06/05/2026
 */
@Component
public class PriceRuleProcessor {
    private static final Logger log = LoggerFactory.getLogger(PriceRuleProcessor.class);
    private final int windowMinute;
    private final String priceStoreName;

    public PriceRuleProcessor(KStream<String, PriceEvent> priceStream, KTable<String, RuleEvent> ruleTable, NotificationSysProperties properties) {
        windowMinute = properties.getWindowMinute();
        priceStoreName = properties.getKafka().getStreams().getPriceStoreName();
        KStream<String, NotificationEvent> notificationStream = makeNotificationStream(priceStream, ruleTable);
        snedNotificationStream(notificationStream);
    }

    private KStream<String, NotificationEvent> makeNotificationStream(KStream<String, PriceEvent> priceStream, KTable<String, RuleEvent> ruleTable) {
        JsonSerde<PriceWindowAgg> windowSerde = new JsonSerde<>(PriceWindowAgg.class);
        KStream<String, PriceWindowAgg> windowedPriceStream = makeWindowedPriceStream(priceStream, windowSerde);
        return windowedPriceStream.leftJoin(ruleTable, this::processRule)
                .filter((k, v) -> v != null)
                .peek((k, v) -> log.info("NOTIFICATION -> {}", v));
    }

    private KStream<String, PriceWindowAgg> makeWindowedPriceStream(KStream<String, PriceEvent> priceStream, JsonSerde<PriceWindowAgg> windowSerde) {
        KTable<Windowed<String>, PriceWindowAgg> priceWindowTable = makePriceWindowTable(priceStream, windowSerde);
        return priceWindowTable
                .toStream()
                .map((windowedKey, agg) -> KeyValue.pair(windowedKey.key(), agg))
                .through(KafkaTopics.TEMP_PRICE_AGG_TOPIC, Produced.with(Serdes.String(), windowSerde));
    }

    private KTable<Windowed<String>, PriceWindowAgg> makePriceWindowTable(KStream<String, PriceEvent> priceStream, JsonSerde<PriceWindowAgg> windowSerde) {
        return priceStream
                .groupByKey()
                .windowedBy(TimeWindows.ofSizeWithNoGrace(Duration.ofMinutes(windowMinute)))
                .aggregate(
                        PriceWindowAgg::new,
                        (key, newPrice, agg) -> {
                            if (agg.getFirstPrice() == null) {
                                agg.setFirstPrice(newPrice.getPrice());
                            }
                            agg.setLastPrice(newPrice.getPrice());
                            agg.setLastTimestamp(newPrice.getEventTimestamp());
                            return agg;
                        },
                        Materialized.<String, PriceWindowAgg, WindowStore<Bytes, byte[]>>as(priceStoreName)
                                .withKeySerde(Serdes.String())
                                .withValueSerde(windowSerde)
                );
    }

    private void snedNotificationStream(KStream<String, NotificationEvent> notificationStream) {
        notificationStream.to(
                KafkaTopics.NOTIFICATION_TOPIC,
                Produced.with(Serdes.String(), new JsonSerde<>(NotificationEvent.class))
        );
    }

    private NotificationEvent processRule(PriceWindowAgg priceAgg, RuleEvent ruleEvent) {
        if (!isValid(priceAgg, ruleEvent)) {
            return null;
        }
        BigDecimal current = priceAgg.getLastPrice();
        BigDecimal first = priceAgg.getFirstPrice();

        boolean thresholdHit = isThresholdHit(ruleEvent, current);
        boolean percentageHit = isPercentageHit(ruleEvent, first, current);

        if (!thresholdHit && !percentageHit) {
            return null;
        }
        RuleContext context = new RuleContext(ruleEvent, first, current, thresholdHit, percentageHit);

        return buildNotification(context);
    }

    private boolean isValid(PriceWindowAgg priceAgg, RuleEvent ruleEvent) {
        if (priceAgg == null || ruleEvent == null) {
            return false;
        }
        if (ruleEvent.getExpirationTime() != null && ruleEvent.getExpirationTime().isBefore(LocalDateTime.now())) {
            return false;
        }
        return priceAgg.getLastPrice() != null && priceAgg.getFirstPrice() != null;
    }

    private boolean isThresholdHit(RuleEvent ruleEvent, BigDecimal currentPrice) {
        return ruleEvent.getThresholdValue() != null &&
                currentPrice.compareTo(ruleEvent.getThresholdValue()) > 0;
    }

    private boolean isPercentageHit(RuleEvent ruleEvent, BigDecimal firstPrice, BigDecimal currentPrice) {
        if (ruleEvent.getPercentageValue() == null) {
            return false;
        }
        BigDecimal percentChange = calculatePercentChange(firstPrice, currentPrice).abs();

        return percentChange.compareTo(BigDecimal.valueOf(ruleEvent.getPercentageValue())) >= 0;
    }

    private NotificationEvent buildNotification(RuleContext ruleContext) {
        String message = String.valueOf(msgBuilder(ruleContext));

        return new NotificationEvent(
                ruleContext.ruleEvent().getRuleId(),
                ruleContext.ruleEvent().getSymbol(),
                ruleContext.ruleEvent().getEmail(),
                ruleContext.ruleEvent().getPhoneNumber(),
                message,
                false,
                NotificationStatus.PENDING,
                ruleContext.ruleEvent().getNotificationType()
        );
    }

    private StringBuilder msgBuilder(RuleContext ruleContext) {
        StringBuilder msg = new StringBuilder("Rule triggered for symbol=" + ruleContext.ruleEvent().getSymbol());

        if (ruleContext.thresholdHit()) {
            msg.append(" | Threshold reached: price=")
                    .append(ruleContext.currentPrice())
                    .append(" >= ")
                    .append(ruleContext.ruleEvent().getThresholdValue());
        }
        if (ruleContext.percentageHit()) {
            msg.append(" | Percentage change reached: ")
                    .append(String.format("%.2f",
                            calculatePercentChange(ruleContext.firstPrice(), ruleContext.currentPrice())))
                    .append("% (required=")
                    .append(ruleContext.ruleEvent().getPercentageValue())
                    .append("%)");
        }
        return msg;
    }

    private BigDecimal calculatePercentChange(BigDecimal firstPrice, BigDecimal currentPrice) {
        return currentPrice.subtract(firstPrice)
                .divide(firstPrice, 4, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100));
    }
}
