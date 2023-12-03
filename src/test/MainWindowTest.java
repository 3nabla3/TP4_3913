package test;

import currencyConverter.Currency;
import currencyConverter.MainWindow;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
            MainWindow.convert("N'importe quoi", eur.getName() , currencies, 100.0);
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
    public void testCompareBug() {
        ArrayList<Currency> currencies = Currency.init();
        String currency1 = new String("Euro");  // force a new memory allocation
        String currency2 = new String("Euro");

        double value = MainWindow.convert(currency1, currency2, currencies, 100.0);
        assertEquals(100.0, value);
    }
}
