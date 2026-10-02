package com.maryam.notificationSystem.mapper;

import com.maryam.notificationSystem.dto.RuleRegisterDto;
import com.maryam.notificationSystem.entity.Rule;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author M.Ezati
 * 06/05/2026
 */
@Mapper(componentModel = "spring")
public interface RuleMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    Rule toEntity(RuleRegisterDto dto);
}
