package com.example.userservice.controller;

import com.example.userservice.dto.RegisterDto;
import com.example.userservice.entity.User;
import com.example.userservice.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterDto registerDto) {
        try {
            User newUser = new User(registerDto.getUsername(), registerDto.getPassword());
            User registeredUser = userService.registerUser(newUser);
            // Don't return the password hash in the response
            registeredUser.setPassword(null);
            return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{username}")
    public ResponseEntity<?> getUserByUsername(@PathVariable String username) {
        return userService.findByUsername(username)
                .map(user -> {
                    // Don't return the password hash in the response
                    user.setPassword(null);
                    return ResponseEntity.ok(user);
                })
                .map(ResponseEntity.class::cast)
                .orElse(new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND));
    }

    @PostMapping("/validate-password")
    public ResponseEntity<?> validatePassword(@RequestBody RegisterDto loginDto) {
        // This endpoint is intended for internal use by the auth-service.
        // It returns the full user object, including the password hash,
        // so the auth-service can perform password validation.
        return userService.findByUsername(loginDto.getUsername())
                .map(ResponseEntity::ok)
                .map(ResponseEntity.class::cast)
                .orElse(new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND));
    }

    @GetMapping("/ping")
    public String ping() {
        return "pong from user-service";
    }
}
