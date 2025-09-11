package cart;

import java.util.Optional;

public class CartService {
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final CartRepository cartRepository;

    public CartService(UserRepository userRepository, BookRepository bookRepository, CartRepository cartRepository) {
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.cartRepository = cartRepository;
    }

    public boolean addBookToCart(long userId, long bookId, int quantity) {
        // add in requirement 2.4
        if (!userRepository.existsById(userId)) {
            return false;
        }

        Optional<Book> optBook = bookRepository.findById(bookId);
        if (optBook.isEmpty()) {
            return false;
        }

        Book book = optBook.get();

        int currentQuantity = cartRepository.getQuantityInCart(userId, bookId);
        int newQuantity = quantity + currentQuantity;

        if (book.getAvailableCopies() < newQuantity) {
            return false;
        }

        return cartRepository.addToCart(userId, bookId, newQuantity);
    }

    public boolean removeBookFromCart(long userId, long bookId) {
        if (!userRepository.existsById(userId)) {
            return false;
        }

        if (!cartRepository.hasCart(userId)) {
            return false;
        }

        return cartRepository.removeFromCart(userId, bookId);
    }

    public boolean clearCart(long userId) {
        if (!userRepository.existsById(userId)) {
            return false;
        }

        if (!cartRepository.hasCart(userId)) {
            return false;
        }

        return cartRepository.clearCart(userId);
    }

    public boolean decreaseCartItemQuantity(long userId, long bookId) {
        if (!userRepository.existsById(userId)) {
            return false;
        }

        if (!cartRepository.hasCart(userId)) {
            return false;
        }

        int currentQuantity = cartRepository.getQuantityInCart(userId, bookId);

        if (currentQuantity <= 0) {
            return false; // item not in cart
        }

        if (currentQuantity == 1) {
            return cartRepository.removeFromCart(userId, bookId);
        } else {
            return cartRepository.updateQuantity(userId, bookId, currentQuantity - 1);
        }
    }

}
