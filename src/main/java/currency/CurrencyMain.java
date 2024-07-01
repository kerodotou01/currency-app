package main.java.currency;

import java.io.IOException;
import java.util.Scanner;

/**
 * This class is used to provide the exchange rate that corresponds to the date
 * and currencies that the user will provide by utilizing the ExchangeRateFinder
 * class.
 */
public class CurrencyMain {

	public static void main(String[] args) {
		String filePath = "currencies.csv";

		String baseCurrency, targetCurrency, dateStr;

		Scanner scanner = new Scanner(System.in);

		System.out.println("\n----------------- Welcome to the Currency Conversion Application -----------------\n");
		System.out.print("Enter base currency: ");
		baseCurrency = scanner.nextLine();

		System.out.print("Enter target currency: ");
		targetCurrency = scanner.nextLine();

		System.out.print("Enter date (dd-MM-yyyy): ");
		dateStr = scanner.nextLine();

		scanner.close();
		try {
			ExchangeRateFinder.validateInput(dateStr, baseCurrency, targetCurrency);
			ExchangeRateFinder exchangeRateFinder = new ExchangeRateFinder(filePath);
			ExchangeRateResult result = exchangeRateFinder.getExchangeRate(baseCurrency, targetCurrency, dateStr);
			System.out.println("\nThe exchange rate from " + baseCurrency + " to " + targetCurrency + " on "
					+ result.getDate() + " is " + result.getExchangeRate());
			System.out.println("\n--------------- Thank for using the Currency Conversion Application ---------------\n");

		} catch (IOException | IllegalArgumentException e) {
			System.err.println(e.getMessage());
		}

	}

}
