package javaDatabaseDemo;

import java.math.BigDecimal;

public class TransactionInfo {
    private int customer_id;
    private int account_no;
    private int transaction_no;
    private String transaction_type;
    private BigDecimal amount;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
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

    public int getTransaction_no() {
        return transaction_no;
    }

    public void setTransaction_no(int transaction_no) {
        this.transaction_no = transaction_no;
    }

    public String getTransaction_type() {
        return transaction_type;
    }

    public void setTransaction_type(String transaction_type) {
        this.transaction_type = transaction_type;
    }
}
