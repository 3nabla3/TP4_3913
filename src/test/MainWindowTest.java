package test;

import currencyConverter.Currency;
import currencyConverter.MainWindow;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Random;

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

    @Test
    public void testIllegalArgs(){
        ArrayList<Currency> currencies = Currency.init();
        Random random = new Random();
        Currency currencyOne = currencies.get(random.nextInt(6));
        Currency currencyTwo = currencies.get(random.nextInt(6));
        assertThrows(IllegalArgumentException.class, () -> {
            // Negative amount
            MainWindow.convert(currencyOne.getName(), currencyTwo.getName(), currencies, -100.0);
            // Out of bounds amount (from specifications [0, 1 000 000] )
            MainWindow.convert(currencyOne.getName(), currencyTwo.getName(), currencies, 1000001.0);
            // Non-existent currency
            MainWindow.convert(currencyOne.getName(), "N'importe quoi", currencies, 100.0);
        });
    }



}
