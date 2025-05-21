package de.keywork.backend.controller;

import de.keywork.backend.dto.FormDataDto;
import de.keywork.backend.service.FormDataService;
import de.keywork.backend.service.UserService;
import de.keywork.backend.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class FormController {

    private final FormDataService formDataService;
    private final UserService userService;
    private final JwtUtil jwtUtil;

    @PostMapping("/form")
    public ResponseEntity<?> saveFormData(@RequestHeader("Authorization") String authorization,
                                       @RequestBody FormDataDto dataDto) {
        long formDataId;
        try {
            formDataId = formDataService.saveFormData(dataDto);
            var token = authorization.substring(7);
            var username = jwtUtil.validateAndExtractUsername(token);
            userService.updateUser(username, formDataId);
        } catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok(formDataId);
    }

    @GetMapping("/form/{formId}")
    public FormDataDto saveFormData(@PathVariable long formId) {
        return formDataService.getFormData(formId);
    }

}
