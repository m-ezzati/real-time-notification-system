package com.maryam.notificationSystem.exception;

public class SimulatorServiceUnavailableException extends RuntimeException {

    public SimulatorServiceUnavailableException(String message) {
        super(message);
    }

    public SimulatorServiceUnavailableException(String message, Throwable cause) {
        super(message, cause);
    }
}
