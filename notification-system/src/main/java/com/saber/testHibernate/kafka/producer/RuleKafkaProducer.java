package com.saber.testHibernate.kafka.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.saber.testHibernate.constants.KafkaTopics;
import com.saber.testHibernate.exception.KafkaEventSerializationException;
import com.saber.testHibernate.kafka.event.RuleEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * @author M.Ezati
 * 06/05/2026
 */
@Service
public class RuleKafkaProducer {

    private static final Logger log = LoggerFactory.getLogger(RuleKafkaProducer.class);
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private static final String RULE_TOPIC = KafkaTopics.RULE_TOPIC;

    public RuleKafkaProducer(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    public void sendRuleCreatedEvent(RuleEvent ruleEvent) {
        try {
            String jsonEvent = objectMapper.writeValueAsString(ruleEvent);
            log.info("Attempting to send Rule event: {}", jsonEvent);
            kafkaTemplate.send(RULE_TOPIC, ruleEvent.getSymbol(), jsonEvent)
                    .whenComplete((result, ex) -> {
                        if (ex == null) {
                            log.info("Rule event sent successfully for symbol={}", ruleEvent.getSymbol());
                        } else {
                            log.warn("Could not send Rule event to Kafka for symbol={}",
                                    ruleEvent.getSymbol(), ex);
                        }
                    });
        } catch (JsonProcessingException e) {
            log.error("Failed to serialize rule event");
            throw new KafkaEventSerializationException("Failed to serialize rule event", e);
        }
    }

}
