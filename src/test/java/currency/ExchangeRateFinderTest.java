package test.java.currency;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import main.java.currency.ExchangeRateFinder;
import main.java.currency.ExchangeRateResult;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * This test checks the functionality of the ExchangeRateFinder class.
 */
public class ExchangeRateFinderTest {

	private static final String CSV_FILE_PATH = "test_currencies.csv";

	/**
	 * Create a temporary CSV file with a few data for testing.
	 * 
	 * @throws IOException
	 */
	@BeforeAll
	public static void setUp() throws IOException {
		String csvContent = "date,base_currency,target_currency,exchange_rate\n" + "17-01-2023,USD,EUR,0.85\n"
				+ "18-01-2023,USD,EUR,0.87\n";
		Files.write(Paths.get(CSV_FILE_PATH), csvContent.getBytes());
	}

	/**
	 * Tests if the CSV file was parsed correctly by trying to retrieve the
	 * exchange rate for the date that exists in the file.
	 * 
	 * @throws IOException
	 */
	@Test
	public void testParseCSV() throws IOException {
		ExchangeRateFinder finder = new ExchangeRateFinder(CSV_FILE_PATH);
		ExchangeRateResult result = finder.getExchangeRate("USD", "EUR", "17-01-2023");
		assertEquals(LocalDate.parse("17-01-2023", DateTimeFormatter.ofPattern("dd-MM-yyyy")), result.getDate());
		assertEquals(0.85, result.getExchangeRate());
	}

	/**
	 * Tests the case that the given date does not exist, expecting the exchange
	 * rate for the previous date.
	 * 
	 * @throws IOException
	 */
	@Test
	public void testGetExchangeRateOnMissingDate() throws IOException {
		ExchangeRateFinder finder = new ExchangeRateFinder(CSV_FILE_PATH);
		ExchangeRateResult result = finder.getExchangeRate("USD", "EUR", "19-01-2023");
		assertEquals(LocalDate.parse("18-01-2023", DateTimeFormatter.ofPattern("dd-MM-yyyy")), result.getDate());
		assertEquals(0.87, result.getExchangeRate());
	}

	/**
	 * Tests the case that the date provided does not exist in the available data
	 * and there is no available rate before that.
	 * 
	 * @throws IOException
	 */
	@Test
	public void testGetExchangeRateBeyondAvailableData() throws IOException {
		ExchangeRateFinder finder = new ExchangeRateFinder(CSV_FILE_PATH);
		assertThrows(IllegalArgumentException.class, () -> finder.getExchangeRate("USD", "EUR", "16-01-2023"));
	}

	/**
	 * Tests the user input for the date format and the currencies.
	 */
	@Test
	public void testValidateInput() {
		// Valid input
		ExchangeRateFinder.validateInput("17-01-2023", "USD", "EUR");

		// Invalid date format
		assertThrows(IllegalArgumentException.class,
				() -> ExchangeRateFinder.validateInput("2023-01-17", "USD", "EUR"));

		// Invalid base currency
		assertThrows(IllegalArgumentException.class, () -> ExchangeRateFinder.validateInput("17-01-2023", "US", "EUR"));

		// Invalid target currency
		assertThrows(IllegalArgumentException.class,
				() -> ExchangeRateFinder.validateInput("17-01-2023", "USD", "EURO"));
	}
}
