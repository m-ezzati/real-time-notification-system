package com.saber.testHibernate.exception;

/**
 * @author M.Ezati
 * 06/05/2026
 */
public class PhoneRequiredException extends RuntimeException {
    public PhoneRequiredException() {
        super("The user doesn't have phone number ");
    }
}
