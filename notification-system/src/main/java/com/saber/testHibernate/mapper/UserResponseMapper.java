package com.saber.testHibernate.mapper;

import com.saber.testHibernate.dto.UserResponseDto;
import com.saber.testHibernate.entity.User;
import org.mapstruct.Mapper;

/**
 * @author M.Ezati
 * 10/05/2026
 */
@Mapper(componentModel = "spring")
public interface UserResponseMapper {
    UserResponseDto toResponseDto(User user);
}
