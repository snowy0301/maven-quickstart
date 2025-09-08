package samsungtdd;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FizzBuzzTest {

    @Test
    public void should_return_FizzBuzz_of_length_5() {
        int number = 5;
        List<String> expected = List.of(new String[]{"1", "2", "Fizz", "4", "Buzz"});

        List<String> actual = FizzBuzz.fizzBuzz(number);

        assertEquals(expected, actual);
    }

    @Test
    public void should_return_FizzBuzz_of_length_12() {
        int number = 12;
        List<String> expected = List.of(new String[]{"1", "2", "Fizz", "4", "Buzz", "Fizz", "7", "8", "Fizz", "Buzz", "11", "Fizz"});

        List<String> actual = FizzBuzz.fizzBuzz(number);

        assertEquals(expected, actual);
    }

    @Test
    public void should_return_FizzBuzz_of_length_15() {
        int number = 15;
        List<String> expected = List.of(new String[]{"1", "2", "Fizz", "4", "Buzz", "Fizz", "7", "8", "Fizz", "Buzz", "11", "Fizz", "13", "14", "FizzBuzz"});

        List<String> actual = FizzBuzz.fizzBuzz(number);

        assertEquals(expected, actual);
        }

    @Test
    public void should_throw_with_number_less_than_or_equal_to_0() {
        int number = 0;

        assertThrows(ArrayIndexOutOfBoundsException.class, () ->  FizzBuzz.fizzBuzz(number));
    }
}
