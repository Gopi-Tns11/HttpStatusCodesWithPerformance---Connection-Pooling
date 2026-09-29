package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Account;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.AccountRepository;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account deposit(Long id, double amount) {

        Account account = accountRepository.findById(id)
                .orElseThrow(() ->
                 new ResourceNotFoundException("Account not found"));

        if (amount <= 0) {
            throw new IllegalArgumentException(
                    "Deposit amount must be greater than zero");
        }

        account.setBalance(account.getBalance() + amount);

        return accountRepository.save(account);
    }

    public Account withdraw(Long id, double amount) {

        Account account = accountRepository.findById(id)
                .orElseThrow(() ->
                 new ResourceNotFoundException("Account not found"));

        if (amount <= 0) {
            throw new IllegalArgumentException(
                    "Withdrawal amount must be greater than zero");
        }

        if (amount > account.getBalance()) {
            throw new IllegalArgumentException(
                    "Insufficient balance");
        }

        account.setBalance(account.getBalance() - amount);

        return accountRepository.save(account);
    }

    public double getBalance(Long id) {

        Account account = accountRepository.findById(id)
                .orElseThrow(() ->
                new ResourceNotFoundException("Account not found"));

        return account.getBalance();
    }
}