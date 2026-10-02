package com.maryam.notificationSystem.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.maryam.notificationSystem.constants.KafkaTopics;
import com.maryam.notificationSystem.kafka.event.PriceEvent;
import com.maryam.notificationSystem.kafka.event.RuleEvent;
import com.maryam.notificationSystem.util.JsonMapperUtil;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author M.Ezati
 * 06/05/2026
 */
@Configuration
public class PriceRuleStreamTopology {
    private static final Logger log = LoggerFactory.getLogger(PriceRuleStreamTopology.class);
    public static final String PRICE_TOPIC = KafkaTopics.PRICE_TOPIC;
    public static final String RULE_TOPIC = KafkaTopics.RULE_TOPIC;
    private final ObjectMapper objectMapper;
    private final JsonMapperUtil jsonMapperUtil;

    public PriceRuleStreamTopology(JsonMapperUtil jsonMapperUtil) {
        this.jsonMapperUtil = jsonMapperUtil;
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
        this.objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @Bean
    public KStream<String, PriceEvent> priceStream(StreamsBuilder builder) {
        return builder
                .stream(PRICE_TOPIC,Consumed.with(Serdes.String(), Serdes.String()))
                .mapValues(json -> jsonMapperUtil.deserialize(json, PriceEvent.class))
                .filter((k, v) -> v != null)
                .peek((k, v) -> log.info("PRICE -> key={} value={}", k, v));
    }

    @Bean
    public KTable<String, RuleEvent> ruleTable(StreamsBuilder builder) {
        KTable<String, RuleEvent> ruleTable =
                builder
                        .table(RULE_TOPIC, Consumed.with(Serdes.String(), Serdes.String()))
                        .mapValues(json -> jsonMapperUtil.deserialize(json, RuleEvent.class));

        ruleTable.toStream()
                .filter((k, v) -> v != null)
                .peek((k, v) -> log.info("RULE -> key={} value={}", k, v));

        return ruleTable;
    }
}
