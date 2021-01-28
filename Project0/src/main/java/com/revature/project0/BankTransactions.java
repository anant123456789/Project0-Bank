package com.revature.project0;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BankTransactions {
	int Balance;

	public void viewTransactions() {
		try {
			Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost/Project0", "postgres",
					"Ejux8521");
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select * from transactions");

			while (rs.next()) {
				System.out.print("Amount changed: " + rs.getInt(1));
				System.out.print(" Account number " + rs.getInt(2));
				System.out.print(" Transaction id " + rs.getInt(3));
				System.out.println(" Transaction type: " + rs.getString(4));
			}

		} catch (SQLException e) {
			System.out.println("Connection failure.");
			e.printStackTrace();
		}
	}

	public void updateD(int amountchanged, int account_number) {
		try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost/Project0", "postgres",
				"Ejux8521")) {
			PreparedStatement statement = conn.prepareStatement(
					"insert into transactions(amountchanged, account_number,transaction_type) values(?,?,'D')");
			statement.setInt(1, amountchanged);
			statement.setInt(2, account_number);
			statement.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Connection failure.");
			e.printStackTrace();
		}

	}

	public void updateW(int amountchanged, int account_number) {
		try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost/Project0", "postgres",
				"Ejux8521")) {
			PreparedStatement statement = conn.prepareStatement(
					"insert into transactions(amountchanged, account_number,transaction_type) values(?,?,'W')");
			statement.setInt(1, amountchanged);
			statement.setInt(2, account_number);
			statement.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Connection failure.");
			e.printStackTrace();
		}

	}

	public int transferCheck(int account_number) {
		try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost/Project0", "postgres",
				"Ejux8521")) {
			PreparedStatement statement = conn.prepareStatement("select balance from account where account_number = ?");
			statement.setInt(1, account_number);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				Balance = rs.getInt("balance");
			}
			return Balance;
		} catch (SQLException e) {
			System.out.println("Connection failure.");
			e.printStackTrace();
			return 0;
		}
	}

	public void updateTransfers(int amountchanged, int toAccount_Number, int fromAccount_Number) {
		try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost/Project0", "postgres",
				"Ejux8521")) {
			PreparedStatement statement = conn
					.prepareStatement("insert into transactions(amountchanged, account_number,transaction_type) values(?,?,'T')");
			statement.setInt(1, amountchanged);
			statement.setInt(2, toAccount_Number);
			statement.executeUpdate();
			PreparedStatement statement1 = conn
					.prepareStatement("insert into transactions(amountchanged, account_number,transaction_type) values(?,?,'T')");
			statement1.setInt(1, amountchanged);
			statement1.setInt(2, fromAccount_Number);
			statement1.executeUpdate();
	}catch (SQLException e) {
		System.out.println("Connection failure.");
		e.printStackTrace();
	}
}
}