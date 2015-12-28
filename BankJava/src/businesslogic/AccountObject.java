package businesslogic; 
import java.util.List;
public class AccountObject extends DataObject{
// Account details are added in this class.
private double balance;
private int acctNumber;
private String last_trans_date;
private String firstName;
private String lastName;
private String address;
private String ssn;
private String occupation;
private String emailID;
private String acctType;
private String userName;
private String passwd;
private boolean active;
private String methodToInvoke; //specifies which method in DAO has to be called private String fromAcct;
private String fromAcct;
private String toAcct;

private String[] acctList;
private String[] beneficiaryList;
private List<TransactionHistory> checkingHist ;
private List<TransactionHistory> savingsHist ;
public double getBalance() {
	return balance;
}
public void setBalance(double balance) {
	this.balance = balance;
}
public int getAcctNumber() {
	return acctNumber;
}
public void setAcctNumber(int acctNumber) {
	this.acctNumber = acctNumber;
}
public String getLast_trans_date() {
	return last_trans_date;
}
public void setLast_trans_date(String last_trans_date) {
	this.last_trans_date = last_trans_date;
}
public String getFirstName() {
	return firstName;
}
public void setFirstName(String firstName) {
	this.firstName = firstName;
}
public String getLastName() {
	return lastName;
}
public void setLastName(String lastName) {
	this.lastName = lastName;
}
public String getAddress() {
	return address;
}
public void setAddress(String address) {
	this.address = address;
}
public String getSsn() {
	return ssn;
}
public void setSsn(String ssn) {
	this.ssn = ssn;
}
public String getOccupation() {
	return occupation;
}
public void setOccupation(String occupation) {
	this.occupation = occupation;
}
public String getEmailID() {
	return emailID;
}
public void setEmailID(String emailID) {
	this.emailID = emailID;
}
public String getAcctType() {
	return acctType;
}
public void setAcctType(String acctType) {
	this.acctType = acctType;
}
public String getUserName() {
	return userName;
}
public void setUserName(String userName) {
	this.userName = userName;
}
public String getPasswd() {
	return passwd;
}
public void setPasswd(String passwd) {
	this.passwd = passwd;
}
public boolean isActive() {
	return active;
}
public void setActive(boolean active) {
	this.active = active;
}
public String getMethodToInvoke() {
	return methodToInvoke;
}
public void setMethodToInvoke(String methodToInvoke) {
	this.methodToInvoke = methodToInvoke;
}

public String getFromAcct() { 
	return fromAcct;
}
public void setFromAcct(String fromAcct) {
	this.fromAcct = fromAcct;
}
public String getToAcct() {
	return toAcct;
}
public void setToAcct(String toAcct) {
	this.toAcct = toAcct;
}
public String[] getAcctList() {
	return acctList;
}
public void setAcctList(String[] acctList) {
	this.acctList = acctList;
}
public String[] getBeneficiaryList() {
	return beneficiaryList;
}
public void setBeneficiaryList(String[] beneficiaryList) {
	this.beneficiaryList = beneficiaryList;
}
public List<TransactionHistory> getCheckingHist() {
	return checkingHist;
}
public void setCheckingHist(List<TransactionHistory> checkingHist) {
	this.checkingHist = checkingHist;
}
public List<TransactionHistory> getSavingsHist() {
	return savingsHist;
}
public void setSavingsHist(List<TransactionHistory> savingsHist) {
	this.savingsHist = savingsHist;
}




}
