package de.keywork.backend.service;

import de.keywork.backend.entity.FormData;
import de.keywork.backend.entity.JackpotHistory;
import de.keywork.backend.entity.Jackpot;
import de.keywork.backend.entity.Result;
import de.keywork.backend.repository.JackpotHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@Transactional
public class JackpotHistoryService {
    private final JackpotHistoryRepository jackpotHistoryRepository;
    private final JackpotService jackpotService;
    private final ResultService resultService;
    private final FormDataService formDataService;

    public long saveJackpotHistory(long formId, long resultId, long jackpotId) throws HttpStatusCodeException {
        JackpotHistory jackpotHistory = new JackpotHistory();
        Jackpot jackpot = jackpotService.requireById(jackpotId);
        jackpotHistory.setJackpot(jackpot);
        Result result = resultService.requireById(resultId);
        jackpotHistory.setResult(result);
        FormData formData = formDataService.requireById(formId);
        jackpotHistory.setFormData(formData);
        return jackpotHistoryRepository.save(jackpotHistory).getId();
    }

    public JackpotHistory getJackpotFromHistory(long historyId) throws ResponseStatusException{
        JackpotHistory jackpotHistory = jackpotHistoryRepository.findById(historyId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return jackpotHistory;
    }
}
