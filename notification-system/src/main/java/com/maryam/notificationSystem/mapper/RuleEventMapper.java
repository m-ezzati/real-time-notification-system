package com.maryam.notificationSystem.mapper;

import com.maryam.notificationSystem.entity.Rule;
import com.maryam.notificationSystem.kafka.event.RuleEvent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author M.Ezati
 * 06/05/2026
 */
@Mapper(componentModel = "spring")
public interface RuleEventMapper {
    @Mapping(source = "id", target = "ruleId")
    RuleEvent toRuleEvent(Rule rule);
}
