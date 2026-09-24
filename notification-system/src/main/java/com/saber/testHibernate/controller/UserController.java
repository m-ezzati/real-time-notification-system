package com.saber.testHibernate.controller;

import com.saber.testHibernate.dto.UserRegisterDto;
import com.saber.testHibernate.dto.UserResponseDto;
import com.saber.testHibernate.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * @author M.Ezati
 * 06/05/2026
 */
@RestController
@RequestMapping("/v1/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> register(@Valid @RequestBody UserRegisterDto dto) {
        UserResponseDto userResponse = userService.register(dto);
        return ResponseEntity.ok(userResponse);
    }

    @GetMapping("/fetchSymbols")
    public Set<String> getSymbols() {
        return userService.getSymbols();
    }

}
