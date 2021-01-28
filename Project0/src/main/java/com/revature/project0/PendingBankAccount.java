package com.revature.project0;

public class PendingBankAccount {
	
	@Override
	public String toString() {
		return "PendingBankAccount [accountNumber=" + accountNumber + ", balance=" + balance + ", customerId="
				+ customerId + "]";
	}
	private int accountNumber;
	private int balance;
	private int customerId;
	public PendingBankAccount(int accountNumber, int balance, int customerId) {
		super();
		this.accountNumber = accountNumber;
		this.balance = balance;
		this.customerId = customerId;
	}
	public int getAccountNumber() {
		return accountNumber;
	}
	public int getBalance() {
		return balance;
	}
	public int getCustomerId() {
		return customerId;
	}
}
