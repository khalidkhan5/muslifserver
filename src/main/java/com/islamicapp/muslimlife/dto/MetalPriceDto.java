package com.islamicapp.muslimlife.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * DTO for Gold/Metal Price API Response
 * Represents real-time precious metal prices and market data
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MetalPriceDto {

    /**
     * Unix timestamp of the price data
     */
    private Long timestamp;

    /**
     * Metal symbol (e.g., XAU for Gold, XAG for Silver)
     */
    private String metal;

    /**
     * Currency code (e.g., USD, EUR)
     */
    private String currency;

    /**
     * Exchange name
     */
    private String exchange;

    /**
     * Trading symbol (exchange:metal+currency)
     */
    private String symbol;

    /**
     * Previous close price
     */
    @JsonProperty("prev_close_price")
    private BigDecimal prevClosePrice;

    /**
     * Opening price
     */
    @JsonProperty("open_price")
    private BigDecimal openPrice;

    /**
     * Lowest price in the period
     */
    @JsonProperty("low_price")
    private BigDecimal lowPrice;

    /**
     * Highest price in the period
     */
    @JsonProperty("high_price")
    private BigDecimal highPrice;

    /**
     * Market open time (Unix timestamp)
     */
    @JsonProperty("open_time")
    private Long openTime;

    /**
     * Current price
     */
    private BigDecimal price;

    /**
     * Price change amount
     */
    private BigDecimal ch;

    /**
     * Price change percentage
     */
    private BigDecimal chp;

    /**
     * Ask price (seller's price)
     */
    private BigDecimal ask;

    /**
     * Bid price (buyer's price)
     */
    private BigDecimal bid;

    /**
     * Price per gram for 24 karat gold
     */
    @JsonProperty("price_gram_24k")
    private BigDecimal priceGram24k;

    /**
     * Price per gram for 22 karat gold
     */
    @JsonProperty("price_gram_22k")
    private BigDecimal priceGram22k;

    /**
     * Price per gram for 21 karat gold
     */
    @JsonProperty("price_gram_21k")
    private BigDecimal priceGram21k;

    /**
     * Price per gram for 20 karat gold
     */
    @JsonProperty("price_gram_20k")
    private BigDecimal priceGram20k;

    /**
     * Price per gram for 18 karat gold
     */
    @JsonProperty("price_gram_18k")
    private BigDecimal priceGram18k;

    /**
     * Price per gram for 16 karat gold
     */
    @JsonProperty("price_gram_16k")
    private BigDecimal priceGram16k;

    /**
     * Price per gram for 14 karat gold
     */
    @JsonProperty("price_gram_14k")
    private BigDecimal priceGram14k;

    /**
     * Price per gram for 10 karat gold
     */
    @JsonProperty("price_gram_10k")
    private BigDecimal priceGram10k;

    // Helper methods

    /**
     * Convert timestamp to LocalDateTime
     */
    public LocalDateTime getTimestampAsDateTime() {
        if (timestamp == null) return null;
        return LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp), ZoneId.systemDefault());
    }

    /**
     * Convert open time to LocalDateTime
     */
    public LocalDateTime getOpenTimeAsDateTime() {
        if (openTime == null) return null;
        return LocalDateTime.ofInstant(Instant.ofEpochSecond(openTime), ZoneId.systemDefault());
    }

    /**
     * Check if price is increasing
     */
    public boolean isPriceIncreasing() {
        return ch != null && ch.compareTo(BigDecimal.ZERO) > 0;
    }

    /**
     * Get formatted price change with sign
     */
    public String getFormattedPriceChange() {
        if (ch == null || chp == null) return "N/A";
        String sign = ch.compareTo(BigDecimal.ZERO) >= 0 ? "+" : "";
        return String.format("%s%.2f (%s%.2f%%)", sign, ch, sign, chp);
    }

    /**
     * Get spread (difference between ask and bid)
     */
    public BigDecimal getSpread() {
        if (ask == null || bid == null) return null;
        return ask.subtract(bid);
    }
}
