package com.example.demo.user;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class UserConfig {
    @Bean
    CommandLineRunner commandLineRunner(UserRepository repository) {
        return args -> {
            User USERNAME = new User(
                    1L,
                    "USERNAME",
                    "PASSWD",
                    List.of("MX1234"),  // 正确的列表格式
                    List.of("A1234")
            );

            User TEST = new User(
                    2L,
                    "TEST",
                    "TEST",
                    List.of(),  // 空列表
                    List.of()
            );

            repository.saveAll(
                    List.of(USERNAME, TEST)
            );
        };
    }
}
