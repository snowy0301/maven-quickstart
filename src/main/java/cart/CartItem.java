package cart;

public class CartItem {
    private final long id;
    private final Book book;
    private final int quantity;

    public CartItem(long id, Book book, int quantity) {
        this.id = id;
        this.book = book;
        this.quantity = quantity;
    }

    public long getId() {
        return id;
    }

    public Book getBook() {
        return book;
    }

    public int getQuantity() {
        return quantity;
    }
}
