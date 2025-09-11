package cart;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findByUsername(String username);

    boolean existsById(long userId);
}