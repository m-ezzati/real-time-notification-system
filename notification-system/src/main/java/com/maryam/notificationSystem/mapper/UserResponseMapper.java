package com.maryam.notificationSystem.mapper;

import com.maryam.notificationSystem.dto.UserResponseDto;
import com.maryam.notificationSystem.entity.User;
import org.mapstruct.Mapper;

/**
 * @author M.Ezati
 * 10/05/2026
 */
@Mapper(componentModel = "spring")
public interface UserResponseMapper {
    UserResponseDto toResponseDto(User user);
}
