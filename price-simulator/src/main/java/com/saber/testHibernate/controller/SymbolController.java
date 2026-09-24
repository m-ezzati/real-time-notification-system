package com.saber.testHibernate.controller;

import com.saber.testHibernate.config.PriceSimulatorProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

/**
 * @author M.Ezati
 * 12/05/2026
 */
@RestController
@RequestMapping("/v1/symbols")
public class SymbolController {
    private final PriceSimulatorProperties properties;

    public SymbolController(PriceSimulatorProperties properties) {
        this.properties = properties;
    }

    @GetMapping
    public Set<String> getSymbols() {
        return properties.getSymbols().keySet();
    }
}
