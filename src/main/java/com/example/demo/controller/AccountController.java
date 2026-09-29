package com.example.demo.controller;

import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Account;
import com.example.demo.service.AccountService;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {

	
	private final AccountService accountService;
	public AccountController (AccountService accountService) {
		this.accountService=accountService;
	}
	
	@PostMapping("/{id}/deposit")
	public ResponseEntity<Account> deposit(
			@PathVariable Long id,
            @RequestParam double amount){
		Account account = accountService.deposit(id, amount);
		return ResponseEntity.ok(account);
		
	}
	
	@PostMapping("/{id}/withdraw")
	public ResponseEntity<Account>withdraw(
			@PathVariable Long id,
			@RequestParam double amount){
		Account account = accountService.withdraw(id, amount);
		return ResponseEntity.ok(account);
		}
	
	 public ResponseEntity<Double> getBalance(
	            @PathVariable Long id) {

	        double balance = accountService.getBalance(id);

	        return ResponseEntity.ok(balance);
	    }
	
}
