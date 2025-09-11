package refactor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BankInterestCalculatorTest {

    private BankInterestCalculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new BankInterestCalculator();
    }

    @Test
    void should_throw_with_invalid_month_below_0() {
        Exception monthBelow1 = assertThrows(IllegalArgumentException.class,
                () -> calculator.calculateInterest(
                10000, 0, BankInterestCalculator.Type.STANDARD));

        assertEquals("Months must be between 1 and 12", monthBelow1.getMessage());
    }

    @Test
    void should_throw_with_invalid_month_over_12() {
        Exception monthOver12 = assertThrows(IllegalArgumentException.class,
                () -> calculator.calculateInterest(
                        10000, 100, BankInterestCalculator.Type.STANDARD));

        assertEquals("Months must be between 1 and 12", monthOver12.getMessage());
    }

    @Test
    void should_throw_with_invalid_principal() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> calculator.calculateInterest(
                        0, 12, BankInterestCalculator.Type.STANDARD));
        assertEquals("Money must be greater than 0.", exception.getMessage());
    }

    @Test
    void should_throw_with_invalid_interest() {}

    @Test
    void test_standard_type_interest_under_6_months() {
        double expected = 10000 * 0.03 * 5 / 12;
        assertEquals(expected, calculator.calculateInterest(10000, 5, BankInterestCalculator.Type.STANDARD));
    }

    @Test
    void test_standard_type_interest_under_12_months() {
        double expected = 10000 * 0.045 * 9 / 12;
        assertEquals(expected, calculator.calculateInterest(10000, 9, BankInterestCalculator.Type.STANDARD));
    }

    @Test
    void test_standard_type_interest_at_least_12_months() {
        double expected = 10000 * 0.06 * 12 / 12;
        assertEquals(expected, calculator.calculateInterest(10000, 12, BankInterestCalculator.Type.STANDARD));
    }

    @Test
    void test_premium_type_interest_under_6_months() {
        double expected = 10000 * 0.05 * 5 / 12;
        assertEquals(expected, calculator.calculateInterest(10000, 5, BankInterestCalculator.Type.PREMIUM));
    }

    @Test
    void test_premium_type_interest_under_12_months() {
        double expected = 10000 * 0.065 * 9 / 12;
        assertEquals(expected, calculator.calculateInterest(10000, 9, BankInterestCalculator.Type.PREMIUM));
    }

    @Test
    void test_premium_type_interest_at_least_12_months() {
        double expected = 10000 * 0.08 * 12 / 12;
        assertEquals(expected, calculator.calculateInterest(10000, 12, BankInterestCalculator.Type.PREMIUM));
    }

    @Test
    void test_vip_type_interest_under_6_months() {
        double expected = 10000 * 0.07 * 5 / 12;
        assertEquals(expected, calculator.calculateInterest(10000, 5, BankInterestCalculator.Type.VIP));
    }

    @Test
    void test_vip_type_interest_under_12_months() {
        double expected = 10000 * 0.085 * 9 / 12;
        assertEquals(expected, calculator.calculateInterest(10000, 9, BankInterestCalculator.Type.VIP));
    }

    @Test
    void test_vip_type_interest_at_least_12_months() {
        double expected = 10000 * 0.1 * 12 / 12;
        assertEquals(expected, calculator.calculateInterest(10000, 12, BankInterestCalculator.Type.VIP));
    }
}
