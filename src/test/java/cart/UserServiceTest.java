package cart;

import static org.mockito.Mockito.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.Optional;

import cart.User.UserRole;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    private UserService userService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        userService = new UserService(userRepository);
    }

    /*** Start function 1: User login ***/
    @Test
    void should_login_successfully_with_correct_credentials() {
        User user = new User(
                1L, "john_doe", "password123", "cachuacute@gmail.com"
                , "John Doe", "36B", "0909090909", UserRole.ADMIN);

        when(userRepository.findByUsername("john_doe")).thenReturn(Optional.of(user));

        Optional<User> result = userService.login("john_doe", "password123");

        assertThat(result.isPresent(), is(true));
        assertThat(result.get().getUsername(), is("john_doe"));
    }

    @Test
    void should_fail_to_login_with_wrong_password() {
        User user = new User(
                1L, "john_doe", "password123", "cachuacute@gmail.com"
                , "John Doe", "36B", "0909090909", User.UserRole.ADMIN);

        when(userRepository.findByUsername("john_doe")).thenReturn(Optional.of(user));

        Optional<User> result = userService.login("john_doe", "wrong-password12345");

        assertThat(result.isPresent(), is(false));
    }

    @Test
    void should_fail_to_login_if_user_not_found() {
        when(userRepository.findByUsername("abcxyz")).thenReturn(Optional.empty());

        Optional<User> result = userService.login("abcxyz", "abcxyz123");

        assertThat(result.isPresent(), is(false));
    }

    @Test
    void should_return_user_role_on_successful_login() {
        User user = new User(
                1L, "john_doe", "password123", "cachuacute@gmail.com"
                , "John Doe", "36B", "0909090909", UserRole.ADMIN);

        when(userRepository.findByUsername("john_doe")).thenReturn(Optional.of(user));

        Optional<User> result = userService.login("john_doe", "password123");

        assertThat(result.isPresent(), is(true));
        assertThat(result.get().getRole(), is(UserRole.ADMIN));
    }
    /*** End function 1 ***/
}
