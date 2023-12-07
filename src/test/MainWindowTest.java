package test;

import currencyConverter.Currency;
import currencyConverter.MainWindow;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class MainWindowTest {
    /**
     * Helper function to convert a short name to a long name
     *
     * @param shortName  the short name of the currency (ex: USD)
     * @param currencies the list of currencies to search in
     * @return the long name of the currency (ex: US Dollar)
     */
    private String convertShortNameToLongName(String shortName, ArrayList<Currency> currencies) {
        for (Currency currency : currencies) {
            if (currency.getShortName().equals(shortName)) {
                return currency.getName();
            }
        }
        return null;
    }

    /**
     * Test that all currencies from the specifications are implemented,
     * and that the conversion is valid. This means that if we convert
     * from x to x, the value remain constant, and if we convert from x to y,
     * the value should be a positive double.
     * black box test
     */
    @Test
    public void testCurrenciesInSpecs() {
        ArrayList<String> currenciesFromSpecs = new ArrayList<>(Arrays.asList("USD", "CAD", "GBP", "EUR", "CHF", "AUD"));
        ArrayList<Currency> currencies = Currency.init();

        for (String curr1 : currenciesFromSpecs) {
            String longName1 = convertShortNameToLongName(curr1, currencies);
            for (String curr2 : currenciesFromSpecs) {
                String longName2 = convertShortNameToLongName(curr2, currencies);
                Double value = MainWindow.convert(longName1, longName2, currencies, 100.0);

                if (curr1.equals(curr2)) assertEquals(100.0, value);  // same currency, same value
                else assertTrue(value > 0d);  // different currency, positive value
            }
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
            MainWindow.convert("Does not exist", eur.getName(), currencies, 100.0);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            // Non-existent currency
            MainWindow.convert(usd.getName(), "Does not exist", currencies, 100.0);
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
        String currency1 = "Euro";  // force a new memory allocation
        String currency2 = "Euro";

        double value = MainWindow.convert(currency1, currency2, currencies, 100.0);
        assertEquals(100.0, value);
    }


    /**
     * Test the conversion with currencies at different locations
     * in the array. This forces the program to test the most important
     * paths in the code.
     * white box test
     */
    @Test
    public void testIChemins() {
        ArrayList<Currency> currencies = Currency.init();
        // test first, middle, and last currency
        int[] indicesToTest = {0, currencies.size() / 2, currencies.size() - 1};

        for (int i : indicesToTest) {
            for (int j : indicesToTest) {
                String currency1 = currencies.get(i).getName();
                String currency2 = currencies.get(j).getName();

                double value = MainWindow.convert(currency1, currency2, currencies, 100.0);
                assertTrue(value > 0d);
            }
        }
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
