package com.saber.testHibernate.exception;

/**
 * @author M.Ezati
 * 06/05/2026
 */
public class EmailOrPhoneNumberAlreadyExistsException extends RuntimeException {
    public EmailOrPhoneNumberAlreadyExistsException(String email) {
        super("Email or phone number already exists: " + email);
    }
}