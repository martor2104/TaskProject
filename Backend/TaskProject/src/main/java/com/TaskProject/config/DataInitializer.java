package com.TaskProject.config;

import com.TaskProject.Entity.SystemRole;
import com.TaskProject.Entity.User;
import com.TaskProject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        if (userRepository.findByEmail("admin@admin.com").isEmpty()) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setEmail("admin@admin.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setSystemRole(SystemRole.ADMIN);

            userRepository.save(admin);
            System.out.println("Admin user created: admin@admin.com / admin123");
        } else {
            System.out.println("Admin user already exists, skipping creation");
        }

    }
}
