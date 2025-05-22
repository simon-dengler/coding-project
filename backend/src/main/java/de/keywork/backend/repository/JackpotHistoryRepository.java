package de.keywork.backend.repository;

import de.keywork.backend.entity.JackpotHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JackpotHistoryRepository extends JpaRepository<JackpotHistory, Long> {
}
