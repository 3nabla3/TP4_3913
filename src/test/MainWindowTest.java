package test;

import currencyConverter.Currency;
import currencyConverter.MainWindow;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class MainWindowTest {
    @Test
    public void testConvert() {
        ArrayList<Currency> currencies = Currency.init();
        for (Currency currency : currencies) {
            Double value = MainWindow.convert(currency.getName(), currency.getName(), currencies, 1.0);
            assertEquals(value, 1.0);
        }
    }
}
