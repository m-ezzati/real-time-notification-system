package com.saber.testHibernate.service.impl;

import com.saber.testHibernate.client.SimulatorClient;
import org.springframework.stereotype.Service;

/**
 * @author M.Ezati
 * 12/05/2026
 */
@Service
public class SymbolValidationServiceImpl {
    private final SimulatorClient simulatorClient;

    public SymbolValidationServiceImpl(SimulatorClient simulatorClient) {
        this.simulatorClient = simulatorClient;
    }

    public void validateSymbol(String symbol) {
        if (!simulatorClient.getSymbols().contains(symbol)) {
            throw new IllegalArgumentException("Symbol not supported: " + symbol);
        }
    }
}
