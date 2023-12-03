package test;

import currencyConverter.Currency;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class CurrencyTest {
    @Test
    public void testConvert() {
        // make sure a rate of 1.0 does not change the value
        double rate = 1.0;
        for (double value = 1.0; value < 10; value += 1.0) {
            assertEquals(Currency.convert(value, rate), value);
        }

        // make sure a rate of 0.0 always returns 0.0
        rate = 0.0;
        for (double value = 0.0; value < 10; value += 1.0) {
            assertEquals(Currency.convert(value, rate), 0.0);
        }
    }
}
