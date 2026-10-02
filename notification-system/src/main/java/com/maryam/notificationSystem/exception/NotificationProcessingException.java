package com.maryam.notificationSystem.exception;

/**
 * @author M.Ezati
 * 11/05/2026
 */
public class NotificationProcessingException extends RuntimeException {
    public NotificationProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}
