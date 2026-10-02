package com.maryam.notificationSystem.service;

import com.maryam.notificationSystem.dto.RuleRegisterDto;
import com.maryam.notificationSystem.dto.RuleResponseDto;

/**
 * @author M.Ezati
 * 06/05/2026
 */
public interface RuleService {
    RuleResponseDto createRule(RuleRegisterDto ruleRegisterDto);
}
