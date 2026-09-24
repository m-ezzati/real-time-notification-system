package service;


import com.saber.testHibernate.client.SimulatorClient;
import com.saber.testHibernate.dto.UserRegisterDto;
import com.saber.testHibernate.dto.UserResponseDto;
import com.saber.testHibernate.entity.User;
import com.saber.testHibernate.exception.EmailOrPhoneNumberAlreadyExistsException;
import com.saber.testHibernate.exception.PhoneNumberAlreadyExistsException;
import com.saber.testHibernate.mapper.UserMapper;
import com.saber.testHibernate.mapper.UserResponseMapper;
import com.saber.testHibernate.repository.UserRepository;
import com.saber.testHibernate.service.impl.UserServiceImpl;
import com.saber.testHibernate.service.manager.UserTransactionalService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    private UserRepository userRepository;
    private UserMapper userMapper;
    private UserServiceImpl userService;
    private UserResponseMapper userResponseMapper;
    private UserTransactionalService userTransactionalService;
    private SimulatorClient simulatorClient;

    @BeforeEach
    void setup() {
        userRepository = mock(UserRepository.class);
        userMapper = mock(UserMapper.class);
        userResponseMapper = mock(UserResponseMapper.class);
        userTransactionalService = mock(UserTransactionalService.class);
        simulatorClient = mock(SimulatorClient.class);

        userService = new UserServiceImpl(userMapper, userResponseMapper, userTransactionalService, simulatorClient);
    }

    @Test
    void register_ShouldCreateUser_WhenEmailAndPhoneNotExists() {

        UserRegisterDto dto = new UserRegisterDto();
        dto.setEmail("test@example.com");
        dto.setPhoneNumber("0912000000");
        dto.setUserName("testUser");

        User mappedUser = new User();
        mappedUser.setUserName("testUser");

        User savedUser = new User();
        savedUser.setUserName("testUser");

        when(userRepository.findByEmailOrPhoneNumber(dto.getEmail(), dto.getPhoneNumber())).thenReturn(Optional.empty());
        when(userRepository.findByPhoneNumber(dto.getPhoneNumber())).thenReturn(Optional.empty());

        when(userMapper.toEntity(dto)).thenReturn(mappedUser);

        when(userRepository.save(mappedUser)).thenReturn(savedUser);

        UserResponseDto result = userService.register(dto);

        assertNotNull(result);
        assertEquals("testUser", result.getUserName());

        verify(userRepository).findByEmailOrPhoneNumber(dto.getEmail(), dto.getPhoneNumber());
        verify(userMapper).toEntity(dto);
        verify(userRepository).save(mappedUser);
    }

    @Test
    void register_ShouldThrowEmailAlreadyExistsException_WhenEmailExists() {

        UserRegisterDto dto = new UserRegisterDto();
        dto.setEmail("exists@example.com");

        when(userRepository.findByEmailOrPhoneNumber(dto.getEmail(), dto.getPhoneNumber()))
                .thenReturn(Optional.of(new User()));

        assertThrows(EmailOrPhoneNumberAlreadyExistsException.class, () -> userService.register(dto));

        verify(userRepository).findByEmailOrPhoneNumber(dto.getEmail(), dto.getPhoneNumber());
        verify(userRepository, never()).findByPhoneNumber(any());
        verify(userMapper, never()).toEntity(any());
        verify(userRepository, never()).save(any());
    }

    @Test
    void register_ShouldThrowPhoneNumberAlreadyExistsException_WhenPhoneExists() {

        UserRegisterDto dto = new UserRegisterDto();
        dto.setEmail("new@example.com");
        dto.setPhoneNumber("0912000000");

        when(userRepository.findByEmailOrPhoneNumber(dto.getEmail(), dto.getPhoneNumber())).thenReturn(Optional.empty());
        when(userRepository.findByPhoneNumber(dto.getPhoneNumber()))
                .thenReturn(Optional.of(new User()));

        assertThrows(PhoneNumberAlreadyExistsException.class,
                () -> userService.register(dto));

        verify(userRepository).findByEmailOrPhoneNumber(dto.getEmail(), dto.getPhoneNumber());
        verify(userRepository).findByPhoneNumber(dto.getPhoneNumber());
        verify(userMapper, never()).toEntity(any());
        verify(userRepository, never()).save(any());
    }
}
