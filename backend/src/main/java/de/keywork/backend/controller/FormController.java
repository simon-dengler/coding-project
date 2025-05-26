package de.keywork.backend.controller;

import de.keywork.backend.dto.FormDataDto;
import de.keywork.backend.service.FormDataService;
import de.keywork.backend.service.UserService;
import de.keywork.backend.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

/**
 * Provides endpoints for storing, updating and fetching {@link de.keywork.backend.entity.FormData}.
 * All endpoints are protected by {@link de.keywork.backend.util.JwtFilter}.
 */
@RestController
@RequiredArgsConstructor
public class FormController {

    private final FormDataService formDataService;
    private final UserService userService;
    private final JwtUtil jwtUtil;

    /**
     * Creates or updates the {@link de.keywork.backend.entity.FormData} for the current user.
     * @param authorization
     * @param dataDto
     * @return FormDataId or http error code
     */
    @PostMapping("/form")
    public ResponseEntity<?> saveFormData(@RequestHeader("Authorization") String authorization,
                                       @RequestBody FormDataDto dataDto) {
        try {
            long formDataId = formDataService.saveFormData(dataDto);
            var token = authorization.substring(7);
            var username = jwtUtil.validateAndExtractUsername(token);
            userService.updateUser(username, formDataId);
            return ResponseEntity.ok(formDataId);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Gets {@link de.keywork.backend.entity.FormData} by id.
     * @param formId
     * @return {@link FormDataDto} for the id or http error code
     */
    @GetMapping("/form/{formId}")
    public ResponseEntity<?> getFormData(@PathVariable long formId) {
        try {
            return ResponseEntity.ok(formDataService.getFormData(formId));
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
        }
    }
}
