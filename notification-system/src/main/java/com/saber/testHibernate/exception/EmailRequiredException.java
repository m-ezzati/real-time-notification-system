package com.saber.testHibernate.exception;

/**
 * @author M.Ezati
 * 06/05/2026
 */
public class EmailRequiredException extends RuntimeException {
    public EmailRequiredException() {
        super("The user doesn't have email ");
    }
}
