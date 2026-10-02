package com.maryam.notificationSystem.entity;

import com.maryam.notificationSystem.entity.enums.NotificationStatus;
import com.maryam.notificationSystem.entity.enums.NotificationType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

/**
 * @author M.Ezati
 * 06/05/2026
 */
@Entity
@Table(name = "notifications")
@SequenceGenerator(
        name = "notification_seq",
        sequenceName = "NOTIFICATION_SEQ",
        allocationSize = 1
)
public class Notification extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "notification_seq")
    private Long id;
    @OneToOne(optional = false)
    @JoinColumn(name = "rule_id", nullable = false)
    private Rule rule;
    @NotBlank
    @Column(nullable = false, length = 1500)
    private String message;
    @NotBlank
    @Column(nullable = false)
    private String symbol;
    private String phoneNumber;
    private String email;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationType type;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationStatus status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Rule getRule() {
        return rule;
    }

    public void setRule(Rule rule) {
        this.rule = rule;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public NotificationType getType() {
        return type;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }

    public NotificationStatus getStatus() {
        return status;
    }

    public void setStatus(NotificationStatus status) {
        this.status = status;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
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
}
