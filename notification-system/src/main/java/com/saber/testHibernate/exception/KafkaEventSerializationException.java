package com.saber.testHibernate.exception;

/**
 * @author M.Ezati
 * 06/05/2026
 */
public class KafkaEventSerializationException extends RuntimeException {
    public KafkaEventSerializationException(String message, Throwable cause) {
        super(message, cause);
    }
}