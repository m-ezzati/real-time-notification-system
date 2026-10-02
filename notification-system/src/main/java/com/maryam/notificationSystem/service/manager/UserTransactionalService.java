package com.maryam.notificationSystem.service.manager;

import com.maryam.notificationSystem.entity.User;
import com.maryam.notificationSystem.exception.EmailOrPhoneNumberAlreadyExistsException;
import com.maryam.notificationSystem.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author M.Ezati
 * 13/05/2026
 */
@Service
public class UserTransactionalService {

    private static final Logger log = LoggerFactory.getLogger(UserTransactionalService.class);
    private final UserRepository userRepository;

    public UserTransactionalService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public User createUser(User user){
        userRepository.findByEmailOrPhoneNumber(user.getEmail(), user.getPhoneNumber())
                .ifPresent(u -> {
                    log.info("Email Or phone number already exists: {}", user.getEmail());
                    throw new EmailOrPhoneNumberAlreadyExistsException(user.getEmail());
                });
        User savedUser = userRepository.save(user);
        log.info("User created successfully with username: {}", savedUser.getUserName());
        return savedUser;
    }
}
