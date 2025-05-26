package de.keywork.backend.service;

import de.keywork.backend.dto.UserDto;
import de.keywork.backend.entity.User;
import de.keywork.backend.repository.FormDataRepository;
import de.keywork.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;

/**
 * Service provides read/write operations on Users.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final FormDataRepository formDataRepo;

    /**
     * Used for framework authentication.
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.getUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPasswordHash(), new ArrayList<>()
        );
    }

    /**
     * Loads a user from the database by username and returns it as UserDto object.
     * @param username username for query
     * @param dto (empty) UserDto object
     * @return UserDto Object with username, id and formDataId
     */
    public UserDto loadUserByUsername(String username, UserDto dto) {
        User user = userRepository.getUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        dto.setUsername(user.getUsername());
        dto.setId(user.getId());
        if(user.getFormData() != null) {
            dto.setFormDataId(user.getFormData().getId());
        }
        return dto;
    }

    /**
     * Finds user by formDataId or throws exception.
     * @param formDataId
     * @return
     * @throws ResponseStatusException
     */
    public User findUserByFormDataId(long formDataId) throws ResponseStatusException{
        var optUser = userRepository.findByFormDataId(formDataId);
        if(optUser.isPresent()) {
            return optUser.get();
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Converts UserDto to User and stores it in the DB.
     * @param userDto
     * @return
     */
    public long saveUser(UserDto userDto) throws ResponseStatusException {
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

    /**
     * Updates existing User by setting the provided formDataId.
     * @param username user to be updated
     * @param formDataId
     * @return
     * @throws Exception
     */
    public long updateUser (String username, Long formDataId) throws Exception{
        var formData = formDataRepo.findById(formDataId).orElseThrow(ChangeSetPersister.NotFoundException::new);
        var user = userRepository.getUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        user.setFormData(formData);
        return userRepository.save(user).getId();
    }

    /**
     * Loads user by Id.
     * @param userId
     * @return
     */
    public User requireById(long userId) throws ResponseStatusException{
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Form data for this id not found"));
    }
}
