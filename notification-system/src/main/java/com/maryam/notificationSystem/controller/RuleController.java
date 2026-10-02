package com.maryam.notificationSystem.controller;

import com.maryam.notificationSystem.dto.RuleRegisterDto;
import com.maryam.notificationSystem.dto.RuleResponseDto;
import com.maryam.notificationSystem.service.RuleService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author M.Ezati
 * 06/05/2026
 */
@RestController
@RequestMapping("/v1/rules")
public class RuleController {
    private final RuleService ruleService;

    public RuleController(RuleService ruleService) {
        this.ruleService = ruleService;
    }

    @PostMapping("/create")
    public ResponseEntity<RuleResponseDto> createRule(@Valid @RequestBody RuleRegisterDto dto) {
        return ResponseEntity.ok(ruleService.createRule(dto));
    }
}
