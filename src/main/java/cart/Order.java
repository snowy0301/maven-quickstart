package cart;

import java.util.List;

public class Order {
    private final long userId;
    private final List<CartItem> items;

    public Order(long userId, List<CartItem> items) {
        this.userId = userId;
        this.items = items;
    }

    public long getUserId() {
        return userId;
    }

    public List<CartItem> getItems() {
        return items;
    }
}
