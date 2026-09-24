package com.saber.testHibernate.dto;

import com.saber.testHibernate.entity.enums.NotificationType;
import com.saber.testHibernate.entity.enums.RuleType;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author M.Ezati
 * 06/05/2026
 */
public class RuleRegisterDto implements Serializable {
    @NotNull(message = "User phone number is required")
    private String userPhoneNumber;
    @NotNull(message = "Symbol is required")
    private String symbol;
    @NotNull(message = "Rule type is required")
    private RuleType ruleType;
    @NotNull(message = "Notification type is required")
    private NotificationType notificationType;
    @Positive(message = "Threshold value must be positive")
    private BigDecimal thresholdValue;
    @Positive(message = "Percentage value must be positive")
    private Double percentageValue;
    @Positive(message = "Window minutes must be positive")
    private Integer windowMinutes;
    @Future(message = "Expiration time must be in the future")
    private LocalDateTime expirationTime;

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
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

    @Override
    public String toString() {
        return "RuleRegisterDto{" +
                "userPhoneNumber=" + userPhoneNumber +
                ", symbol='" + symbol + '\'' +
                ", ruleType=" + ruleType +
                ", notificationType=" + notificationType +
                ", thresholdValue=" + thresholdValue +
                ", percentageValue=" + percentageValue +
                ", windowMinutes=" + windowMinutes +
                ", expirationTime=" + expirationTime +
                '}';
    }
}
