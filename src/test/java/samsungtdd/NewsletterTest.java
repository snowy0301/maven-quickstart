package samsungtdd;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class NewsletterTest {

    /*
    You are building a feature that allows users to sub to a newsletter using their email. the
    goal is to follow TDD to implement this feature step by step

    Functional requirements:
    - User can input an email address to subscribe -> subscribe()
    - The email must be validated to proper format -> 1 test to validate email
    - If the email already exist in the system, it should not be added again -> throw message
    by exception -> 1 test throw for existing emails
    - If the email is valid and not already subscribed, return true -> 1 test for good valid email,
    with no duplicate
    - A confirmation message should be returned based on the outcome

    System = List :)))))
    */
    Newsletter newsletter;

    @BeforeEach
    void setUp() {
        newsletter = new Newsletter();
    }

    @Test
    void should_subscribe_successfully_with_valid_email() {
        boolean result = newsletter.subscribe("user@gmail.com");

        assertEquals("Subscribe successfully", result ? "Subscribe successfully" : "");
    }


    @Test
    void should_throw_with_invalid_email() {
        String email = "email";

        Exception exception = assertThrows(
                InvalidEmailFormatException.class,
                () -> newsletter.subscribe(email)
        );

        assertEquals("Invalid email format.", exception.getMessage());
    }

    @Test
    void should_throw_with_duplicate_email() {
        String email = "cachuacute@gmail.com";

        Exception exception = assertThrows(
                EmailAlreadySubscribedException.class,
                () -> newsletter.subscribe(email)
        );

        assertEquals("Email already subscribed.", exception.getMessage());
    }
}
