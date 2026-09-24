package com.saber.testHibernate.exception;

/**
 * @author M.Ezati
 * 11/05/2026
 */
public class NotificationProcessingException extends RuntimeException {
    public NotificationProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}
