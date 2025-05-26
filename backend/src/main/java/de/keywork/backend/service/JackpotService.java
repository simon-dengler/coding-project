package de.keywork.backend.service;

import de.keywork.backend.entity.Jackpot;
import de.keywork.backend.repository.JackpotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

/**
 * Service for read operations on {@link Jackpot} objects.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class JackpotService {
    private final JackpotRepository jackpotRepository;

    /**
     * Gets {@link Jackpot} by id.
     * @param id
     * @return {@link Jackpot} object
     * @throws ResponseStatusException if object with id not present
     */
    public Jackpot requireById(long id) throws ResponseStatusException{
        return jackpotRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Jackpot for this id not found"));
    }
}
