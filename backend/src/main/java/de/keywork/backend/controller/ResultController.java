package de.keywork.backend.controller;

import de.keywork.backend.dto.JackpotDto;
import de.keywork.backend.dto.ResultDto;
import de.keywork.backend.entity.Jackpot;
import de.keywork.backend.entity.User;
import de.keywork.backend.service.*;
import de.keywork.backend.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.server.ResponseStatusException;

import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
public class ResultController {
    private final ResultService resultService;
    private final JackpotHistoryService jackpotHistoryService;
    private final JackpotService jackpotService;
    private final JwtUtil jwtUtil;
    private final FormDataService formDataService;
    private final UserService userService;

    @PostMapping("/result/{formId}")
    public ResponseEntity<?> saveResult(@PathVariable long formId,
                                              @RequestBody ResultDto resultDto) {
        resultDto.setId(formId);
        try {
            long resultId = resultService.saveResult(resultDto);
            if (resultDto.isAnsweredCorrectly()) {
                JackpotDto jackpotDto = resultService.getRandomJackpot();
                var id = jackpotHistoryService.saveJackpotHistory(formId, resultId, jackpotDto.getId());
                return ResponseEntity.ok(id);
            }
            return ResponseEntity.ok().build();
        } catch (HttpStatusCodeException exception){
            return ResponseEntity.status(exception.getStatusCode()).body(exception.getMessage());
        } catch (NoSuchElementException exception){
            return ResponseEntity.internalServerError().body(exception.getMessage());
        }
    }

    @GetMapping("/result/{jackpotHistoryId}")
    public ResponseEntity<?> fetchJackpot(@PathVariable long jackpotHistoryId,
                                          @RequestHeader("Authorization") String authorization){
        try {
            var token = authorization.substring(7);
            var username = jwtUtil.validateAndExtractUsername(token);
            var jackpotHistory = jackpotHistoryService.getJackpotFromHistory(jackpotHistoryId);
            User user = userService.findUserByFormDataId(jackpotHistory.getFormData().getId());
            if(user.getUsername().equals(username)){
                Jackpot jackpot = jackpotHistory.getJackpot();
                JackpotDto jackpotDto = new JackpotDto();
                jackpotDto.setName(jackpot.getName());
                jackpotDto.setDescription(jackpot.getDescription());
                return ResponseEntity.ok(jackpotDto);
            }
            return ResponseEntity.badRequest().body("Jackpot not fount for User.");
        } catch (ResponseStatusException exception) {
            return ResponseEntity.status(exception.getStatusCode()).body(exception.getMessage());
        }
    }
}
