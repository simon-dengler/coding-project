package de.keywork.backend.controller;

import de.keywork.backend.dto.UserDto;
import de.keywork.backend.service.UserService;
import de.keywork.backend.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

/**
 * Povides REST endpoints for sign-up and log-in functionality, that do not require a valid token (logged-in user).
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authManager;

    /**
     * Checks, if a username is already taken or available.
     * @param userDto contains the username
     * @return Map with key "exists" and value of type bool, or http error code.
     */
    @PostMapping("/check")
    public ResponseEntity<?> checkUsername(@RequestBody UserDto userDto){
        Map<String, Boolean> map = new HashMap<String, Boolean>();
        if (userDto == null || userDto.getUsername() == null) {
            return ResponseEntity.badRequest().body("No User provided.");
        }
        try {
            if (userService.loadUserByUsername(userDto.getUsername()) != null){
                map.put("exists", true);
                return ResponseEntity.ok(map);
            }
        } catch (UsernameNotFoundException e){
            map.put("exists", false);
            return ResponseEntity.ok(map);
        }
        return ResponseEntity.internalServerError().body("Internal Error occured.");
    }

    /**
     * Creates new user and stores it in the DB.
     * @param userDto user to be created
     * @return valid JWT for the user or http error code
     */
    @PostMapping("/register")
    public ResponseEntity<?> saveUser(@RequestBody UserDto userDto){
        long id;
        try {
            id = userService.saveUser(userDto);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        }
        var token = jwtUtil.generateToken(userService.requireById(id).getUsername());
        if (token != null) {
            var response = new HashMap<String, String>();
            response.put("token", token);
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.internalServerError().build();
    }

    /**
     * Enpoint for login
     * @param userDto dto with username and password
     * @return valid JWT or http error code
     */
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody UserDto userDto) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(userDto.getUsername(), userDto.getPassword()));
        String token = jwtUtil.generateToken(userDto.getUsername());
        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        return ResponseEntity.ok(response);
    }
}
