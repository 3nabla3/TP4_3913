package test;

import currencyConverter.Currency;
import currencyConverter.MainWindow;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MainWindowTest {
    @Test
    public void testConvert() {
        ArrayList<Currency> currencies = Currency.init();
        for (Currency currency : currencies) {
            Double value = MainWindow.convert(currency.getName(), currency.getName(), currencies, 1.0);
            assertEquals(value, 1.0);
        }
    }

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
        String currencyName = "US";
        currencyName += "D";

        double value = MainWindow.convert(currencyName, "USD", currencies, 100.0);
        assertEquals(100.0, value);
    }
}
