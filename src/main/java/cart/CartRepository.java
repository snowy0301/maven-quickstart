package cart;

import java.util.List;

public interface CartRepository {

    // Returns true if the operation succeeded.
    boolean addToCart(long userId, long bookId, int quantity);

    int getQuantityInCart(long userId, long bookId);

    boolean hasCart(long userId);

    boolean removeFromCart(long userId, long bookId);

    boolean clearCart(long userId);

    boolean updateQuantity(long userId, long bookId, int newQuantity);

    List<CartItem> getCartItems(long userId);
}
