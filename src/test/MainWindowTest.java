package test;

import currencyConverter.Currency;
import currencyConverter.MainWindow;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class MainWindowTest {
    /**
     * Test if converting a currency to the same currency always returns
     * the same amount chosen.
     * black box test
     */
    @Test
    public void testConvert() {
        ArrayList<Currency> currencies = Currency.init();
        for (Currency currency : currencies) {
            Double value = MainWindow.convert(currency.getName(), currency.getName(), currencies, 1.0);
            assertEquals(value, 1.0);
        }
    }

    /**
     * Test that all currencies in the specifications are implemented.
     * black box test
     */
    @Test
    public void testAllCurrencies() {
        ArrayList<String> validCurrencies =
                new ArrayList<>(Arrays.asList("USD", "CAD", "GBP", "EUR", "CHF", "AUD"));

        ArrayList<Currency> currencies = Currency.init();

        for (String shortName : validCurrencies) {
            String longName = null;
            for (Currency currency : currencies) {
                if (currency.getShortName().equals(shortName)) {
                    longName = currency.getName();
                }
            }
            assertNotNull(longName);
            Double value = MainWindow.convert(longName, longName, currencies, 1.0);
            assertEquals(1.0, value);
        }

    }

    /**
     * Testing for non-valid arguments:
     * - Negative amount
     * - Amount greater than 1 000 000 (maximum is 1 000 000)
     * - Non-existant currencies for currency1 and currency2
     * black box test
     */
    @Test
    public void testIllegalArgs() {
        ArrayList<Currency> currencies = Currency.init();
        Currency usd = currencies.get(0);
        Currency eur = currencies.get(1);

        assertThrows(IllegalArgumentException.class, () -> {
            // Negative amount
            MainWindow.convert(usd.getName(), eur.getName(), currencies, -100.0);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            // Out of bounds amount (from specifications [0, 1 000 000] )
            MainWindow.convert(usd.getName(), eur.getName(), currencies, 1_000_001.0);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            // Non-existent currency
            MainWindow.convert(usd.getName(), "N'importe quoi", currencies, 100.0);
            MainWindow.convert("N'importe quoi", eur.getName(), currencies, 100.0);
        });
    }

    /**
     * Test the conversion with a non-static currency name.
     * There is a bug in the code where the currency name is compared
     * with a double equals (==) instead of a .equals() method.
     * This test is here to show that the bug is still present.
     * white box test
     */
    @Test
    public void testStringCompareBug() {
        ArrayList<Currency> currencies = Currency.init();
        String currency1 = new String("Euro");  // force a new memory allocation
        String currency2 = new String("Euro");

        double value = MainWindow.convert(currency1, currency2, currencies, 100.0);
        assertEquals(100.0, value);
    }

    /**
     * Test the conversion with an empty list of currencies.
     * This should return 0.0
     * white box test
     */
    @Test
    public void testCurrenciesEmpty() {
        ArrayList<Currency> currencies = new ArrayList<>();
        String currency1 = "Euro";
        String currency2 = "US Dollar";

        double value = MainWindow.convert(currency1, currency2, currencies, 100.0);
        assertEquals(0.0, value);
    }

    /**
     * Test the conversion with null value for currencies
     * This should return 0.0
     * white box test
     */
    @Test
    public void testCurrencyArgNull() {
        ArrayList<Currency> currencies = Currency.init();

        double value = MainWindow.convert(null, "US Dollar", currencies, 100.0);
        assertEquals(0.0, value);

        value = MainWindow.convert("US Dollar", null, currencies, 100.0);
        assertEquals(0.0, value);

        value = MainWindow.convert(null, null, currencies, 100.0);
        assertEquals(0.0, value);
    }
}
