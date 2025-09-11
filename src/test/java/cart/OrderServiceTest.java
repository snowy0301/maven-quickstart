package cart;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/***
 * OrderServiceTest contains tests for function 4: Checkout order
 */
class OrderServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private OrderRepository orderRepository;

    private OrderService orderService;

    private List<CartItem> createSampleCartItems() {
        Book book1 = new Book(101L, "Hạnh phúc của một tang gia", "Vũ Trọng Phụng", 5, 5);
        Book book2 = new Book(102L, "Future Shock", "Alvin Toffler", 10, 10);
        CartItem item1 = new CartItem(1L, book1, 2);
        CartItem item2 = new CartItem(2L, book2, 1);
        return List.of(item1, item2);
    }

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        orderService = new OrderService(userRepository, cartRepository, orderRepository);
    }

    @Test
    void should_not_allow_checkout_if_cart_is_empty() {
        long userId = 1L;

        when(userRepository.existsById(userId)).thenReturn(true);
        when(cartRepository.hasCart(userId)).thenReturn(true);
        when(cartRepository.getCartItems(userId)).thenReturn(Collections.emptyList());

        boolean result = orderService.checkout(userId);

        assertThat(result, is(false));

        verify(userRepository).existsById(userId);
        verify(cartRepository).hasCart(userId);
        verify(cartRepository).getCartItems(userId);
    }

    @Test
    void should_create_order_from_cart_items_on_checkout() {
        long userId = 1L;
        List<CartItem> cartItems = createSampleCartItems();

        when(userRepository.existsById(userId)).thenReturn(true);
        when(cartRepository.hasCart(userId)).thenReturn(true);
        when(cartRepository.getCartItems(userId)).thenReturn(cartItems);

        when(orderRepository.save(any(Order.class))).thenReturn(true);

        boolean result = orderService.checkout(userId);

        assertThat(result, is(true));
        verify(orderRepository).save(
                argThat(order -> order.getUserId() == userId
                        && order.getItems().size() == cartItems.size()));
    }

    @Test
    void should_reduce_stock_after_successful_checkout() {
        long userId = 1L;
        List<CartItem> cartItems = createSampleCartItems();

        // Capture original stock
        Map<Long, Integer> originalStock = cartItems.stream()
                .collect(Collectors.toMap(
                        item -> item.getBook().getId(),
                        item -> item.getBook().getAvailableCopies()
                ));

        when(userRepository.existsById(userId)).thenReturn(true);
        when(cartRepository.hasCart(userId)).thenReturn(true);
        when(cartRepository.getCartItems(userId)).thenReturn(cartItems);
        when(orderRepository.save(any(Order.class))).thenReturn(true);

        boolean result = orderService.checkout(userId);

        assertThat(result, is(true));

        for (CartItem item : cartItems) {
            Book book = item.getBook();
            int before = originalStock.get(book.getId());
            int expectedAfter = before - item.getQuantity();

            assertThat(book.getAvailableCopies(), is(expectedAfter));
        }
    }

    @Test
    void should_clear_cart_after_successful_checkout() {
        long userId = 1L;
        List<CartItem> cartItems = createSampleCartItems();

        when(userRepository.existsById(userId)).thenReturn(true);
        when(cartRepository.hasCart(userId)).thenReturn(true);
        when(cartRepository.getCartItems(userId)).thenReturn(cartItems);
        when(orderRepository.save(any(Order.class))).thenReturn(true);
        when(cartRepository.clearCart(userId)).thenReturn(true);

        boolean result = orderService.checkout(userId);

        assertThat(result, is(true));

        verify(cartRepository).clearCart(userId);
    }

    @Test
    void should_fail_checkout_if_any_cart_item_stock_is_insufficient() {
        long userId = 1L;

        // Book has only 1 copy available, cart asks for 3
        Book book = new Book(101L, "1984", "George Orwell", 3, 1);
        CartItem item = new CartItem(1L, book, 3);

        List<CartItem> cartItems = List.of(item);

        when(userRepository.existsById(userId)).thenReturn(true);
        when(cartRepository.hasCart(userId)).thenReturn(true);
        when(cartRepository.getCartItems(userId)).thenReturn(cartItems);

        boolean result = orderService.checkout(userId);

        assertThat(result, is(false));

        // Ensure nothing else is called
        verify(orderRepository, never()).save(any());
        verify(cartRepository, never()).clearCart(anyLong());
    }

}
