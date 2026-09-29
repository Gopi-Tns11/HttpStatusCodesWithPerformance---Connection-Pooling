package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
    
    private String accountNumber;
    private String accountHolderName;
    private double balance;
    
    public Account() {
    	
    }
    
    public Account(String accountNumber,String accountHolderName,double balance) {
    	this.accountNumber=accountNumber;
    	this.accountHolderName=accountHolderName;
    	this.balance=balance;
    	
    }

	public long getId() {
		return id;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public String getAccountHolderName() {
		return accountHolderName;
	}

	public double getBalance() {
		return balance;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public void setAccountHolderName(String accountHolderName) {
		this.accountHolderName = accountHolderName;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}
    
}
