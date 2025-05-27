package de.keywork.backend.service;

import de.keywork.backend.dto.UserDto;
import de.keywork.backend.entity.User;
import de.keywork.backend.repository.UserRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.when;

/**
 * Provides unit tests for the {@link UserService}.
 */
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    //@Mock
    //private FormDataRepository formDataRepository;

    @InjectMocks
    private UserService userService;

    /**
     * Tests for the loadByUsername method.
     */
    @Nested
    class LoadByUsernameTest {
        @Test
        public void returnsUserDto_whenUsernameExists () {
            // Arrange
            User user = new User();
            user.setId(1L);
            user.setUsername("Simon");
            when(userRepository.getUserByUsername("Simon")).thenReturn(Optional.of(user));
            // Act
            UserDto actualDto = userService.loadUserByUsername("Simon", new UserDto());
            // Assert
            assertEquals("Simon", actualDto.getUsername());
            assertEquals(1L, actualDto.getId());
        }

        @Test
        public void throwsException_whenUsernameDoesNotExists () {
            // Arrange
            when(userRepository.getUserByUsername("Max")).thenThrow(new UsernameNotFoundException("User not found"));
            // Act & Assert
            assertThrows(UsernameNotFoundException.class,
                    () -> userService.loadUserByUsername("Max", new UserDto()));
        }
    }

    /**
     * Tests for the saveUser method.
     */
    @Nested
    class SaveUserTest {
        @Test
        public void returnsId_whenDtoIsValidAndUserDoesNotExist() {
            // Arrange
            var dto = new UserDto();
            dto.setUsername("Simon");
            dto.setPassword("Testpassword");
            when(userRepository.existsByUsername("Simon")).thenReturn(false);
            when(passwordEncoder.encode("Testpassword")).thenReturn("Testpassword");
            User user = new User();
            user.setPasswordHash(dto.getPassword());
            user.setUsername(dto.getUsername());
            user.setId(1L);
            // alternatively any(User.class)
            when(userRepository.save(argThat(u -> u.getUsername().equals(user.getUsername()))))
                    .thenReturn(user);
            // Act
            long id = userService.saveUser(dto);
            // Assert
            assertEquals(user.getId(), id);
        }

        @Test
        public void throwsError_whenUsernameExistsOrDtoNotValid() {
            // Arrange
            var invalidDto = new UserDto();
            var validDto = new UserDto();
            validDto.setUsername("Max");
            validDto.setPassword("123");
            when(userRepository.existsByUsername("Max")).thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already exists"));
            // Act & Assert
            assertThrows(ResponseStatusException.class,
                    () -> userService.saveUser(invalidDto));
            assertThrows(ResponseStatusException.class,
                    () -> userService.saveUser(validDto));
        }
    }

}
