package de.keywork.backend.service;

import de.keywork.backend.dto.JackpotDto;
import de.keywork.backend.dto.ResultDto;
import de.keywork.backend.entity.FormData;
import de.keywork.backend.entity.Jackpot;
import de.keywork.backend.entity.Result;
import de.keywork.backend.repository.JackpotRepository;
import de.keywork.backend.repository.ResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.NoSuchElementException;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Service that provides write and read functionality for {@link Result} objects.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class ResultService {
    private final JackpotRepository jackpotRepository;
    private final ResultRepository resultRepository;
    private final FormDataService formDataService;

    /**
     * Convert a {@link ResultDto} to a {@link Result} object and store it in the DB.
     * @param dto
     * @return id of the created object
     */
    public long saveResult(ResultDto dto){
        Result result = new Result();
        result.setCategory(dto.getCategory());
        result.setQuestion(dto.getQuestion());
        result.setCorrectAnswer(dto.getCorrectAnswer());
        result.setAnsweredCorrectly(dto.isAnsweredCorrectly());
        FormData formData = formDataService.requireById(dto.getFormDataId());
        result.setFormData(formData);
        return resultRepository.save(result).getId();
    }

    /**
     * Fetches a random jackpot from the DB, as long as one is present there.
     * @return random {@link Jackpot}
     * @throws NoSuchElementException if DB is empty or jackpot with random id is missing
     */
    public JackpotDto getRandomJackpot() throws NoSuchElementException {
        long min = 1;
        long max = jackpotRepository.findMaxId().orElseThrow(() -> new NoSuchElementException("Table is possibly empty."));
        long random = ThreadLocalRandom.current().nextLong(min, max + 1);
        Jackpot jackpot = jackpotRepository.findById(random).orElseThrow(() -> new NoSuchElementException(String.format("Jackpot with id %d not found.", random)));
        JackpotDto dto = new JackpotDto();
        dto.setId(jackpot.getId());
        dto.setName(jackpot.getName());
        dto.setDescription(jackpot.getDescription());
        return dto;
    }

    /**
     * Fetches {@link Result} by id.
     * @param id
     * @return {@link Result} object
     * @throws ResponseStatusException if object with id is not present
     */
    public Result requireById(long id) throws ResponseStatusException{
        return resultRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Result with this id not found."));
    }
}
