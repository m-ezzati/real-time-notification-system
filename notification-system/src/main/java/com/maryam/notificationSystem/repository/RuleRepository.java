package com.maryam.notificationSystem.repository;

import com.maryam.notificationSystem.entity.Rule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author M.Ezati
 * 06/05/2026
 */
@Repository
public interface RuleRepository extends JpaRepository<Rule, Long> {
}