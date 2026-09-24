package com.saber.testHibernate.kafka.event;

import com.saber.testHibernate.entity.enums.NotificationType;
import com.saber.testHibernate.entity.enums.RuleType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author M.Ezati
 * 06/05/2026
 */
public class RuleEvent {
    private Long ruleId;
    private String symbol;
    private RuleType ruleType;
    private BigDecimal thresholdValue;
    private Double percentageValue;
    private Integer windowMinutes;
    private LocalDateTime expirationTime;
    private NotificationType notificationType;
    private String phoneNumber;
    private String email;

    public RuleEvent() {
    }

    public RuleEvent(Long ruleId, String symbol, RuleType ruleType, BigDecimal thresholdValue, Double percentageValue, Integer windowMinutes,
                     LocalDateTime expirationTime, NotificationType notificationType, String phoneNumber, String email) {
        this.ruleId = ruleId;
        this.symbol = symbol;
        this.ruleType = ruleType;
        this.thresholdValue = thresholdValue;
        this.percentageValue = percentageValue;
        this.windowMinutes = windowMinutes;
        this.expirationTime = expirationTime;
        this.notificationType = notificationType;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public Long getRuleId() {
        return ruleId;
    }

    public void setRuleId(Long ruleId) {
        this.ruleId = ruleId;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public RuleType getRuleType() {
        return ruleType;
    }

    public void setRuleType(RuleType ruleType) {
        this.ruleType = ruleType;
    }

    public BigDecimal getThresholdValue() {
        return thresholdValue;
    }

    public void setThresholdValue(BigDecimal thresholdValue) {
        this.thresholdValue = thresholdValue;
    }

    public Double getPercentageValue() {
        return percentageValue;
    }

    public void setPercentageValue(Double percentageValue) {
        this.percentageValue = percentageValue;
    }

    public Integer getWindowMinutes() {
        return windowMinutes;
    }

    public void setWindowMinutes(Integer windowMinutes) {
        this.windowMinutes = windowMinutes;
    }

    public LocalDateTime getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(LocalDateTime expirationTime) {
        this.expirationTime = expirationTime;
    }

    public NotificationType getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(NotificationType notificationType) {
        this.notificationType = notificationType;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "RuleEvent{" +
                "ruleId=" + ruleId +
                ", symbol='" + symbol + '\'' +
                ", ruleType=" + ruleType +
                ", thresholdValue=" + thresholdValue +
                ", percentageValue=" + percentageValue +
                ", windowMinutes=" + windowMinutes +
                ", expirationTime=" + expirationTime +
                ", notificationType=" + notificationType +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
