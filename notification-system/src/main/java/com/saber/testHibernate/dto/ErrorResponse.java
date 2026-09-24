package com.saber.testHibernate.dto;

import java.io.Serializable;
import java.util.Map;

/**
 * @author M.Ezati
 * 06/05/2026
 */
public class ErrorResponse implements Serializable {
    private String message;
    private int status;
    Map<String, String> errors;

    public ErrorResponse(String message, int status) {
        this.message = message;
        this.status = status;
    }

    public ErrorResponse(String message, int status, Map<String, String> errors) {
        this.message = message;
        this.status = status;
        this.errors = errors;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }
}
