package com.maryam.notificationSystem.exception;

public class EventPublisherNotFoundException extends RuntimeException {
    public EventPublisherNotFoundException(String eventType) {
        super("No publisher registered for event type: " + eventType);
    }
}
