package com.saber.testHibernate.mapper;

import com.saber.testHibernate.dto.UserRegisterDto;
import com.saber.testHibernate.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author M.Ezati
 * 06/05/2026
 */
@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "id", ignore = true)
    User toEntity(UserRegisterDto dto);

}