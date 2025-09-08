package samsungtdd;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PrimeNumberTest {
    @Test
    public void should_return_false_with_non_prime_number() {
        int number = 12;
        boolean expected = false;

        boolean actual = PrimeNumber.check(number);

        assertEquals(expected, actual);
    }
}
