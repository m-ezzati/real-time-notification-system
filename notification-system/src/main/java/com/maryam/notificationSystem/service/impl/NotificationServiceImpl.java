package com.maryam.notificationSystem.service.impl;

import com.maryam.notificationSystem.entity.Rule;
import com.maryam.notificationSystem.kafka.event.NotificationEvent;
import com.maryam.notificationSystem.entity.Notification;
import com.maryam.notificationSystem.mapper.NotificationMapper;
import com.maryam.notificationSystem.repository.NotificationRepository;
import com.maryam.notificationSystem.repository.RuleRepository;
import com.maryam.notificationSystem.service.NotificationService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author M.Ezati
 * 06/05/2026
 */
@Service
public class NotificationServiceImpl implements NotificationService {

    private static final Logger log = LoggerFactory.getLogger(NotificationServiceImpl.class);
    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;
    private final RuleRepository ruleRepository;

    public NotificationServiceImpl(NotificationRepository notificationRepository,
                                   NotificationMapper notificationMapper, RuleRepository ruleRepository) {
        this.notificationRepository = notificationRepository;
        this.notificationMapper = notificationMapper;
        this.ruleRepository = ruleRepository;
    }

    @Transactional
    @Override
    public Notification createNotification(NotificationEvent notificationEvent) {
        boolean exists = notificationRepository.existsByRuleId(notificationEvent.getRuleId());
        if (exists) {
            log.info("The Notification for this rule has already sent");
            return null;
        }
        Notification notification = notificationMapper.toEntity(notificationEvent);
        Rule rule = ruleRepository.getReferenceById(notificationEvent.getRuleId());
        notification.setRule(rule);
        return notificationRepository.save(notification);
    }
}
