package com.srilatha.etl;

import java.math.BigDecimal;
import java.time.LocalDate;

/** Cleaned, typed record that gets loaded into the database. */
public class PriceRecord {
    private final String symbol;
    private final LocalDate tradeDate;
    private final BigDecimal price;
    private final String currency;
    private final String vendor;

    public PriceRecord(String symbol, LocalDate tradeDate, BigDecimal price, String currency, String vendor) {
        this.symbol = symbol;
        this.tradeDate = tradeDate;
        this.price = price;
        this.currency = currency;
        this.vendor = vendor;
    }

    public String getSymbol() { return symbol; }
    public LocalDate getTradeDate() { return tradeDate; }
    public BigDecimal getPrice() { return price; }
    public String getCurrency() { return currency; }
    public String getVendor() { return vendor; }
}
