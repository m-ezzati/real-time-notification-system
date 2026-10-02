package com.maryam.notificationSystem.service;

import com.maryam.notificationSystem.dto.UserRegisterDto;
import com.maryam.notificationSystem.dto.UserResponseDto;

import java.util.Set;

/**
 * @author M.Ezati
 * 06/05/2026
 */
public interface UserService {
    UserResponseDto register(UserRegisterDto dto);
    Set<String> getSymbols();
}
