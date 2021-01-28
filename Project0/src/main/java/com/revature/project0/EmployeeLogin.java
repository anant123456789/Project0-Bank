package com.revature.project0;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EmployeeLogin {
	private static Logger logger = LogManager.getLogger(EmployeeLogin.class.getName());

	public void loginMenu() {
		Scanner scan = new Scanner(System.in);

		char option = 'a';
		System.out.println("Enter your username");
		String username1 = scan.nextLine();
		System.out.println("Enter your password");
		String password1 = scan.nextLine();
		System.out.println("Enter your employee id");
		int employee_id = Integer.parseInt(scan.nextLine());

		
		if (checkLoginE(username1, password1, employee_id) == false) {
			System.out.println("Wrong Login Details");
			return;
		}
		logger.info("You just logged in as an Employee");
		do {
			displayOptions();

			option = scan.nextLine().charAt(0);
			switch (option) {
			case 'A':
				viewPendingAccounts(scan);
				logger.info("You just viewed and decided on pending user Accounts");
				break;
			case 'B':
				
				viewAccount();
				logger.info("You just viewed customer bank accounts");
				break;
			case 'C':
				BankTransactions bt = new BankTransactions();
				bt.viewTransactions();
				logger.info("You just viewed the transaction log");
				break;
			case 'D':
				viewPendingBankAccount(scan);
				logger.info("You just viewed and decided on pending Bank Accounts");
				break;
			}
		} while (option != 'E');
	}

	public void viewPendingBankAccount(Scanner scan) {
		Pending p = new Pending();
		ArrayList<PendingBankAccount> accounts = p.viewPendingBank();
		if (accounts.size() == 0) {
			System.out.println("No Bank Accounts found");
			return;
		}
		for (PendingBankAccount account : accounts) {
			System.out.println(account.toString());
		}
		System.out.println("Approve or Reject Account");
		String choice = scan.nextLine();
		if (choice.equals("Approve")) {
			System.out.println("Enter account number to approve");
			int account_number = Integer.parseInt(scan.nextLine());
			Pending p1 = new Pending();
			p1.approveBank(account_number);
			logger.info("You just approved bank account with account number: " + account_number);
		}
		if (choice.equals("Reject")) {
			System.out.println("Enter account number to reject");
			int account_number = Integer.parseInt(scan.nextLine());
			Pending p1 = new Pending();
			p1.rejectBank(account_number);
			logger.info("You just rejected bank account with account number: " + account_number);

		}
	}

	private void displayOptions() {
		System.out.println("Welcome!");
		System.out.println("A. View Pending User Accounts");
		System.out.println("B. View Accounts");
		System.out.println("C. View Transcations logs");
		System.out.println("D. View Pending Bank Accounts");
		System.out.println("E. Exit");

		System.out.println("Enter an option");
	}

	private void viewPendingAccounts(Scanner scan) {
		Pending p = new Pending();
		p.viewPending();
		System.out.println("Approve or Reject Account");
		String choice = scan.nextLine();
		if (choice.equals("Approve")) {
			System.out.println("Enter username to approve");
			String username = scan.nextLine();
			Pending p1 = new Pending();
			p1.approve(username);
			logger.info("You just approved user account with username: " + username);
		}
		if (choice.equals("Reject")) {
			System.out.println("Enter username to reject");
			String username = scan.nextLine();
			Pending p1 = new Pending();
			p1.reject(username);
			logger.info("You just rejected user account with username: " + username);
		}
	}

	public boolean checkLoginE(String username1, String password1, int employee_id) {
		try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost/Project0", "postgres",
				"Ejux8521")) {
			PreparedStatement statement = conn.prepareStatement(
					"select * from public.user where username = ? and password = ? and employee_id = ?");
			statement.setString(1, username1);
			statement.setString(2, password1);
			statement.setInt(3, employee_id);
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
	public void viewAccount() {
		try {
			Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost/Project0", "postgres",
					"Ejux8521");
			PreparedStatement stmt = conn.prepareStatement("select * from account where account_status = true order by account_number");
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				
					System.out.print("Account Number: " + rs.getInt(1) + "|");
					System.out.print(" Balance: " + rs.getInt(2) + "|");
					System.out.println(" Customer id: " + rs.getInt(3));
				
			}
		} catch (SQLException e) {
			System.out.println("Connection failure.");
			e.printStackTrace();

		}
	}
}
