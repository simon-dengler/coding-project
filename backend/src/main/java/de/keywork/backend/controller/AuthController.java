package de.keywork.backend.controller;

import de.keywork.backend.dto.UserDto;
import de.keywork.backend.service.UserService;
import de.keywork.backend.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authManager;

    @PostMapping("/check")
    public boolean checkUser(@RequestBody UserDto userDto){
        if (userService.loadUserByUsername(userDto.getUsername()) != null){
            return true;
        }
        return false;
    }

    @PostMapping("/register")
    public long saveUser(@RequestBody UserDto userDto){
        return userService.saveUser(userDto);
    }

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
