package com.revature.project0;

public class PendingTransfer {
private int transfer_id;
private int from;
private int to;
private int amount;
private String comment;
public PendingTransfer(int transfer_id, int from, int to, int amount, String comment) {
	super();
	this.transfer_id = transfer_id;
	this.from = from;
	this.to = to;
	this.amount = amount;
	this.comment = comment;
}
public int getTransfer_id() {
	return transfer_id;
}
public int getFrom() {
	return from;
}
public int getTo() {
	return to;
}
public int getAmount() {
	return amount;
}
public String getComment() {
	return comment;
}
@Override
public String toString() {
	return "PendingTransfer [transfer_id=" + transfer_id + ", from=" + from + ", to=" + to + ", amount=" + amount
			+ ", comment=" + comment + "]";
}




}
