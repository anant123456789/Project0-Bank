package com.revature.project0;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CustomerMenu {
	private static Logger logger = LogManager.getLogger(CustomerLogin.class.getName());  

	public CustomerAccount data;

	public CustomerMenu(CustomerAccount data) {
		this.data=data;
	}
	
	public void updateAccount() {
		try {
			Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost/Project0", "postgres",
					"Ejux8521");
			PreparedStatement stmt = conn.prepareStatement("select balance from account where account_number = ?");
			stmt.setInt(1, data.getAccountNumber());
			ResultSet rs = stmt.executeQuery();
			int balance = -1;
			if(rs.next()) {
				System.out.println("$ " + rs.getInt("balance"));
				balance = rs.getInt("balance");
			}
			if (balance >= 0) {
				data = new CustomerAccount(data.getAccountNumber(),balance);
				return;
			}

			// account_number = accountnumber;
		} catch (SQLException e) {
			System.out.println("Connection failure.");
			e.printStackTrace();
		}
			data = null;
	}

	/*
	 * get connection, use preparedstatement to query data from account, applying
	 * arithmetic login(42) set update and new bounds
	 * 
	 */
	public void deposit(int amount) {
		// balance = balance + amount;
		try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost/Project0", "postgres",
				"Ejux8521")) {
			PreparedStatement statement = conn
					.prepareStatement("select * from account where account_number = ?");
			statement.setInt(1, data.getAccountNumber());
			ResultSet rs = statement.executeQuery();
			
			if (!rs.next()) {
				System.out.println("Account not found");
				return;
			}
			
			PreparedStatement statement1 = conn
					.prepareStatement("update account set balance = balance + ? where account_number = ?");
			statement1.setInt(1, amount);
			statement1.setInt(2, data.getAccountNumber());
			statement1.executeUpdate();
			logger.info("You deposited $" + amount + " to account number: " + data.getAccountNumber());
			updateAccount();
		} catch (SQLException e) {
			System.out.println("Connection failure.");
			e.printStackTrace();
		}
	}

	public void withdraw(int amount) {
		// balance = balance - amount;
		try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost/Project0", "postgres",
				"Ejux8521")) {
			updateAccount();

			if (data.getBalance() - amount < 0) {
				System.out.println("Not enough Balance");
				return;
			} 
			PreparedStatement statement1 = conn
					.prepareStatement("update account set balance = balance - ? where account_number = ?");
			statement1.setInt(1, amount);
			statement1.setInt(2, data.getAccountNumber());
			statement1.executeUpdate();
			logger.info("You withdrew $" + amount + " to account number: " + data.getAccountNumber());
			updateAccount();
			
			
		} catch (SQLException e) {
			System.out.println("Connection failure.");
			e.printStackTrace();
		}
	}

	

	public boolean checkLoginC(String username1, String password1) {
		try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost/Project0", "postgres",
				"Ejux8521")) {
			PreparedStatement statement = conn
					.prepareStatement("select * from public.user where username = ? and password = ?");
			statement.setString(1, username1);
			statement.setString(2, password1);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			System.out.println("Connection failure.");
			e.printStackTrace();
		}
		return false;
	}

	

	

	public void transfer(int transferFrom, int transferTo, int amount) {
		try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost/Project0", "postgres",
				"Ejux8521")) {
			CallableStatement cstmt = conn.prepareCall("call transfer(?,?,?)");
			
			cstmt.setInt(1, transferFrom);
			cstmt.setInt(2, transferTo);
			cstmt.setInt(3, amount);
			cstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("Connection failure.");
			e.printStackTrace();
		}
	}

	public void viewPrevious() {
		try {
			Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost/Project0", "postgres",
					"Ejux8521");
			PreparedStatement stmt = conn
					.prepareStatement("select * from transactions where account_number = ? order by transaction_id desc limit 1");
			stmt.setInt(1, data.getAccountNumber());
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				System.out.print("Amount changed: " + rs.getInt(1)+ "|");
				System.out.print(" Account number: " + rs.getInt(2)+ "|");
				System.out.print(" Transaction id: " + rs.getInt(3)+ "|");
				System.out.println(" Transaction type: " + rs.getString(4));
			}
		} catch (SQLException e) {
			System.out.println("Connection failure.");
			e.printStackTrace();

		}
	}

	
	
}