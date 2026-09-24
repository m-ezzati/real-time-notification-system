package com.saber.testHibernate.dto;

import com.saber.testHibernate.kafka.event.RuleEvent;

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
