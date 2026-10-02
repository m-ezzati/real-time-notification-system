package com.maryam.notificationSystem.exception;

/**
 * @author M.Ezati
 * 06/05/2026
 */
public class PhoneNumberAlreadyExistsException extends RuntimeException {
    public PhoneNumberAlreadyExistsException(String phoneNumber) {
        super("Phone number already exists: " + phoneNumber);
    }
}