package com.maryam.notificationSystem.kafka.event;

import com.maryam.notificationSystem.entity.enums.NotificationStatus;
import com.maryam.notificationSystem.entity.enums.NotificationType;

/**
 * @author M.Ezati
 * 06/05/2026
 */
public class NotificationEvent {
    private Long ruleId;
    private String symbol;
    private String email;
    private String phoneNumber;
    private String message;
    private boolean delivery;
    private NotificationStatus notificationStatus;
    private NotificationType notificationType;
    public NotificationEvent() {
    }

    public NotificationEvent(Long ruleId, String symbol, String email, String phoneNumber, String message, boolean delivery, NotificationStatus notificationStatus, NotificationType notificationType) {
        this.ruleId = ruleId;
        this.symbol = symbol;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.message = message;
        this.delivery = delivery;
        this.notificationStatus = notificationStatus;
        this.notificationType = notificationType;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isDelivery() {
        return delivery;
    }

    public void setDelivery(boolean delivery) {
        this.delivery = delivery;
    }

    public NotificationStatus getNotificationStatus() {
        return notificationStatus;
    }

    public void setNotificationStatus(NotificationStatus notificationStatus) {
        this.notificationStatus = notificationStatus;
    }

    public NotificationType getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(NotificationType notificationType) {
        this.notificationType = notificationType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "NotificationEvent{" +
                "symbol='" + symbol + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", message='" + message + '\'' +
                ", delivery=" + delivery +
                ", notificationStatus=" + notificationStatus +
                ", notificationType=" + notificationType +
                '}';
    }
}
