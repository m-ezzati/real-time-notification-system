package com.maryam.notificationSystem.exception;

/**
 * @author M.Ezati
 * 06/05/2026
 */
public class PercentageValueRequiredException extends RuntimeException  {
    public PercentageValueRequiredException(){
        super("percentageValue is required for PERCENT_CHANGE rule type");
    }
}
