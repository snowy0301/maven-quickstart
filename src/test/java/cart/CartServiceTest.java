package cart;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CartServiceTest {

    private UserRepository userRepository;
    private BookRepository bookRepository;
    private CartRepository cartRepository;
    private CartService cartService;

    @BeforeEach
    void setup() {
        userRepository = mock(UserRepository.class);
        bookRepository = mock(BookRepository.class);
        cartRepository = mock(CartRepository.class);
        cartService = new CartService(userRepository, bookRepository, cartRepository);
    }

    /*** Start function 2: Add book to cart ***/
    @Test
    void should_add_book_to_cart_if_enough_stock_available() {
        long userId = 1L;
        long bookId = 101L;
        int quantity = 2;

        Book book = new Book(bookId, "Hạnh phúc của một tang gia", "Vũ Trọng Phụng", 5, 5);

        when(userRepository.existsById(userId)).thenReturn(true);        // stub to pass requirement 2.4
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(cartRepository.addToCart(userId, bookId, quantity)).thenReturn(true);

        boolean result = cartService.addBookToCart(userId, bookId, quantity);

        assertThat(result, is(true));

        verify(bookRepository).findById(bookId);
        verify(cartRepository).addToCart(userId, bookId, quantity);
    }

    @Test
    void should_fail_if_book_does_not_exist() {
        long userId = 1L;
        long bookId = 999L;

        when(userRepository.existsById(userId)).thenReturn(true);
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        boolean result = cartService.addBookToCart(userId, bookId, 1);

        assertThat(result, is(false));
    }

    @Test
    void should_reject_adding_to_cart_if_quantity_exceeds_stock() {
        long userId = 1L;
        long bookId = 101L;
        int quantityRequested = 10;

        Book book = new Book(bookId, "Hạnh phúc của một tang gia", "Vũ Trọng Phụng", 5, 5);

        when(userRepository.existsById(userId)).thenReturn(true);        // stub to pass requirement 2.4
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));

        // addToCart should never be called here since out of stock
        boolean result = cartService.addBookToCart(userId, bookId, quantityRequested);

        assertThat(result, is(false));

        verify(bookRepository).findById(bookId);
        verify(cartRepository, never()).addToCart(anyLong(), anyLong(), anyInt());
    }

    @Test
    void should_increase_quantity_if_book_already_in_cart() {
        long userId = 1L;
        long bookId = 101L;

        // 5 copies available
        Book book = new Book(bookId, "Dune", "Herbert", 5, 5);

        when(userRepository.existsById(userId)).thenReturn(true);        // stub to pass requirement 2.4
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(cartRepository.getQuantityInCart(userId, bookId)).thenReturn(2);        // stub: currently has 2 copies in cart

        // Expect addToCart to be called with quantity 3
        when(cartRepository.addToCart(userId, bookId, 3)).thenReturn(true);

        boolean result = cartService.addBookToCart(userId, bookId, 1);

        assertThat(result, is(true));

        // mock all behaviors
        verify(bookRepository).findById(bookId);
        verify(cartRepository).getQuantityInCart(userId, bookId);
        verify(cartRepository).addToCart(userId, bookId, 3);
    }

    @Test
    void should_reject_adding_book_to_cart_if_user_does_not_exist() {
        long userId = 999L;  // non-existing user
        long bookId = 101L;
        int quantity = 1;

        // Assume we have a UserRepository mock
        when(userRepository.existsById(userId)).thenReturn(false);

        // BookRepository might still have the book, but user check comes first
        boolean result = cartService.addBookToCart(userId, bookId, quantity);

        assertThat(result, is(false));

        verify(userRepository).existsById(userId);
        verify(bookRepository, never()).findById(anyLong());
        verify(cartRepository, never()).addToCart(anyLong(), anyLong(), anyInt());
    }
    /*** End function 2 ***/

    /*** Start function 3: Remove or clear cart ***/
    @Test
    void should_remove_book_from_cart_if_present() {
        long userId = 1L;
        long bookId = 101L;

        // Mock: user exists and has a cart
        when(userRepository.existsById(userId)).thenReturn(true);
        when(cartRepository.hasCart(userId)).thenReturn(true);
        when(cartRepository.removeFromCart(userId, bookId)).thenReturn(true);

        boolean result = cartService.removeBookFromCart(userId, bookId);

        assertThat(result, is(true));

        verify(userRepository).existsById(userId);
        verify(cartRepository).hasCart(userId);
        verify(cartRepository).removeFromCart(userId, bookId);
    }

    @Test
    void should_fail_to_remove_if_user_does_not_exist() {
        long userId = 1L;
        long bookId = 101L;

        when(userRepository.existsById(userId)).thenReturn(false);

        boolean result = cartService.removeBookFromCart(userId, bookId);

        assertThat(result, is(false));
    }

    @Test
    void should_fail_to_remove_if_cart_does_not_exist() {
        long userId = 1L;
        long bookId = 101L;

        when(userRepository.existsById(userId)).thenReturn(true);
        when(cartRepository.hasCart(userId)).thenReturn(false);

        boolean result = cartService.removeBookFromCart(userId, bookId);

        assertThat(result, is(false));
    }

    @Test
    void should_clear_entire_cart_if_cart_exists() {
        long userId = 1L;

        // Mock: user exists and has a cart
        when(userRepository.existsById(userId)).thenReturn(true);
        when(cartRepository.hasCart(userId)).thenReturn(true);
        when(cartRepository.clearCart(userId)).thenReturn(true);

        boolean result = cartService.clearCart(userId);

        assertThat(result, is(true));

        verify(userRepository).existsById(userId);
        verify(cartRepository).hasCart(userId);
        verify(cartRepository).clearCart(userId);
    }

    @Test
    void should_fail_to_clear_if_user_does_not_exist() {
        long userId = 1L;

        when(userRepository.existsById(userId)).thenReturn(false);

        boolean result = cartService.clearCart(userId);

        assertThat(result, is(false));
    }

    @Test
    void should_fail_to_clear_if_cart_does_not_exist() {
        long userId = 1L;

        when(userRepository.existsById(userId)).thenReturn(true);
        when(cartRepository.hasCart(userId)).thenReturn(false);

        boolean result = cartService.clearCart(userId);

        assertThat(result, is(false));
    }

    @Test
    void should_decrease_quantity_or_remove_item_if_quantity_is_one() {
        long userId = 1L;
        long bookId = 101L;

        when(userRepository.existsById(userId)).thenReturn(true);
        when(cartRepository.hasCart(userId)).thenReturn(true);

        // Case 1: Quantity is greater than 1 -> expect quantity decreased
        when(cartRepository.getQuantityInCart(userId, bookId)).thenReturn(3);
        when(cartRepository.updateQuantity(userId, bookId, 2)).thenReturn(true);

        boolean resultDecrease = cartService.decreaseCartItemQuantity(userId, bookId);

        assertThat(resultDecrease, is(true));
        verify(cartRepository).updateQuantity(userId, bookId, 2);

        // Reset mocks for next case
        reset(cartRepository);

        // Case 2: Quantity is 1 -> expect item removed
        when(userRepository.existsById(userId)).thenReturn(true);
        when(cartRepository.hasCart(userId)).thenReturn(true);
        when(cartRepository.getQuantityInCart(userId, bookId)).thenReturn(1);
        when(cartRepository.removeFromCart(userId, bookId)).thenReturn(true);

        boolean resultRemove = cartService.decreaseCartItemQuantity(userId, bookId);

        assertThat(resultRemove, is(true));
        verify(cartRepository).removeFromCart(userId, bookId);
    }

    @Test
    void should_fail_to_decrease_if_user_does_not_exist() {
        long userId = 1L;
        long bookId = 101L;

        when(userRepository.existsById(userId)).thenReturn(false);

        boolean result = cartService.decreaseCartItemQuantity(userId, bookId);

        assertThat(result, is(false));
    }

    @Test
    void should_fail_to_decrease_if_cart_does_not_exist() {
        long userId = 1L;
        long bookId = 101L;

        when(userRepository.existsById(userId)).thenReturn(true);
        when(cartRepository.hasCart(userId)).thenReturn(false);

        boolean result = cartService.decreaseCartItemQuantity(userId, bookId);

        assertThat(result, is(false));
    }

    @Test
    void should_fail_to_decrease_if_item_quantity_is_zero_or_less() {
        long userId = 1L;
        long bookId = 101L;

        when(userRepository.existsById(userId)).thenReturn(true);
        when(cartRepository.hasCart(userId)).thenReturn(true);
        when(cartRepository.getQuantityInCart(userId, bookId)).thenReturn(0);

        boolean result = cartService.decreaseCartItemQuantity(userId, bookId);

        assertThat(result, is(false));
    }

    /*** End function 3 ***/
}
