package org.springframework.boot.ems.service;

import org.springframework.boot.ems.dto.UserResponse;
import org.springframework.boot.ems.entity.User;
import org.springframework.boot.ems.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.boot.ems.dto.UserRegistrationRequest;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Get all users
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserResponse::fromUser)
                .toList();
    }

    // Get user by ID
    public UserResponse getUserById(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "User not found: " + userId
                        )
                );
        return UserResponse.fromUser(user);
    }

    // Register user
    public UserResponse registerUser(
            UserRegistrationRequest request) {

        if (userRepository
                .findByUsername(request.getUsername())
                .isPresent()) {

            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Username already exists"
            );
        }

        User user = new User();

        user.setUsername(request.getUsername());

        // Hash password before saving
        user.setPassword(
                passwordEncoder.encode(request.getPassword())
        );

        if (request.getRole() == null ||
                request.getRole().isBlank()) {

            user.setRole("USER");

        } else {

            user.setRole(request.getRole());
        }

        User savedUser = userRepository.save(user);

        return UserResponse.fromUser(savedUser);
    }

    // Delete user
    public void deleteUser(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "User not found: " + userId
                        )
                );

        userRepository.delete(user);
    }
}