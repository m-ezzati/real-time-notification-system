package com.saber.testHibernate.kafka.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.saber.testHibernate.constants.KafkaTopics;
import com.saber.testHibernate.entity.enums.NotificationStatus;
import com.saber.testHibernate.exception.NotificationProcessingException;
import com.saber.testHibernate.kafka.event.NotificationEvent;
import com.saber.testHibernate.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @author M.Ezati
 * 06/05/2026
 */
@Component
public class NotificationConsumer {

    private static final Logger log = LoggerFactory.getLogger(NotificationConsumer.class);

    private final ObjectMapper objectMapper;
    private final NotificationService notificationService;

    public NotificationConsumer(ObjectMapper objectMapper, NotificationService notificationService) {
        this.objectMapper = objectMapper;
        this.notificationService = notificationService;
    }

    @KafkaListener(topics = KafkaTopics.NOTIFICATION_TOPIC, groupId = "#{@notificationSysProperties.kafka.consumer.notificationGroupId}")
    public void consume(String message) {
        log.info("Received message from topic {} : {}", KafkaTopics.NOTIFICATION_TOPIC, message);
        try {
            NotificationEvent event = objectMapper.readValue(message, NotificationEvent.class);
            event.setNotificationStatus(NotificationStatus.PENDING);
            notificationService.createNotification(event);
            log.info("Notification processed successfully for ruleId={} symbol={}", event.getRuleId(), event.getSymbol());
        } catch (Exception ex) {
            log.error("Failed to process notification message: {}", message, ex);
            throw new NotificationProcessingException("Error processing notification event", ex);
        }
    }
}
