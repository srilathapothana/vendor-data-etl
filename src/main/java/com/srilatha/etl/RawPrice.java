package com.srilatha.etl;

/** One line of the vendor CSV, read as plain strings (the "Extract" step). */
public class RawPrice {
    private String symbol;
    private String tradeDate;
    private String price;
    private String currency;
    private String vendor;

    public String getSymbol() { return symbol; }
    public void setSymbol(String symbol) { this.symbol = symbol; }
    public String getTradeDate() { return tradeDate; }
    public void setTradeDate(String tradeDate) { this.tradeDate = tradeDate; }
    public String getPrice() { return price; }
    public void setPrice(String price) { this.price = price; }
    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }
    public String getVendor() { return vendor; }
    public void setVendor(String vendor) { this.vendor = vendor; }
}
