package com.maryam.notificationSystem.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maryam.notificationSystem.entity.OutboxEvent;
import com.maryam.notificationSystem.entity.enums.OutboxStatus;
import com.maryam.notificationSystem.exception.KafkaEventSerializationException;
import com.maryam.notificationSystem.repository.OutboxRepository;
import com.maryam.notificationSystem.service.OutboxService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @author M.Ezati
 * 11/05/2026
 */
@Service
public class OutboxServiceImpl implements OutboxService {

    private final OutboxRepository outboxRepository;

    public OutboxServiceImpl(OutboxRepository repository) {
        this.outboxRepository = repository;
    }

    public void saveEvent(OutboxEvent outboxEvent) {
        outboxRepository.save(outboxEvent);
    }
}
