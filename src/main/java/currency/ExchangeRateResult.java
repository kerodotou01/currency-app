package main.java.currency;

import java.time.LocalDate;

/**
 * This class is used to include both the date and exchange rate in the result
 * of the exchange rate request.
 */
public class ExchangeRateResult {
	private LocalDate date;
	private double exchangeRate;

	public ExchangeRateResult(LocalDate date, double exchangeRate) {
		this.date = date;
		this.exchangeRate = exchangeRate;
	}

	public LocalDate getDate() {
		return date;
	}

	public double getExchangeRate() {
		return exchangeRate;
	}

	@Override
	public String toString() {
		return "ExchangeRateResult{" + "date=" + date + ", exchangeRate=" + exchangeRate + '}';
	}
}
