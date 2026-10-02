package com.maryam.notificationSystem.stream;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author M.Ezati
 * 06/05/2026
 */
public class PriceWindowAgg {

    private BigDecimal firstPrice;
    private BigDecimal lastPrice;
    private LocalDateTime lastTimestamp;

    public PriceWindowAgg() {
    }

    public BigDecimal getFirstPrice() {
        return firstPrice;
    }

    public void setFirstPrice(BigDecimal firstPrice) {
        this.firstPrice = firstPrice;
    }

    public BigDecimal getLastPrice() {
        return lastPrice;
    }

    public void setLastPrice(BigDecimal lastPrice) {
        this.lastPrice = lastPrice;
    }

    public LocalDateTime getLastTimestamp() {
        return lastTimestamp;
    }

    public void setLastTimestamp(LocalDateTime lastTimestamp) {
        this.lastTimestamp = lastTimestamp;
    }
}
