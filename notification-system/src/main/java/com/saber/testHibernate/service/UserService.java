package com.saber.testHibernate.service;

import com.saber.testHibernate.dto.UserRegisterDto;
import com.saber.testHibernate.dto.UserResponseDto;

import java.util.Set;

/**
 * @author M.Ezati
 * 06/05/2026
 */
public interface UserService {
    UserResponseDto register(UserRegisterDto dto);
    Set<String> getSymbols();
}
