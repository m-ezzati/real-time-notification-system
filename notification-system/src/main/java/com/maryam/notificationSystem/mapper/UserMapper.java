package com.maryam.notificationSystem.mapper;

import com.maryam.notificationSystem.dto.UserRegisterDto;
import com.maryam.notificationSystem.entity.User;
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