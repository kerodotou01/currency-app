package test.java.currency;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import main.java.currency.ExchangeRate;
/**
 * Unit tests for checking the functionality of the ExchangeRate class. 
 */
class ExchangeRateTest {

	/**
	 * It is a simple data class so we just check the constructor and the getters.
	 */
    @Test
    public void testExchangeRateConstructorAndGetters() {
        LocalDate date = LocalDate.of(2023, 1, 17);
        String baseCurrency = "USD";
        String targetCurrency = "EUR";
        double exchangeRate = 0.85;

        ExchangeRate rate = new ExchangeRate(date, baseCurrency, targetCurrency, exchangeRate);

        assertEquals(date, rate.getDate());
        assertEquals(baseCurrency, rate.getBaseCurrency());
        assertEquals(targetCurrency, rate.getTargetCurrency());
        assertEquals(exchangeRate, rate.getExchangeRate());
    }
}
