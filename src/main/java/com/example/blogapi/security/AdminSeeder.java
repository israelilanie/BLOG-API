package com.example.blogapi.security;

import com.example.blogapi.model.Role;
import com.example.blogapi.model.User;
import com.example.blogapi.repository.UserRepo;
import lombok.RequiredArgsConstructor;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

@Component
@RequiredArgsConstructor
public class AdminSeeder implements CommandLineRunner {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    @Value("${admin.email}")
    private String adminEmail;

    @Value("${admin.password}")
    private String adminPassword;


    @Override
    public void run(String... args) throws Exception {

        boolean adminEmailExists = userRepo.findByEmail(adminEmail).isPresent();
        if(!adminEmailExists){
            User user = new User();
            user.setEmail(adminEmail);
            user.setUsername("admin");
            user.setPassword(passwordEncoder.encode(adminPassword));
            user.setRole(Role.ROLE_ADMIN);
            userRepo.save(user);
            System.out.println("ADMIN CREATED");
        }
    }
}
