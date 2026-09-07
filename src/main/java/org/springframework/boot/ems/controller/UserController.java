package org.springframework.boot.ems.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ems.entity.User;
import org.springframework.boot.ems.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // GET all users
    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


    // GET user by ID
    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(
            @PathVariable("id") Long userId) {

        User user = userRepository
                .findById(userId)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "User not found: " + userId
                        )
                );

        return ResponseEntity.ok(user);
    }


    // REGISTER user
    @PostMapping("/users/register")
    public ResponseEntity<User> registerUser(
            @RequestBody User user) {

        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Username already exists"
            );
        }

        if (user.getRole() == null || user.getRole().isBlank()) {
            user.setRole("USER");
        }

        user.setPassword(
                passwordEncoder.encode(user.getPassword())
        );

        User savedUser = userRepository.save(user);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedUser);
    }


    // LOGIN user
    @PostMapping("/users/login")
    public ResponseEntity<String> login(
            @RequestBody User loginRequest) {

        User user = userRepository
                .findByUsername(loginRequest.getUsername())
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.UNAUTHORIZED,
                                "Invalid username or password"
                        )
                );

        if (!passwordEncoder.matches(
                loginRequest.getPassword(),
                user.getPassword())) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Invalid username or password"
            );
        }

        return ResponseEntity.ok("Login successful");
    }


    // DELETE user
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(
            @PathVariable("id") Long userId) {

        User user = userRepository
                .findById(userId)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "User not found: " + userId
                        )
                );

        userRepository.delete(user);

        return ResponseEntity.noContent().build();
    }
}