package com.saber.testHibernate.exception;

/**
 * @author M.Ezati
 * 06/05/2026
 */
public class ThresholdValueRequiredException extends RuntimeException {
    public ThresholdValueRequiredException() {
        super("thresholdValue is required for PRICE_THRESHOLD rule type");
    }
}
