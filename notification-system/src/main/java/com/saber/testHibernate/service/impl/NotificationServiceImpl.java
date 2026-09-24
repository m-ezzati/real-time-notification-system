package com.saber.testHibernate.service.impl;

import com.saber.testHibernate.entity.Rule;
import com.saber.testHibernate.kafka.event.NotificationEvent;
import com.saber.testHibernate.entity.Notification;
import com.saber.testHibernate.mapper.NotificationMapper;
import com.saber.testHibernate.repository.NotificationRepository;
import com.saber.testHibernate.repository.RuleRepository;
import com.saber.testHibernate.service.NotificationService;
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
