package javaDatabaseDemo;

import java.math.BigDecimal;

public class AccountInfo {
	private int customer_id;
	private int account_no;
	private BigDecimal balance;
	private String status;
	private String branch;

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getStatus(){
		return status;
	}
	public int getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(int customer_id) {
		this.customer_id = customer_id;
	}
	public int getAccount_no() {
		return account_no;
	}
	public void setAccount_no(int account_no) {
		this.account_no = account_no;
	}
	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	public void setStatus(String status){
		this.status = status;
	}
	public String toString()
	{
		return "----------------------------------------------------------"+"\n"
				+"Account_no:"+account_no+"  |  "+"Balance:"+ balance;
	}

}
