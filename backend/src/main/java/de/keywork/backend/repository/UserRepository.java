package de.keywork.backend.repository;

import de.keywork.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> getUserByUsername(String username);
    boolean existsByUsername(String username);
}
