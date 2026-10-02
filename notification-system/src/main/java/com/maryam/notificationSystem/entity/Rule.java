package com.maryam.notificationSystem.entity;

import com.maryam.notificationSystem.entity.enums.NotificationType;
import com.maryam.notificationSystem.entity.enums.RuleType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author M.Ezati
 * 06/05/2026
 */
@Entity
@Table(name = "rules")
@SequenceGenerator(
        name = "rule_seq",
        sequenceName = "RULE_SEQ",
        allocationSize = 1
)
public class Rule extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rule_seq")
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String symbol;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RuleType ruleType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationType notificationType;

    @Column
    @Positive
    private BigDecimal thresholdValue;

    @Column
    @Positive
    private Double percentageValue;

    @Column
    @Positive
    private Integer windowMinutes;

    @Column
    @Future
    private LocalDateTime expirationTime;

    @Transient
    private transient boolean notificationSent = false;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public boolean isNotificationSent() {
        return notificationSent;
    }

    public void setNotificationSent(boolean notificationSent) {
        this.notificationSent = notificationSent;
    }

    @Override
    public String toString() {
        return "Rule{" +
                "id=" + id +
                ", user=" + user +
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
