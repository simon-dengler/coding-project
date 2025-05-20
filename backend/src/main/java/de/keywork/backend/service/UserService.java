package de.keywork.backend.service;

import de.keywork.backend.dto.UserDto;
import de.keywork.backend.entity.User;
import de.keywork.backend.repository.UserRepository;
import de.keywork.backend.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.getUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPasswordHash(), new ArrayList<>()
        );
    }

    public long saveUser(UserDto userDto){
        User user;
        if(userDto.getId() != null) {
            user = requireById(userDto.getId());
        } else {
            user = new User();
        }
        if (ObjectUtils.isEmpty(userDto.getUsername())
            || ObjectUtils.isEmpty(userDto.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Required field not filled");
        }
        if (userRepository.existsByUsername(userDto.getUsername())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already exists");
        }
        user.setUsername(userDto.getUsername());
        String passwordHash = passwordEncoder.encode(userDto.getPassword());
        user.setPasswordHash(passwordHash);
        user = userRepository.save(user);
        return user.getId();
    }

    public User requireById(long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Form data for this id not found"));
    }
}
