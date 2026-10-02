package com.maryam.simulator.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author M.Ezati
 * 06/05/2026
 */
public class PriceEvent implements Serializable {
    private String symbol;
    private BigDecimal price;
    private LocalDateTime eventTimestamp;

    public PriceEvent(String symbol, BigDecimal price, LocalDateTime eventTimestamp) {
        this.symbol = symbol;
        this.price = price;
        this.eventTimestamp = eventTimestamp;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalDateTime getEventTimestamp() {
        return eventTimestamp;
    }

    public void setEventTimestamp(LocalDateTime eventTimestamp) {
        this.eventTimestamp = eventTimestamp;
    }

    @Override
    public String toString() {
        return "PriceEvent{" +
                "symbol='" + symbol + '\'' +
                ", price=" + price +
                ", eventTimestamp=" + eventTimestamp +
                '}';
    }
}
