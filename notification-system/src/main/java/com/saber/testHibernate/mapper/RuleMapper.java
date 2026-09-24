package com.saber.testHibernate.mapper;

import com.saber.testHibernate.dto.RuleRegisterDto;
import com.saber.testHibernate.entity.Rule;
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
