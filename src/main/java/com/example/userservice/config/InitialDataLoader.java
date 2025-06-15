package com.example.userservice.config;

import com.example.userservice.entity.User;
import com.example.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class InitialDataLoader implements ApplicationRunner {

    private final UserRepository userRepository;

    @Autowired
    public InitialDataLoader(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        // Thêm người dùng mặc định khi ứng dụng khởi chạy
        createDefaultUserIfNotExists("test123", "test123");
        createDefaultUserIfNotExists("hoaiphu", "test123");
        
        System.out.println("✅ Hoàn thành khởi tạo dữ liệu mặc định!");
    }

    private void createDefaultUserIfNotExists(String username, String password) {
        if (!userRepository.existsByUsername(username)) {
            User user = new User(username, password);
            userRepository.save(user);
            System.out.println("✅ Đã tạo người dùng mặc định: " + username);
        } else {
            System.out.println("ℹ️ Người dùng đã tồn tại, bỏ qua: " + username);
        }
    }
} 