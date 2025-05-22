package de.keywork.backend.repository;

import de.keywork.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> getUserByUsername(String username);
    boolean existsByUsername(String username);
    @Query(value = "SELECT * FROM `user` u where u.form_data_id = :form_id", nativeQuery = true)
    Optional<User> findByFormDataId(@Param("form_id") long formDataId);
}
