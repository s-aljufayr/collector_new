package tech.wakeb.collector.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tech.wakeb.collector.model.User;

import java.util.Collection;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Page<User> findAll(Pageable pageable);
    Collection<User> findByUsernameOrEmail(String username, String email);
    Optional<User> findByEmail(String email);
    Collection<User> findByUsernameStartsWith(String username);
    Collection<User> findByUsernameEndsWith(String username);
    Collection<User> findByUsernameNotContaining(String username);
//    Collection<User> findByUsernameEndsWithOrNameEndWith(String username, String name);
    Collection<User> findByUsernameContaining(String username);
    @Query(value = "SELECT u FROM User u WHERE u.name LIKE %:name%")
    Collection<User> searchByName(@Param("name") String name);

}
