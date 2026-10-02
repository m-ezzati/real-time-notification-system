package com.maryam.notificationSystem.service.impl;

import com.maryam.notificationSystem.client.SimulatorClient;
import com.maryam.notificationSystem.dto.UserRegisterDto;
import com.maryam.notificationSystem.dto.UserResponseDto;
import com.maryam.notificationSystem.mapper.UserMapper;
import com.maryam.notificationSystem.mapper.UserResponseMapper;
import com.maryam.notificationSystem.service.UserService;
import com.maryam.notificationSystem.service.manager.UserTransactionalService;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * @author M.Ezati
 * 06/05/2026
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final UserResponseMapper userResponseMapper;
    private final UserTransactionalService userTransactionalService;
    private final SimulatorClient simulatorClient;

    public UserServiceImpl(UserMapper userMapper, UserResponseMapper userResponseMapper, UserTransactionalService userTransactionalService, SimulatorClient simulatorClient) {
        this.userMapper = userMapper;
        this.userResponseMapper = userResponseMapper;
        this.userTransactionalService = userTransactionalService;
        this.simulatorClient = simulatorClient;
    }

    @Override
    public UserResponseDto register(UserRegisterDto dto) {
        return userResponseMapper
                .toResponseDto(userTransactionalService
                        .createUser(userMapper.toEntity(dto)));
    }

    @Override
    public Set<String> getSymbols() {
        return simulatorClient.getSymbols();
    }
}
