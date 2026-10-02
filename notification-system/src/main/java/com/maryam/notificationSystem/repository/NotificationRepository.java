package com.maryam.notificationSystem.repository;

import com.maryam.notificationSystem.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author M.Ezati
 * 06/05/2026
 */
@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    boolean existsByRuleId(Long ruleId);
}
