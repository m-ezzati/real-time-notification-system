package com.maryam.notificationSystem.outbox.publisher;

/**
 * @author M.Ezati
 * 11/05/2026
 */
public interface EventPublisher {
    String getEventType();
    void publish(String payload);
}
