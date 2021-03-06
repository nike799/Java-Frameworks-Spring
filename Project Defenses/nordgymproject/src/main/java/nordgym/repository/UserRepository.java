package nordgym.repository;

import nordgym.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    @Query(value = "SELECT * FROM users u ORDER by u.first_name ASC,u.last_name ASC", nativeQuery = true)
    List<User> getAllUsersOrderedByName();

    Optional<User> findBySubscriptionNumberIsLike(String subscriptionNumber);

}
