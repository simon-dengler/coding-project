package de.keywork.backend.controller;

import de.keywork.backend.dto.UserDto;
import de.keywork.backend.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AccountController {
    private final JwtUtil jwtUtil;

    @GetMapping("/account")
    public ResponseEntity<?> getAccountName(@RequestHeader("Authorization") String authorization){
        if(authorization != null
        &&authorization.startsWith("Bearer ")){
            try {
                var token = authorization.substring(7);
                var dto = new UserDto();
                dto.setUsername(jwtUtil.validateAndExtractUsername(token));
                return ResponseEntity.ok(dto);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Token");
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Missing or invalid Authorization Header.");
        }
    }
}
