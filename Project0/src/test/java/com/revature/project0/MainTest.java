package com.revature.project0;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Scanner;
public class MainTest {
	   @Test
	    void can_view_pending_transactions()
	    {
	       
	        
	        Pending pending = new Pending();
	      pending.viewPendingTransfer(50);
	    }
	   @Test
	   void can_view_pending_bank_accounts()
	   {
		   EmployeeLogin e1 = new EmployeeLogin();
		   e1.viewPendingBankAccount(new Scanner(System.in));
	   }
	   @Test
	   void can_withdraw()
	   {
		   CustomerLogin c1 = new CustomerLogin();
		   c1.withdraw(new Scanner(System.in));
	   }
	   @Test
	   void can_deposit()
	   {
		   CustomerLogin c1 = new CustomerLogin();
		   c1.deposit(new Scanner(System.in));
	   }
	   @Test
	   void can_post_transfer_Request()
	   {
		   CustomerLogin c1 = new CustomerLogin();
		   c1.postTransfer(new Scanner(System.in));
	   }
	   @Test
	   void can_decide_Transfer()
	   {
		   CustomerLogin c1 = new CustomerLogin();
		   c1.resolveTransfer(new Scanner(System.in));
	   }
	   @Test
	   void can_create_new_bank_account()
	   {
		   CustomerLogin c1 = new CustomerLogin();
		   c1.createNewBankAccount(new Scanner(System.in));
	   }
	   @Test
	   void view_transaction_log()
	   {
		   BankTransactions bt = new BankTransactions();
			bt.viewTransactions();
	   }
}
