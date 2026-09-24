package com.saber.testHibernate.config;

import jakarta.validation.constraints.Min;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.util.Map;

/**
 * @author M.Ezati
 * 06/05/2026
 */
@Validated
@Component
@ConfigurationProperties(prefix = "price-simulator")
public class PriceSimulatorProperties {
    @Min(1)
    private int priceGenerationInterval;
    private Map<String, BigDecimal> symbols;

    public int getPriceGenerationInterval() {
        return priceGenerationInterval;
    }

    public void setPriceGenerationInterval(int priceGenerationInterval) {
        this.priceGenerationInterval = priceGenerationInterval;
    }

    public Map<String, BigDecimal> getSymbols() {
        return symbols;
    }

    public void setSymbols(Map<String, BigDecimal> symbols) {
        this.symbols = symbols;
    }

    public long getPriceGenerationIntervalMillis() {
        return priceGenerationInterval * 60L * 1000L;
    }
}
