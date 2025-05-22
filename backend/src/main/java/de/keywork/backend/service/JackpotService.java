package de.keywork.backend.service;

import de.keywork.backend.dto.JackpotDto;
import de.keywork.backend.entity.Jackpot;
import de.keywork.backend.repository.JackpotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@Transactional
public class JackpotService {
    private final JackpotRepository jackpotRepository;

    public Jackpot requireById(long id){
        return jackpotRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Result for this id not found"));
    }

    public JackpotDto fetchJackpot(long id) throws ResponseStatusException{
        Jackpot jackpot = requireById(id);
        JackpotDto dto = new JackpotDto();
        dto.setId(jackpot.getId());
        dto.setName(jackpot.getName());
        dto.setDescription(jackpot.getDescription());
        return dto;
    }
}
