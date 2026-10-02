package com.maryam.notificationSystem.outbox.factory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maryam.notificationSystem.entity.OutboxEvent;
import com.maryam.notificationSystem.entity.enums.OutboxStatus;
import com.maryam.notificationSystem.exception.KafkaEventSerializationException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author M.Ezati
 * 11/05/2026
 */
@Component
public class OutboxEventFactory {

    private final ObjectMapper objectMapper;

    public OutboxEventFactory(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public OutboxEvent create(String aggregateType, Long aggregateId, String eventType, Object payload) {
        try {
            OutboxEvent event = new OutboxEvent();
            event.setAggregateType(aggregateType);
            event.setAggregateId(aggregateId);
            event.setEventType(eventType);
            event.setPayload(objectMapper.writeValueAsString(payload));
            event.setCreatedAt(LocalDateTime.now());
            event.setStatus(OutboxStatus.PENDING);
            return event;

        } catch (JsonProcessingException e) {
            throw new KafkaEventSerializationException("Failed to serialize event payload", e);
        }
    }
}