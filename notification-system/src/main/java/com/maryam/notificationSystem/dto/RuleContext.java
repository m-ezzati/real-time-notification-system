package com.maryam.notificationSystem.dto;

import com.maryam.notificationSystem.kafka.event.RuleEvent;

import java.math.BigDecimal;

/**
 * @author M.Ezati
 * 09/05/2026
 */
public record RuleContext(
        RuleEvent ruleEvent,
        BigDecimal firstPrice,
        BigDecimal currentPrice,
        boolean thresholdHit,
        boolean percentageHit
) {}
