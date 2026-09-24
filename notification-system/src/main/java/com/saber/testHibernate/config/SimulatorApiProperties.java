package com.saber.testHibernate.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author M.Ezati
 * 12/05/2026
 */
@Component
@ConfigurationProperties(prefix = "simulator.api")
public class SimulatorApiProperties {
    private String baseUrl;
    private String symbolsPath;

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getSymbolsPath() {
        return symbolsPath;
    }

    public void setSymbolsPath(String symbolsPath) {
        this.symbolsPath = symbolsPath;
    }
}
