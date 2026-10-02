package com.maryam.notificationSystem.repository;

import com.maryam.notificationSystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author M.Ezati
 * 06/05/2026
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmailOrPhoneNumber(String email, String phoneNumber);

    Optional<User> findByPhoneNumber(String phoneNumber);
}
