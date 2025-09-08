package samsungtdd;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FizzBuzzTest {
    @Test
    public void should_return_FizzBuzz_of_length_5() {
        int number = 5;
        String expected = "1 2 Fizz 4 Buzz";

        String actual = FizzBuzz.fizzBuzz(number);

        assertEquals(expected, actual);
    }

    @Test
    public void should_return_FizzBuzz_of_length_12() {
        int number = 12;
        String expected = "1 2 Fizz 4 Buzz Fizz 7 8 Fizz Buzz 11 Fizz";

        String actual = FizzBuzz.fizzBuzz(number);

        assertEquals(expected, actual);
    }

    @Test
    public void should_return_FizzBuzz_of_length_15() {
        int number = 15;
        String expected = "1 2 Fizz 4 Buzz Fizz 7 8 Fizz Buzz 11 Fizz 13 14 FizzBuzz";

        String actual = FizzBuzz.fizzBuzz(number);

        assertEquals(expected, actual);
        }

    @Test
    public void should_return_none_with_number_less_than_or_equal_to_0() {
        int number = 0;
        String expected = "";
        String actual = FizzBuzz.fizzBuzz(number);

        assertEquals(expected, actual);
    }
}
