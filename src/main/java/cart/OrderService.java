package cart;

import java.util.List;

public class OrderService {

    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;

    public OrderService(UserRepository userRepository, CartRepository cartRepository, OrderRepository orderRepository) {
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
        this.orderRepository = orderRepository;
    }

    public boolean checkout(long userId) {
        if (!userRepository.existsById(userId)) {
            return false;
        }

        if (!cartRepository.hasCart(userId)) {
            return false;
        }

        List<CartItem> items = cartRepository.getCartItems(userId);
        if (items.isEmpty()) return false;

        // add in function 4.5 to refactor
        if (!hasSufficientStock(items)) return false;


        // add in function 4.5
        for (CartItem item : items) {
            if (item.getQuantity() > item.getBook().getAvailableCopies()) {
                return false;
            }
        }

        // add in function 4.3
        try {
            for (CartItem item : items) {
                Book book = item.getBook();
                book.reduceAvailableCopies(item.getQuantity());
            }
        } catch (IllegalArgumentException e) {
            // Could handle insufficient stock here, for now return false
            return false;
        }

        // add in function 4.4
        Order order = new Order(userId, items);
        boolean saved = orderRepository.save(order);

        if (saved) {
            cartRepository.clearCart(userId);
        }

        return saved;
    }

    private boolean hasSufficientStock(List<CartItem> items) {
        return items.stream().allMatch(item ->
                item.getQuantity() <= item.getBook().getAvailableCopies()
        );
    }

}
