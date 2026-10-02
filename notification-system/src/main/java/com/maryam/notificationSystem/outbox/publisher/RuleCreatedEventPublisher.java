package com.maryam.notificationSystem.outbox.publisher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maryam.notificationSystem.exception.EventPublishException;
import com.maryam.notificationSystem.exception.KafkaEventSerializationException;
import com.maryam.notificationSystem.kafka.event.RuleEvent;
import com.maryam.notificationSystem.kafka.producer.RuleKafkaProducer;
import org.apache.kafka.common.KafkaException;
import org.springframework.stereotype.Service;

/**
 * @author M.Ezati
 * 11/05/2026
 */
@Service
public class RuleCreatedEventPublisher implements EventPublisher {
    private final RuleKafkaProducer ruleKafkaProducer;
    private final ObjectMapper objectMapper;

    public RuleCreatedEventPublisher(RuleKafkaProducer kafkaProducer, ObjectMapper objectMapper) {
        this.ruleKafkaProducer = kafkaProducer;
        this.objectMapper = objectMapper;
    }

    @Override
    public String getEventType() {
        return "RULE_CREATED";
    }

    @Override
    public void publish(String payload) {
        try {
            RuleEvent event = objectMapper.readValue(payload, RuleEvent.class);
            ruleKafkaProducer.sendRuleCreatedEvent(event);
        } catch (JsonProcessingException ex) {
            throw new KafkaEventSerializationException("Failed to deserialize RuleEvent payload", ex);
        } catch (KafkaException ex) {
            throw new EventPublishException("Failed to publish RuleEvent to Kafka");
        }
    }
}