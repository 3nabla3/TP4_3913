package test;

import currencyConverter.Currency;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class CurrencyTest {

    /**
     * Test the conversion of a value with a rate of 1.0
     * This should not change the value
     * black box test
     */
    @Test
    public void testRate1() {
        double rate = 1.0;
        for (double value = 1.0; value < 10; value += 1.0) {
            assertEquals(value, Currency.convert(value, rate));
        }
    }

    /**
     * Test the conversion of a value with a rate of 0.0
     * This should always return 0.0
     * black box test
     */
    @Test
    public void testRate0() {
        double rate = 0.0;
        for (double value = 0.0; value < 10; value += 1.0) {
            assertEquals(0.0, Currency.convert(value, rate));
        }
    }

    /**
     * Test the conversion of a value with a negative rate
     * This should return a negative value
     * black box test
     */
    @Test
    public void testNegativeValue() {
        double rate = 1.5;
        double value = -1.0;
        assertEquals(-1.5, Currency.convert(value, rate));
    }

    /**
     * Test the conversion of a negative value
     * This should return a negative value
     * black box test
     */
    @Test
    public void testNegativeRate() {
        double rate = -1.5;
        double value = 1.0;
        assertEquals(-1.5, Currency.convert(value, rate));
    }

    /**
     * Test the conversion of a value with fractions of cents
     * This should round the result to 2 decimals
     * white box test
     */
    @Test
    public void testConvertWithRounding() {
        Double amount = 123.456789;
        Double exchangeValue = 1.23456789;
        assertEquals(152.42, Currency.convert(amount, exchangeValue));
    }

    /**
     * Test the conversion with null values
     * This should throw a NullPointerException
     * white box test
     */
    @Test
    public void testNullValues() {
        assertThrows(NullPointerException.class, () -> Currency.convert(null, 1.5d));
        assertThrows(NullPointerException.class, () -> Currency.convert(1.5d, null));
    }
}
