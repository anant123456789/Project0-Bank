package com.revature.project0;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Pending {
	public void viewPending() {
		try {
			Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost/Project0", "postgres",
					"Ejux8521");
			PreparedStatement stmt = conn.prepareStatement("select * from pending_user");
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				System.out.print("First Name: " + rs.getString("first_name") + "|");
				System.out.print(" Last Name: " + rs.getString("last_name") + "|");
				System.out.print(" Username: " + rs.getString("username") + "|");
				System.out.print(" Password: " + rs.getString("password") + "|");
				System.out.println(" Customer id: " + rs.getInt("customer_id"));
			}
		} catch (SQLException e) {
			System.out.println("Connection failure.");
			e.printStackTrace();

		}
	}

	public void approve(String username) {
		try {
			Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost/Project0", "postgres",
					"Ejux8521");
			PreparedStatement stmt = conn.prepareStatement(
					"insert into public.user" + "(first_name, last_name, username, password, customer_id) "
							+ "select first_name, last_name, username, password, customer_id "
							+ "from pending_user  where pending_user.username = ?;");
			stmt.setString(1, username);
			stmt.executeUpdate();
			PreparedStatement stmt1 = conn.prepareStatement("delete from pending_user where username = ? ");
			stmt1.setString(1, username);
			stmt1.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Connection failure.");
			e.printStackTrace();
		}
	}

	public void reject(String username) {
		try {
			Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost/Project0", "postgres",
					"Ejux8521");
			PreparedStatement stmt1 = conn.prepareStatement("delete from pending_user where username = ? ");
			stmt1.setString(1, username);
			stmt1.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Connection failure.");
			e.printStackTrace();
		}
	}

	public void approveBank(int account_number) {
		try {
			Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost/Project0", "postgres",
					"Ejux8521");
			PreparedStatement stmt = conn
					.prepareStatement("update account set account_status = true where account_number = ?");
			stmt.setInt(1, account_number);
			stmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Connection failure.");
			e.printStackTrace();
		}
	}

	public ArrayList<PendingBankAccount> viewPendingBank() {
		ArrayList<PendingBankAccount> accounts = new ArrayList<PendingBankAccount>();
		try {
			Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost/Project0", "postgres",
					"Ejux8521");
			PreparedStatement stmt = conn.prepareStatement("select * from account where account_status = false");
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {

				PendingBankAccount pa = new PendingBankAccount(rs.getInt("account_number"), rs.getInt("balance"),
						rs.getInt("customer_id"));
				accounts.add(pa);
			}

		} catch (SQLException e) {
			System.out.println("Connection failure.");
			e.printStackTrace();
		}
		return accounts;
	}

	public void rejectBank(int account_number) {
		try {
			Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost/Project0", "postgres",
					"Ejux8521");
			PreparedStatement stmt1 = conn.prepareStatement("delete from account where account_number = ? ");
			stmt1.setInt(1, account_number);
			stmt1.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Connection failure.");
			e.printStackTrace();
		}
	}

	public ArrayList<PendingTransfer> viewPendingTransfer(int account_number) {
		ArrayList<PendingTransfer> transfer = new ArrayList<PendingTransfer>();
		try {
			Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost/Project0", "postgres",
					"Ejux8521");
			PreparedStatement stmt = conn
					.prepareStatement("select * from pending_transfer where t_to = ?");
			stmt.setInt(1, account_number);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				PendingTransfer pt = new PendingTransfer(rs.getInt("t_id"), rs.getInt("t_from"), rs.getInt("t_to"),
						rs.getInt("t_amount"), rs.getString("t_comment"));
				transfer.add(pt);
			}

		} catch (SQLException e) {
			System.out.println("Connection failure.");
			e.printStackTrace();

		}
		return transfer;
	}

	public void DeclineTransfer(int transfer_id) {
		try {
			Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost/Project0", "postgres",
					"Ejux8521");
			PreparedStatement stmt1 = conn.prepareStatement("delete from pending_transfer where t_id = ? ");
			stmt1.setInt(1, transfer_id);
			stmt1.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Connection failure.");
			e.printStackTrace();
		}
	}

	public void PendingTransfer(int from, int to, int amount, String comment) {
		try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost/Project0", "postgres",
				"Ejux8521")) {
			PreparedStatement statement = conn
					.prepareStatement("insert into pending_transfer(t_from, t_to, t_amount, t_comment) values(?,?,?,?)");
			statement.setInt(1, from);
			statement.setInt(2, to);
			statement.setInt(3, amount);
			statement.setString(4, comment);
			
			statement.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Connection failure.");
			e.printStackTrace();
		}
	}
	
}
