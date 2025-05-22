package de.keywork.backend.repository;

import de.keywork.backend.entity.Result;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResultRepository extends JpaRepository<Result, Long> {
}
