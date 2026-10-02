package com.maryam.notificationSystem.repository;

import com.maryam.notificationSystem.entity.OutboxEvent;
import com.maryam.notificationSystem.entity.enums.OutboxStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author M.Ezati
 * 11/05/2026
 */
public interface OutboxRepository extends JpaRepository<OutboxEvent, Long> {
    List<OutboxEvent> findTopHundredByStatusOrderByCreatedAtAsc(OutboxStatus status);
}
