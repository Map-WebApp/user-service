package com.example.userservice.service;

import com.example.userservice.entity.User;
import java.util.Optional;

public interface UserService {
    User registerUser(User user);
    Optional<User> findByUsername(String username);
    User validateUser(String username, String password);
} 