package main.java.currency;

import java.time.LocalDate;

/**
 * This class represents the exchange rate for a specific date, base currency and a target currency.
 */
public class ExchangeRate {
    private LocalDate date;
    private String baseCurrency;
    private String targetCurrency;
    private double exchangeRate;

    public ExchangeRate(LocalDate date, String baseCurrency, String targetCurrency, double exchangeRate) {
        this.date = date;
        this.baseCurrency = baseCurrency;
        this.targetCurrency = targetCurrency;
        this.exchangeRate = exchangeRate;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getBaseCurrency() {
        return baseCurrency;
    }

    public String getTargetCurrency() {
        return targetCurrency;
    }

    public double getExchangeRate() {
        return exchangeRate;
    }
}

