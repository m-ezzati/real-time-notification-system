package com.maryam.notificationSystem.service;


import com.maryam.notificationSystem.entity.OutboxEvent;

/**
 * @author M.Ezati
 * 11/05/2026
 */
public interface OutboxService {
    void saveEvent(OutboxEvent outboxEvent);
}
