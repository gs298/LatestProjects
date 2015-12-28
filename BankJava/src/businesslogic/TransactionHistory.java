package businesslogic;
import java.io.Serializable;
import java.sql.Date;
public class TransactionHistory implements Serializable {

	protected Date transactionDate;
	protected double amount;
	protected String transactionType;
	protected String acctNum;
	public Date getTransactionDate() {
	
	return transactionDate;
	}
	public void setTransactionDate(Date transactionDate) {
	this.transactionDate = transactionDate;
	}
	public double getAmount() {
	return amount;
	}
	public void setAmount(double amount) {
	this.amount = amount;
	}
	public String getTransactionType() {
	return transactionType;
	}
	public void setTransactionType(String transactionType) {
	
	this.transactionType = transactionType;
	}
	public String getAcctNum() {
	return acctNum;
	}
	public void setAcctNum(String acctNum) {
	
	this.acctNum = acctNum;
	}
}
