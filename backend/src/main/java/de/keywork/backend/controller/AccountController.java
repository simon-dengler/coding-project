package de.keywork.backend.controller;

import de.keywork.backend.dto.UserDto;
import de.keywork.backend.service.UserService;
import de.keywork.backend.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Provides functionality for account management.
 * Endpoints are secured by JWT-Filter.
 */
@RestController
@RequiredArgsConstructor
public class AccountController {
    private final UserService userService;
    private final JwtUtil jwtUtil;

    /**
     * Loads data for a user by valid JWT
     * @param authorization valid JWT
     * @return User data as {@link UserDto} or HTTP Status Error
     */
    @GetMapping("/account")
    public ResponseEntity<?> getAccount(@RequestHeader("Authorization") String authorization){
        if(authorization != null
        &&authorization.startsWith("Bearer ")){
            try {
                var token = authorization.substring(7);
                var dto = new UserDto();
                var username = jwtUtil.validateAndExtractUsername(token);
                dto = userService.loadUserByUsername(username, dto);
                if(dto.getUsername() == null){
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found.");
                }
                return ResponseEntity.ok(dto);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Token");
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Missing or invalid Authorization Header.");
        }
    }
}
