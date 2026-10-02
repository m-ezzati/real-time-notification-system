package stream;

import com.maryam.notificationSystem.config.NotificationSysProperties;
import com.maryam.notificationSystem.entity.enums.NotificationStatus;
import com.maryam.notificationSystem.kafka.event.NotificationEvent;
import com.maryam.notificationSystem.kafka.event.PriceEvent;
import com.maryam.notificationSystem.kafka.event.RuleEvent;
import com.maryam.notificationSystem.stream.PriceRuleProcessor;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.*;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.kafka.support.serializer.JsonSerde;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class PriceRuleProcessorTest {

    private static final String RULE_TOPIC = "rules";
    private static final String PRICE_TOPIC = "prices";
    private static final String NOTIFICATION_TOPIC = "notifications";

    private TopologyTestDriver testDriver;
    private TestInputTopic<String, PriceEvent> priceInputTopic;
    private TestInputTopic<String, RuleEvent> ruleInputTopic;
    private TestOutputTopic<String, NotificationEvent> notificationOutputTopic;

    @BeforeEach
    void setup() {
        NotificationSysProperties properties = mock(NotificationSysProperties.class);

        StreamsBuilder builder = new StreamsBuilder();

        JsonSerde<PriceEvent> priceSerde = new JsonSerde<>(PriceEvent.class);
        JsonSerde<RuleEvent> ruleSerde = new JsonSerde<>(RuleEvent.class);
        JsonSerde<NotificationEvent> notificationSerde = new JsonSerde<>(NotificationEvent.class);

        priceSerde.configure(new HashMap<>(), false);
        ruleSerde.configure(new HashMap<>(), false);
        notificationSerde.configure(new HashMap<>(), false);

        KStream<String, PriceEvent> priceStream = builder.stream(PRICE_TOPIC, Consumed.with(Serdes.String(), priceSerde));
        KTable<String, RuleEvent> ruleTable = builder.table(RULE_TOPIC, Consumed.with(Serdes.String(), ruleSerde));


        new PriceRuleProcessor(priceStream, ruleTable, properties);

        Topology topology = builder.build();

        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "test-processor");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "dummy:9092");
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, JsonSerde.class.getName());

        testDriver = new TopologyTestDriver(topology, props);

        priceInputTopic = testDriver.createInputTopic(PRICE_TOPIC, Serdes.String().serializer(), priceSerde.serializer());
        ruleInputTopic = testDriver.createInputTopic(RULE_TOPIC, Serdes.String().serializer(), ruleSerde.serializer());
        notificationOutputTopic = testDriver.createOutputTopic(NOTIFICATION_TOPIC, Serdes.String().deserializer(), notificationSerde.deserializer());
    }

    @AfterEach
    void tearDown() {
        testDriver.close();
    }

    @Test
    void shouldSendNotification_WhenThresholdIsReached() {
        RuleEvent rule = new RuleEvent();
        rule.setSymbol("BTC");
        rule.setThresholdValue(BigDecimal.valueOf(50000.0));
        rule.setEmail("test@test.com");

        ruleInputTopic.pipeInput("BTC", rule);

        PriceEvent price = new PriceEvent();
        price.setPrice(BigDecimal.valueOf(51000.0));
        price.setEventTimestamp(LocalDateTime.now());

        priceInputTopic.pipeInput("BTC", price);

        assertFalse(notificationOutputTopic.isEmpty());
        NotificationEvent notificationEvent = notificationOutputTopic.readValue();
        assertTrue(notificationEvent.getMessage().contains("Threshold reached"));
        assertEquals(NotificationStatus.PENDING, notificationEvent.getNotificationStatus());
    }

    @Test
    void shouldSendNotification_WhenPercentageChangeIsReached() {
        RuleEvent rule = new RuleEvent();
        rule.setSymbol("ETH");
        rule.setPercentageValue(10.0);

        ruleInputTopic.pipeInput("ETH", rule);

        PriceEvent firstPriceEvent = new PriceEvent();
        firstPriceEvent.setPrice(BigDecimal.valueOf(100.0));
        firstPriceEvent.setEventTimestamp(LocalDateTime.now());
        priceInputTopic.pipeInput("ETH", firstPriceEvent);

        PriceEvent lastPriceEvent = new PriceEvent();
        lastPriceEvent.setPrice(BigDecimal.valueOf(115.0));
        lastPriceEvent.setEventTimestamp(LocalDateTime.now());
        priceInputTopic.pipeInput("ETH", lastPriceEvent);

        NotificationEvent notificationEvent = notificationOutputTopic.readValue();
        assertTrue(notificationEvent.getMessage().contains("Percentage change reached"));
        assertTrue(notificationEvent.getMessage().contains("15.00%"));
    }

    @Test
    void shouldNotSendNotification_WhenRuleIsExpired() {
        RuleEvent rule = new RuleEvent();
        rule.setSymbol("GOOG");
        rule.setThresholdValue(BigDecimal.valueOf(10.0));
        rule.setExpirationTime(LocalDateTime.now().minus(Duration.ofHours(1)));

        ruleInputTopic.pipeInput("GOOG", rule);

        PriceEvent price = new PriceEvent();
        price.setPrice(BigDecimal.valueOf(200.0));
        priceInputTopic.pipeInput("GOOG", price);

        assertTrue(notificationOutputTopic.isEmpty());
    }
}