package de.keywork.backend.repository;

import de.keywork.backend.entity.Jackpot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


public interface JackpotRepository extends JpaRepository<Jackpot, Long> {
    @Query(value = "SELECT MAX(id) FROM jackpots", nativeQuery = true)
    Optional<Long> findMaxId();
}