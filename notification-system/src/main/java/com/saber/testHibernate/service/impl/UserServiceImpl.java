package com.saber.testHibernate.service.impl;

import com.saber.testHibernate.client.SimulatorClient;
import com.saber.testHibernate.dto.UserRegisterDto;
import com.saber.testHibernate.dto.UserResponseDto;
import com.saber.testHibernate.mapper.UserMapper;
import com.saber.testHibernate.mapper.UserResponseMapper;
import com.saber.testHibernate.service.UserService;
import com.saber.testHibernate.service.manager.UserTransactionalService;
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
