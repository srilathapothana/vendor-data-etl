package com.srilatha.etl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

/**
 * Transform step: validates and cleans each row.
 * Returning null filters the row out (it is counted as "filtered" by Spring Batch).
 */
@Component
public class PriceProcessor implements ItemProcessor<RawPrice, PriceRecord> {

    private static final Logger log = LoggerFactory.getLogger(PriceProcessor.class);

    @Override
    public PriceRecord process(RawPrice raw) {
        if (raw.getSymbol() == null || raw.getSymbol().isBlank()) {
            log.warn("Skipping row with missing symbol");
            return null;
        }
        try {
            LocalDate date = LocalDate.parse(raw.getTradeDate().trim());
            BigDecimal price = new BigDecimal(raw.getPrice().trim());
            if (price.signum() <= 0) {
                log.warn("Skipping {} - non-positive price {}", raw.getSymbol(), price);
                return null;
            }
            String currency = (raw.getCurrency() == null || raw.getCurrency().isBlank())
                    ? "USD" : raw.getCurrency().trim().toUpperCase();
            String vendor = raw.getVendor() == null ? "UNKNOWN" : raw.getVendor().trim();
            return new PriceRecord(raw.getSymbol().trim().toUpperCase(), date, price, currency, vendor);
        } catch (DateTimeParseException | NumberFormatException | NullPointerException e) {
            log.warn("Skipping {} - invalid date or price", raw.getSymbol());
            return null;
        }
    }
}
