package com.saber.testHibernate.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.saber.testHibernate.config.PriceSimulatorProperties;
import com.saber.testHibernate.constants.KafkaTopics;
import com.saber.testHibernate.dto.PriceEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @author M.Ezati
 * 06/05/2026
 */
@Service
public class PriceSimulatorService {
    private static final String PRICE_TOPIC = KafkaTopics.PRICE_TOPIC;
    private static final Logger log = LoggerFactory.getLogger(PriceSimulatorService.class);
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    private final Random random = new Random();
    private final Map<String, BigDecimal> lastPrices;
    private final List<String> symbols;


    public PriceSimulatorService(KafkaTemplate<String, String> kafkaTemplate, PriceSimulatorProperties properties) {
        this.kafkaTemplate = kafkaTemplate;
        this.lastPrices = new HashMap<>(properties.getSymbols());
        this.symbols = List.copyOf(properties.getSymbols().keySet());
    }

    @Scheduled(fixedRateString = "#{@priceSimulatorProperties.priceGenerationIntervalMillis}")
    public void generatePrice() {
        String symbol = symbols.get(random.nextInt(symbols.size()));

        try {
            PriceEvent priceEvent = buildPriceEvent(symbol);
            String payload = serialize(priceEvent);
            sendToKafka(symbol, payload);
            log.info("Price event sent | symbol={} price={} topic={}",
                    priceEvent.getSymbol(), priceEvent.getPrice(), PRICE_TOPIC);
        } catch (JsonProcessingException e) {
            log.error("Serialization failed for symbol={}", symbol, e);
        } catch (Exception e) {
            log.error("Price generation job failed for symbol={}", symbol, e);
        }
    }

    private PriceEvent buildPriceEvent(String symbol) {
        BigDecimal oldPrice = lastPrices.get(symbol);
        double changePercent = (random.nextDouble() * 0.2) - 0.1;
        BigDecimal newPrice = oldPrice
                .multiply(BigDecimal.valueOf(1 + changePercent))
                .setScale(4, RoundingMode.HALF_UP);
        lastPrices.put(symbol, newPrice);

        return new PriceEvent(symbol, newPrice, LocalDateTime.now());
    }

    private String serialize(PriceEvent priceEvent) throws JsonProcessingException {
        return objectMapper.writeValueAsString(priceEvent);
    }

    private void sendToKafka(String symbol, String payload) {
        kafkaTemplate.send(PRICE_TOPIC, symbol, payload);
    }

}
