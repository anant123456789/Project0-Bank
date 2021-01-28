package com.revature.project0;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Menu {
	private static Logger logger = LogManager.getLogger(Menu.class.getName());

	void showMenu() {
		char option2 = 'a';
		do {
			Scanner scanner = new Scanner(System.in);
			System.out.println("A.Register");
			System.out.println("B.Login");
			System.out.println("C.Exit");
			System.out.println();
			System.out.println("Enter an option: ");
			option2 = scanner.nextLine().charAt(0);
			switch (option2) {
			case 'A':
				register(scanner);
				break;
			case 'B':
				System.out.println("Employee(E) or Customer(C)");
				String inputs = scanner.nextLine();

				CustomerLogin l = new CustomerLogin();
				EmployeeLogin e = new EmployeeLogin();
				if (inputs.equals("C")) {
					l.loginMenu();
				}
				if (inputs.equals("E")) {
					e.loginMenu();
				}
				break;

			case 'C':
				System.out.println("Thank you for banking with us!");
				logger.info("Exits the program");
				System.exit(0);

			default:
				System.out.println("Error: invalid option. Please enter A, B or C");
				break;
			}

		} while (option2 != 'C');
	}

	private void register(Scanner scanner) {
		System.out.println("Enter your customerid");
		int customer_id = Integer.parseInt(scanner.nextLine());
		System.out.println("Enter your first name");
		String first_name = scanner.nextLine();
		System.out.println("Enter your last name");
		String last_name = scanner.nextLine();
		System.out.println("Create your username");
		String username = scanner.nextLine();
		System.out.println("Create your password");
		String password = scanner.nextLine();
		
		createAccount(username, customer_id, password, first_name, last_name);
		System.out.println("Wait for authorization");
		logger.info("Accounts created go to Pending accounts for employee authorization");
	}
	public void createAccount(String username, int customer_id, String password, String first_name, String last_name) {

		try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost/Project0", "postgres",
				"Ejux8521")) {
			PreparedStatement statement = conn.prepareStatement(
					"insert into pending_user(customer_id, username, password, first_name, last_name) values(?, ?, ?, ?, ?)");
			statement.setInt(1, customer_id);
			statement.setString(2, username);
			statement.setString(3, password);
			statement.setString(4, first_name);
			statement.setString(5, last_name);
			statement.executeUpdate();
			User u = new User(password, username, first_name, last_name, customer_id);
		} catch (SQLException e) {
			System.out.println("Connection failure.");
			e.printStackTrace();
		}
	}
}
