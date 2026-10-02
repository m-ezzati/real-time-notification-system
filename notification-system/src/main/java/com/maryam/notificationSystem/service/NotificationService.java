package com.maryam.notificationSystem.service;

import com.maryam.notificationSystem.kafka.event.NotificationEvent;
import com.maryam.notificationSystem.entity.Notification;

/**
 * @author M.Ezati
 * 06/05/2026
 */
public interface NotificationService {
    Notification createNotification(NotificationEvent notificationEvent);
}
