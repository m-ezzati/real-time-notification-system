package com.maryam.notificationSystem.service.impl;

import com.maryam.notificationSystem.dto.RuleRegisterDto;
import com.maryam.notificationSystem.dto.RuleResponseDto;
import com.maryam.notificationSystem.entity.Rule;
import com.maryam.notificationSystem.entity.User;
import com.maryam.notificationSystem.entity.enums.NotificationType;
import com.maryam.notificationSystem.entity.enums.RuleType;
import com.maryam.notificationSystem.exception.*;
import com.maryam.notificationSystem.mapper.RuleMapper;
import com.maryam.notificationSystem.mapper.RuleResponseMapper;
import com.maryam.notificationSystem.repository.UserRepository;
import com.maryam.notificationSystem.service.RuleService;
import com.maryam.notificationSystem.service.manager.RuleTransactionalService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class RuleServiceImpl implements RuleService {

    private static final Logger log = LoggerFactory.getLogger(RuleServiceImpl.class);

    private final RuleMapper ruleMapper;
    private final UserRepository userRepository;
    private final RuleResponseMapper ruleResponseMapper;
    private final SymbolValidationServiceImpl symbolValidationService;
    private final RuleTransactionalService ruleManager;

    public RuleServiceImpl(RuleMapper ruleMapper, UserRepository userRepository, RuleResponseMapper ruleResponseMapper, SymbolValidationServiceImpl symbolValidationService, RuleTransactionalService ruleManager) {
        this.ruleMapper = ruleMapper;
        this.userRepository = userRepository;
        this.ruleResponseMapper = ruleResponseMapper;
        this.symbolValidationService = symbolValidationService;
        this.ruleManager = ruleManager;
    }

    @Override
    public RuleResponseDto createRule(@Valid RuleRegisterDto dto) {
        validateRuleConfiguration(dto);
        symbolValidationService.validateSymbol(dto.getSymbol());
        User user = findUser(dto.getUserPhoneNumber());
        validateNotificationAccess(user, dto);
        Rule rule = buildRule(user, dto);
        Rule savedRule = ruleManager.saveRuleAndEvent(rule, user);
        log.info("Rule created successfully | ruleId={} user={} symbol={}", savedRule.getId(), user.getUserName(), savedRule.getSymbol());

        return ruleResponseMapper.toDto(savedRule);
    }

    private void validateRuleConfiguration(RuleRegisterDto dto) {
        if (dto.getRuleType() == RuleType.PRICE_THRESHOLD && dto.getThresholdValue() == null) {
            throw new ThresholdValueRequiredException();
        }
        if (dto.getRuleType() == RuleType.PERCENT_CHANGE && dto.getPercentageValue() == null) {
            throw new PercentageValueRequiredException();
        }
    }

    private void validateNotificationAccess(User user, RuleRegisterDto dto) {
        if (dto.getNotificationType() == NotificationType.EMAIL && user.getEmail() == null) {
            throw new EmailRequiredException();
        }
        if (dto.getNotificationType() == NotificationType.SMS && user.getPhoneNumber() == null) {
            throw new PhoneRequiredException();
        }
    }

    private Rule buildRule(User user, RuleRegisterDto dto) {
        Rule rule = ruleMapper.toEntity(dto);
        rule.setUser(user);
        rule.setNotificationType(dto.getNotificationType());
        return rule;
    }

    private User findUser(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new UserNotFoundException(phoneNumber));
    }
}
