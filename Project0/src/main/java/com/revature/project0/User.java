package com.revature.project0;

public class User {
	private String password;
	private String username;
	private String first_name;
	private String last_name;
	private int customer_id;

	@Override
	public String toString() {
		return "User [password=" + password + ", username=" + username + ", first_name=" + first_name + ", last_name="
				+ last_name + ", customer_id=" + customer_id + "]";
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public User(String password, String username, String first_name, String last_name, int customer_id) {
		super();
		this.password = password;
		this.username = username;
		this.first_name = first_name;
		this.last_name = last_name;
		this.customer_id = customer_id;
	}

	public User() {
		super();
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public int getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(int customer_id) {
		this.customer_id = customer_id;
	}

	public User(String password, String username) {
		super();
		this.password = password;
		this.username = username;
	}

}
