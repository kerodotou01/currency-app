package main.java.currency;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

/**
 * The main functionality of this class is to provide the exchange rate given a
 * date, base currency and target currency. In order to achieve that it reads a
 * CSV file that contains the exchange rates for specific dates, base currencies
 * and target currencies and it stores the data in a hash map.
 */
public class ExchangeRateFinder {
	private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	private Map<String, ExchangeRate> exchangeRates;

	/**
	 * Constructor used to initialize this object by reading a CSV file.
	 * 
	 * @param filePath Path to the CSV file.
	 * @throws IOException if an I/O error occurs reading from the file.
	 */
	public ExchangeRateFinder(String filePath) throws IOException {
		exchangeRates = new HashMap<>();
		parseCSV(filePath);
	}

	/**
	 * Reads a CSV file that contains the exchange rates, it generates a unique key
	 * for each entry and then it populates the hash map.
	 * 
	 * @param filePath Path to the CSV file.
	 * @throws IOException if an I/O error occurs reading from the file.
	 */
	private void parseCSV(String filePath) throws IOException {
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			String line;
			br.readLine(); // Skip header line
			while ((line = br.readLine()) != null) {
				String[] values = line.split(",");
				LocalDate date = LocalDate.parse(values[0], DATE_FORMAT);
				String baseCurrency = values[1];
				String targetCurrency = values[2];
				double exchangeRate = Double.parseDouble(values[3]);
				String key = generateKey(date, baseCurrency, targetCurrency);
				exchangeRates.put(key, new ExchangeRate(date, baseCurrency, targetCurrency, exchangeRate));
			}
		}
	}

	/**
	 * Retrieves the exchange rate for the specified base currency, target currency
	 * and date. If the exact date is not available, it looks for the most recent
	 * available rate before the given date.
	 *
	 * @param baseCurrency   The base currency code.
	 * @param targetCurrency The target currency code.
	 * @param dateStr        The date in the format "dd-MM-yyyy".
	 * @return ExchangeRateResult Contains the date and exchange rate.
	 * @throws IllegalArgumentException if no exchange rate is available for the
	 *                                  given date and currencies.
	 */
	public ExchangeRateResult getExchangeRate(String baseCurrency, String targetCurrency, String dateStr) {
		LocalDate date = LocalDate.parse(dateStr, DATE_FORMAT);
		LocalDate originalDate = date;
		while (!(date.getYear() < 2023)) {
			String key = generateKey(date, baseCurrency, targetCurrency);
			if (exchangeRates.containsKey(key)) {
				if (!date.equals(originalDate)) {
					System.out.println(
							"\nNo exchange rate available for " + originalDate + ". Returning rate for " + date + ".");
				}
				ExchangeRate exchangeRate = exchangeRates.get(key);
				return new ExchangeRateResult(exchangeRate.getDate(), exchangeRate.getExchangeRate());
			}
			date = date.minusDays(1);
		}
		throw new IllegalArgumentException("No exchange rate available for the given date and currencies.");
	}

	/**
	 * Generates a key which for each entry in the hash map.
	 * 
	 * @param date           The date of the exchange rate.
	 * @param baseCurrency   The base currency code.
	 * @param targetCurrency The target currency code.
	 * @return
	 */
	private String generateKey(LocalDate date, String baseCurrency, String targetCurrency) {
		return date.toString() + "-" + baseCurrency + "-" + targetCurrency;
	}

	/**
	 * Validates the user input for the currencies and the date.
	 * 
	 * @param dateStr        The date should be in the dd-MM-yyyy format
	 * @param baseCurrency   The base currency code.
	 * @param targetCurrency The target currency code.
	 * @throws IllegalArgumentException If the input for the currencies and the date
	 *                                  are not valid.
	 */
	public static void validateInput(String dateStr, String baseCurrency, String targetCurrency) {
		try {
			LocalDate.parse(dateStr, DATE_FORMAT);
		} catch (DateTimeParseException e) {
			throw new IllegalArgumentException("Invalid date format. Please use DD-MM-YYYY.");
		}

		if (baseCurrency.length() != 3 || !baseCurrency.matches("[A-Z]+")) {
			throw new IllegalArgumentException(
					"Invalid base currency code. It should be a 3-letter uppercase alphabetic code.");
		}

		if (targetCurrency.length() != 3 || !targetCurrency.matches("[A-Z]+")) {
			throw new IllegalArgumentException(
					"Invalid target currency code. It should be a 3-letter uppercase alphabetic code.");
		}
	}
}
