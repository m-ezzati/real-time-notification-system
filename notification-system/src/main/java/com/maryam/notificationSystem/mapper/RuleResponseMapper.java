package com.maryam.notificationSystem.mapper;

import com.maryam.notificationSystem.dto.RuleResponseDto;
import com.maryam.notificationSystem.entity.Rule;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * @author M.Ezati
 * 09/05/2026
 */
@Mapper(componentModel = "spring")
public interface RuleResponseMapper {
    @Mappings({
            @Mapping(target = "userPhoneNumber", source = "user.phoneNumber"),
            @Mapping(target = "username", source = "user.userName")
    })
    RuleResponseDto toDto(Rule rule);
}