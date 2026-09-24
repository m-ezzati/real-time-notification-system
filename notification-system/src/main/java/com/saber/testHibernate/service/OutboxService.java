package com.saber.testHibernate.service;


import com.saber.testHibernate.entity.OutboxEvent;

/**
 * @author M.Ezati
 * 11/05/2026
 */
public interface OutboxService {
    void saveEvent(OutboxEvent outboxEvent);
}
