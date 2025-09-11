package cart;

import java.util.Optional;

public interface BookRepository {
    Optional<Book> findById(long bookId);
}

