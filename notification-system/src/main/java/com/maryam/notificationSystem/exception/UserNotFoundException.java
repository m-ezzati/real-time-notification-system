package com.maryam.notificationSystem.exception;

/**
 * @author M.Ezati
 * 06/05/2026
 */
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String userPhoneNumber) {
        super("The user with phone number: " + userPhoneNumber + " not found");
    }
}
