package com.saber.testHibernate.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.saber.testHibernate.entity.OutboxEvent;
import com.saber.testHibernate.entity.enums.OutboxStatus;
import com.saber.testHibernate.exception.KafkaEventSerializationException;
import com.saber.testHibernate.repository.OutboxRepository;
import com.saber.testHibernate.service.OutboxService;
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
