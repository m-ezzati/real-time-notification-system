package com.saber.testHibernate.service;

import com.saber.testHibernate.dto.RuleRegisterDto;
import com.saber.testHibernate.dto.RuleResponseDto;

/**
 * @author M.Ezati
 * 06/05/2026
 */
public interface RuleService {
    RuleResponseDto createRule(RuleRegisterDto ruleRegisterDto);
}
