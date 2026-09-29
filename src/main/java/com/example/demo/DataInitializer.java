package com.example.demo;


import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.entity.Account;
import com.example.demo.repository.AccountRepository;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner loadData(AccountRepository accountRepository) {

        return args -> {

            Account account1 =
                    new Account("ACC1001", "Gopi", 10000);

            Account account2 =
                    new Account("ACC1002", "Ravi", 20000);

            accountRepository.save(account1);
            accountRepository.save(account2);

            System.out.println("Test accounts created successfully.");
        };
    }
}