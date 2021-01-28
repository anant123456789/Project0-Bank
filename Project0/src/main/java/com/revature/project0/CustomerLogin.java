package com.revature.project0;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CustomerLogin {
	private static Logger logger = LogManager.getLogger(CustomerLogin.class.getName());

	public void loginMenu() {

		Scanner scan = new Scanner(System.in);
		char option1 = 'a';
		System.out.println("Enter your username");
		String username1 = scan.nextLine();
		System.out.println("Enter your password");
		String password1 = scan.nextLine();

		if (checkLoginC(username1, password1) == false) {
			System.out.println("Wrong Login Details");

			logger.info("You entered the wrong login details");
			return;
		}
		logger.info("You just logged in as a customer");
		do {

			customerOptions();
			option1 = scan.nextLine().charAt(0);

			System.out.println();

			switch (option1) {
			case 'A':
				System.out.println("Enter your customer id");
				int customer_id = Integer.parseInt(scan.nextLine());
				
				viewCAccounts(customer_id);
				logger.info("You just viewed your accounts");
				break;
			case 'B':
				System.out.println("Enter your account number");
				int accountnumber = Integer.parseInt(scan.nextLine());
				
				CustomerAccount account = getAccount(accountnumber);
				if(account == null) {
					System.out.println("Account not found");
					break;
				}
				CustomerMenu ca5 = new CustomerMenu(account);
				logger.info("Account number: " + account.getAccountNumber()+"- $"+account.getBalance());
				break;
			case 'C':
				deposit(scan);
				break;
			case 'D':
				withdraw(scan);
				break;
			case 'E':
				System.out.println("Enter your account number");
				int accountnumberE = Integer.parseInt(scan.nextLine());
				
				CustomerAccount accountE = getAccount(accountnumberE);
				if(accountE == null) {
					System.out.println("Account not found");
					break;
				}
				CustomerMenu ca2 = new CustomerMenu(accountE);
				ca2.viewPrevious();
				logger.info("You just viewed your previous transaction");
				break;
			case 'F':
				createNewBankAccount(scan);
				break;
			case 'G':
				postTransfer(scan);
				break;
			case 'H':
				resolveTransfer(scan);
				logger.info("You just viewed and decided on pending transfers");
				break;
			case 'I':
				break;
			default:
				System.out.println("Error: invalid option. Please enter A, B, C, D, or E or F or G.");
				break;
			}
		} while (option1 != 'I');

	}

	public void createNewBankAccount(Scanner scan) {
		System.out.println("Enter your customer id");
		int customer_id2 = Integer.parseInt(scan.nextLine());

		System.out.println("Enter starting balance");
		int starting_balance = Integer.parseInt(scan.nextLine());
		
		createBankAccount(customer_id2, starting_balance);
		System.out.println("Wait for authorization");
		logger.info("The bank account will be pending until an employee approves it");
	}

	public void postTransfer(Scanner scan) {
		int transferFrom = 0;
		int transferTo = 0;
		int amount1 = 0;
		System.out.println("Enter account number to transfer from");
		transferFrom = Integer.parseInt(scan.nextLine());
		System.out.println("Enter account number to transfer to");
		transferTo = Integer.parseInt(scan.nextLine());
		System.out.println("Enter amount");
		amount1 = Integer.parseInt(scan.nextLine());
		if (amount1 < 0) {
			System.out.println("Cannot transfer negative money");
			logger.fatal("You tried to transfer negative money");
			return;
		}
		System.out.println("Enter comment");
		String comment = scan.nextLine();
		BankTransactions bt = new BankTransactions();
		if (bt.transferCheck(transferFrom) < amount1) {
			System.out.println("Not enough balance");
			return;
		}
		Pending p = new Pending();
		p.PendingTransfer(transferFrom, transferTo, amount1, comment);
		System.out.println("Waiting for approval");
		logger.info("You just created a transfer request");

	}

	public void resolveTransfer(Scanner scan) {
		int transferFrom = 0;
		int transferTo = 0;
		int amount1 = 0;
		System.out.println("Enter your account_number");
		int account_number2 = Integer.parseInt(scan.nextLine());
		Pending p1 = new Pending();
		ArrayList<PendingTransfer> transfers = p1.viewPendingTransfer(account_number2);
		if (transfers.size() == 0) {
			System.out.println("No Transfer Requests found");
			return;
		}
		for (PendingTransfer transfer : transfers) {
			System.out.println(transfer.toString());
		}

		System.out.println("Approve or Reject Transfer");
		String choice = scan.nextLine();
		if (choice.equals("Approve")) {
			System.out.println("Enter transfer_id to approve");
			int transfer_id = Integer.parseInt(scan.nextLine());
			for (PendingTransfer transfer : transfers) {
				if (transfer.getTransfer_id() == transfer_id) {
					transferFrom = transfer.getFrom();
					transferTo = transfer.getTo();
					amount1 = transfer.getAmount();
					break;
				}
			}
			if (transferFrom == 0) {
				System.out.println("No transfer was found");
				return;
			}
			CustomerAccount toAccount = getAccount(transferTo);
			if(toAccount == null) {
				System.out.println("Account not found");
				return;
			}
			CustomerMenu ca8 = new CustomerMenu(toAccount);
			ca8.transfer(transferFrom, transferTo, amount1);
			Pending p4 = new Pending();
			p4.DeclineTransfer(transfer_id);
			BankTransactions bt = new BankTransactions();
			bt.updateTransfers(amount1, transferTo, transferFrom);
			logger.info("You just approved transfer with transfer id: " + transfer_id);
		}
		if (choice.equals("Reject")) {
			System.out.println("Enter transfer_id to reject");
			int transfer_id = Integer.parseInt(scan.nextLine());
			Pending p3 = new Pending();
			p3.DeclineTransfer(transfer_id);
			logger.info("You just rejected transfer with transfer id: " + transfer_id);

		}
	}

	public void withdraw(Scanner scan) {
		System.out.println("Enter an amount to withdraw: ");
		int amount2 = Integer.parseInt(scan.nextLine());
		if (amount2 < 0) {
			System.out.println("Cannot withdraw negative money");
			logger.fatal("You just tried to withdraw negative money");
			return;
		}
		System.out.println("Enter account number");
		int account_number1 = Integer.parseInt(scan.nextLine());
		CustomerAccount account = getAccount(account_number1);
		CustomerMenu a3 = new CustomerMenu(account);
		a3.withdraw(amount2);
		BankTransactions bt1 = new BankTransactions();
		bt1.updateW(amount2, account_number1);
	}

	public void deposit(Scanner scan) {
		System.out.println("Enter an amount to deposit: ");
		int amount = Integer.parseInt(scan.nextLine());
		if (amount < 0) {
			System.out.println("Cannot deposit negative money");
			logger.fatal("You just tried to deposit negative money");
			return;
		}
		System.out.println("Enter account number");
		int account_number = Integer.parseInt(scan.nextLine());
		CustomerAccount account = getAccount(account_number);
		if (account == null) {
			System.out.println("Account not found");
			return;
		}
		CustomerMenu a1 = new CustomerMenu(account);
		a1.deposit(amount);
		BankTransactions bt = new BankTransactions();
		bt.updateD(amount, account_number);
	}

	private void customerOptions() {
		System.out.println("Welcome, " + "!");

		System.out.println();
		System.out.println("What would you like to do?");
		System.out.println();
		System.out.println("A. View Bank Accounts");
		System.out.println("B. Check your balance");
		System.out.println("C. Make a deposit");
		System.out.println("D. Make a withdrawal");
		System.out.println("E. View previous transaction");
		System.out.println("F. Create a new bank account");
		System.out.println("G. Make a Transfer");
		System.out.println("H. View Pending Transfers");
		System.out.println("I. Exit");

		System.out.println();
		System.out.println("Enter an option: ");
	}

	public CustomerAccount getAccount(int accountnumber) {
		try {
			Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost/Project0", "postgres",
					"Ejux8521");
			PreparedStatement stmt = conn.prepareStatement("select balance from account where account_number = ?");
			stmt.setInt(1, accountnumber);
			ResultSet rs = stmt.executeQuery();
			int balance = -1;
			if (rs.next()) {
				balance = rs.getInt("balance");
			}
			if (balance >= 0) {
				return new CustomerAccount(accountnumber, balance);
			}

			// account_number = accountnumber;
		} catch (SQLException e) {
			System.out.println("Connection failure.");
			e.printStackTrace();
		}
		return null;
	}

	public void createBankAccount(int customer_id, int starting_balance) {
		Random rand = new Random();
		int account_number = rand.nextInt(999);
		try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost/Project0", "postgres",
				"Ejux8521")) {
			PreparedStatement statement = conn.prepareStatement(
					"insert into account(account_number, customer_id, balance, account_status) values(?,?,?,false)");
			statement.setInt(1, account_number);
			statement.setInt(2, customer_id);
			statement.setInt(3, starting_balance);
			int balance = starting_balance;
			statement.executeUpdate();

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
	public void viewCAccounts(int customer_id) {
		try {
			Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost/Project0", "postgres",
					"Ejux8521");
			PreparedStatement stmt = conn.prepareStatement("select * from account where customer_id = ? and account_status = true order by account_number");
			stmt.setInt(1, customer_id);
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				System.out.print("Account number: " + rs.getInt(1)  + "|");
				System.out.print(" Balance: " + rs.getInt(2) + "|");
				System.out.println(" Customer id: " + rs.getInt(3));
			}
		} catch (SQLException e) {
			System.out.println("Connection failure.");
			e.printStackTrace();

		}
	}
}
