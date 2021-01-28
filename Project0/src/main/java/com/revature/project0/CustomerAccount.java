package com.revature.project0;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CustomerAccount {
	private int accountNumber;
	private int balance;
	public CustomerAccount(int accountNumber, int balance) {
		super();
		this.accountNumber = accountNumber;
		this.balance = balance;
		
		
	}
	public int getAccountNumber() {
		return accountNumber;
	}
	public int getBalance() {
		return balance;
	}
	

}
