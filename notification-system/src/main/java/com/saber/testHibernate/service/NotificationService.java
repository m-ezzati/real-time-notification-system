package com.saber.testHibernate.service;

import com.saber.testHibernate.kafka.event.NotificationEvent;
import com.saber.testHibernate.entity.Notification;

/**
 * @author M.Ezati
 * 06/05/2026
 */
public interface NotificationService {
    Notification createNotification(NotificationEvent notificationEvent);
}
