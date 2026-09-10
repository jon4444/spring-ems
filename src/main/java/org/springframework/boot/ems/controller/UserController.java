package org.springframework.boot.ems.controller;

import org.springframework.boot.ems.dto.UserRegistrationRequest;
import org.springframework.boot.ems.dto.UserResponse;
import org.springframework.boot.ems.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // GET all users
    @GetMapping("/users")
    public List<UserResponse> getAllUsers() {
        return userService.getAllUsers();
    }

    // GET user by ID
    @GetMapping("/users/{id}")
    public ResponseEntity<UserResponse> getUserById(
            @PathVariable("id") Long userId) {

        UserResponse user = userService.getUserById(userId);

        return ResponseEntity.ok(user);
    }

    // REGISTER user
    @PostMapping("/users/register")
    public ResponseEntity<UserResponse> registerUser(
            @RequestBody UserRegistrationRequest request) {

        UserResponse savedUser =
                userService.registerUser(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedUser);
    }

    // DELETE user
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(
            @PathVariable("id") Long userId) {

        userService.deleteUser(userId);

        return ResponseEntity.noContent().build();
    }
}