package businesslogic;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
public class QueryDAO {
	DataObject message = new DataObject();
	
	private String CHECK_USER_QUERY ="select count(*) from user_info where user_name=?";
	private String VALIDATE_USER_QUERY ="select password from user_info where user_name=?";
	private String IS_ADMIN_QUERY ="select count(*) from user_info where user_name=? and is_admin=1";
	
	private String ACC_DETAILS_QUERY ="select account_type,account_num,balance,last_transaction_date,is_active from account_info a,user_info u where u.user_name=? and u.user_id=a.user_id";
	private String ACC_LIST_QUERY ="select distinct(account_num) from account_info a,user_info u where u.user_name=? and u.user_id=a.user_id";
	private String BEN_LIST_QUERY ="select distinct(account_num) from acc_beneficiaries a,user_info u where u.user_name=? and u.user_id=a.user_id";
	private String ACC_BAL_QUERY ="select balance from account_info where account_num=?";
	private String BAL_UPDATE_QUERY ="update account_info set balance=?,last_transaction_date=? where account_num=?";
	
	
	private String INSERT_ACC_INFO_QUERY="insert into account_info values (?,?,?,?,?,?)";
	private String INSERT_USER_INFO_QUERY="insert into user_info(user_name,password,email,is_admin,first_name,last_name,address,occupation,ssn) values (?,?,?,?,?,?,?,?,?)";
	private String GET_ACCOUNT_NUM_QUERY="select max(account_num) from account_info where account_type=?";
	private String GET_USERID_QUERY ="select user_id from user_info where ssn=?";
	private String GET_USERID_FROM_NAME_QUERY ="select user_id from user_info where user_name=?";
	private String TRANSACTION_HIST_QUERY ="select tr.transaction_date,tr.amount,tr.transaction_type,tr.acct_num,a.account_type from transaction_history tr,user_info u,account_info a where tr.user_id=u.user_id and a.account_num=tr.acct_num and u.user_name=?";

	private String ACC_DETAILS_BY_ID_QUERY="select a.account_type,a.account_num,a.balance,a.last_transaction_date,a.is_active,u.user_name,u.e mail,u.first_name,u.last_name,u.address,u.occupation from account_info a,user_info u"
		 +"where a.account_num=? and a.user_id=u.user_id";
			private String UPDATE_ACC_FREEZE="update account_info set is_active=0 where account_num=? ";
			private String UPDATE_ACC_ACTIVATE="update account_info set is_active=1 where account_num=? ";
			private String UPDATE_USER_INFO_QUERY="update user_info u,account_info a set user_name=?,email=?,first_name=?,last_name=?,address=?,occupation =? where a.account_num=? and a.user_id=u.user_id";
			private String INSERT_TRANSACTION_HIST="insert into transaction_history values (?,?,?,?,?)";

			//method to check whether username exists or not
		public AccountObject isUserExists(Connection conn, String userName){
			if(conn!=null){
			AccountObject obj=new AccountObject();
			PreparedStatement stmt = null;
			ResultSet rs = null;
			
			try {
				stmt = conn.prepareStatement(CHECK_USER_QUERY); 
				stmt.setString(1, userName); 
				System.out.println("Runnin SQL :" + stmt);
				rs = stmt.executeQuery();
				while(rs.next()){
				int count =rs.getInt(1);
				if(count==0){
				obj.setMessage("Error"); 
				}else{
				obj.setMessage("Success"); }
				}
				return obj;
				} catch (SQLException e) {
				e.printStackTrace();
				}
			finally{
				try{ 
					stmt.close();
					rs.close();
					} catch (SQLException e) {
					e.printStackTrace();
					}
			}
			}
			return null;
			
			}
			
			public AccountObject validateUser(Connection conn, String userName,String password){ 
				if(conn!=null){
				AccountObject obj=new AccountObject();
				PreparedStatement stmt = null;
				ResultSet rs = null;
				try {
				stmt = conn.prepareStatement(VALIDATE_USER_QUERY);
				stmt.setString(1, userName);
				System.out.println("Runnin SQL :" + stmt);
				rs = stmt.executeQuery();
				while(rs.next()){
				String pass =rs.getString(1); if(password.equals(pass)){
				obj.setMessage("Success"); }else{
				obj.setMessage("Error");
				} }
				return obj;
				} catch (SQLException e) {
				e.printStackTrace(); }
				finally
				{
				try {
					
					stmt.close();
					rs.close();
				} catch (SQLException e) {
				e.printStackTrace(); 
				}
			}
		}
			return null; 
	}
	
			public AccountObject isAdmin(Connection conn, String userName){ 
				if(conn!=null){
				AccountObject obj=new AccountObject();
				PreparedStatement stmt = null; 
				ResultSet rs = null;
				try {
				stmt = conn.prepareStatement(IS_ADMIN_QUERY); 
				stmt.setString(1, userName);
				System.out.println("Runnin SQL :" + stmt);
				rs = stmt.executeQuery();
				while(rs.next()){
				int count =rs.getInt(1); if(count>0){
				obj.setMessage("true"); }else{
				obj.setMessage("false");
				} 
				}
				return obj;
				} catch (SQLException e) {
				e.printStackTrace(); }
				finally
				{
					try {
						stmt.close();
						rs.close();
						} catch (SQLException e) {
						e.printStackTrace(); }
				}
			}
			return null;
			}
					
	public List<AccountObject> getAccountDetails(Connection conn, String userName){
				PreparedStatement stmt = null;
				ResultSet rs = null;
				List<AccountObject> acctList = new ArrayList<AccountObject>(); 
				AccountObject obj;
				
				String[][] accInfo=new String[2][4]; try {
					stmt = conn.prepareStatement(ACC_DETAILS_QUERY);
					stmt.setString(1, userName);
					rs = stmt.executeQuery();
					System.out.println("Runnin SQL :" + stmt); 
					while(rs.next()){
						obj = new AccountObject(); 
						obj.setAcctType(rs.getString(1));
						obj.setAcctNumber(rs.getInt(2)); 
						obj.setBalance(rs.getDouble(3)); 
						obj.setLast_trans_date(rs.getString(4)); 
						if(rs.getInt(5)==1){
							obj.setActive(true); }
						else{
							obj.setActive(false); 
							}
						acctList.add(obj); 
						}
					return acctList;
				} catch (SQLException e) {
				e.printStackTrace(); }
				finally
				{
					try {
						stmt.close();
						rs.close();
						} catch (SQLException e) {
								e.printStackTrace(); }
							}
				return null;
	}
					
	public AccountObject getAccountList(Connection conn, String userName){
		PreparedStatement stmt = null;
	ArrayList a=new ArrayList();
	ResultSet rs = null;
	AccountObject obj=new AccountObject(); 
	try {
	stmt = conn.prepareStatement(ACC_LIST_QUERY);
	stmt.setString(1, userName);
	rs = stmt.executeQuery(); 
	System.out.println("Runnin SQL :" + stmt);
	
	while(rs.next()){
	a.add(rs.getString(1));
	}
	String[] accList = new String[a.size()];
	accList = (String[]) a.toArray(accList);
	obj.setAcctList(accList);
	return obj;
	} catch (SQLException e) {
	e.printStackTrace(); }
	finally
	{
	try {
	stmt.close();
	rs.close();
	} catch (SQLException e) {
	e.printStackTrace(); }
	}
	return null;
	}
	
	public AccountObject getBeneficiariesList(Connection conn, String userName){
	PreparedStatement stmt = null;
	ArrayList a=new ArrayList();
	ResultSet rs = null;
	AccountObject obj=new AccountObject(); 
	try {
	stmt = conn.prepareStatement(BEN_LIST_QUERY); stmt.setString(1, userName);
	rs = stmt.executeQuery(); System.out.println("Runnin SQL :" + stmt); while(rs.next()){
	a.add(rs.getString(1));
	}
	String[] benList = new String[a.size()]; benList = (String[]) a.toArray(benList);
	obj.setBeneficiaryList(benList);
	return obj;
	} catch (SQLException e) { 
		e.printStackTrace();
	}
	finally
	{
	try {
	stmt.close();
	rs.close();
	} catch (SQLException e) {
	e.printStackTrace(); }
	}
	return null;}

	
	public AccountObject transferMoney(Connection conn, String fromAcc,String toAcc,double amount,String userName ){
		AccountObject obj = new AccountObject(); 
		double oldSourceBal=0;
	
		double oldDestBal=0;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
		stmt = conn.prepareStatement(ACC_BAL_QUERY);
		stmt.setString(1, fromAcc); 
		
		System.out.println("Runnin SQL :" + stmt);
		rs = stmt.executeQuery();
		while(rs.next()){
		oldSourceBal = rs.getFloat(1);
		}
		if(oldSourceBal-amount <0){
		obj.setMessage("Insufficient Funds to continue transfer"); 
		return obj;
		}else{
		double newSourceBal = oldSourceBal-amount;
		stmt.setString(1, toAcc); 
		System.out.println("Runnin SQL :" + stmt); 
		rs = stmt.executeQuery(); 
		while(rs.next()){
		oldDestBal = rs.getFloat(1); 
		}
		double newDestBal =oldDestBal+amount;
		Calendar currenttime = Calendar.getInstance();
		Date sqldate = new Date((currenttime.getTime()).getTime());
		stmt = conn.prepareStatement(BAL_UPDATE_QUERY); 
		stmt.setDouble(1, newSourceBal); 
		stmt.setDate(2, sqldate);
		stmt.setString(3, fromAcc);
		System.out.println("Runnin SQL :" + stmt);
		stmt.executeUpdate();
		stmt.setDouble(1, newDestBal); 
		stmt.setDate(2, sqldate);
		stmt.setString(3, toAcc);
		System.out.println("Runnin SQL :" + stmt); 
		stmt.executeUpdate();
		//inserting transaction history records
		stmt = conn.prepareStatement(GET_USERID_FROM_NAME_QUERY); 
		stmt.setString(1, userName);
		System.out.println("Runnin SQL :" + stmt);
		rs = stmt.executeQuery();
		int user_id=0;
		while(rs.next()){
		user_id=rs.getInt(1); }
		stmt = conn.prepareStatement(INSERT_TRANSACTION_HIST); 
		stmt.setInt(1, user_id);
		
		stmt.setDate(2, sqldate); stmt.setDouble(3, amount); stmt.setString(4, "CREDIT"); stmt.setString(5, toAcc); System.out.println("Runnin SQL :" + stmt); stmt.executeUpdate();
		stmt.setString(4, "DEBIT"); stmt.setString(5, fromAcc); System.out.println("Runnin SQL :" + stmt); stmt.executeUpdate();
		obj.setMessage("Transaction Successful"); }
		} catch (SQLException e) {
		obj.setMessage("Error in Transaction. Please contact System Administrator"); e.printStackTrace();
		return obj;
		}
		finally
		{
		try {
		stmt.close();
		rs.close();
		} catch (SQLException e) {
		e.printStackTrace(); }
		}
		return obj; }
		    //withdraw money from a account
		public AccountObject withdrawMoney(Connection conn, String fromAcc,double amount,String userName ){
		AccountObject obj = new AccountObject(); double oldSourceBal=0;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
		stmt = conn.prepareStatement(ACC_BAL_QUERY); stmt.setString(1, fromAcc); System.out.println("Runnin SQL :" + stmt); rs = stmt.executeQuery();
		while(rs.next()){
		oldSourceBal = rs.getFloat(1); }
		if(oldSourceBal-amount <0){
		obj.setMessage("Insufficient Funds to continue transfer"); return obj;
		}else{
		double newSourceBal = oldSourceBal-amount;
		Calendar currenttime = Calendar.getInstance();
		Date sqldate = new Date((currenttime.getTime()).getTime());
		stmt = conn.prepareStatement(BAL_UPDATE_QUERY); stmt.setDouble(1, newSourceBal);
		
		
		stmt.setDate(2, sqldate); stmt.setString(3, fromAcc); System.out.println("Runnin SQL :" + stmt); stmt.executeUpdate();
		stmt = conn.prepareStatement(GET_USERID_FROM_NAME_QUERY); stmt.setString(1, userName);
		System.out.println("Runnin SQL :" + stmt);
		rs = stmt.executeQuery();
		int user_id=0; while(rs.next()){
		user_id=rs.getInt(1); }
		stmt = conn.prepareStatement(INSERT_TRANSACTION_HIST); stmt.setInt(1, user_id);
		stmt.setDate(2,sqldate);
		stmt.setDouble(3, amount);
		stmt.setString(4, "DEBIT"); stmt.setString(5, fromAcc); System.out.println("Runnin SQL :" + stmt); stmt.executeUpdate();
		obj.setMessage("Transaction Successful"); }
		} catch (SQLException e) {
		obj.setMessage("Error in Transaction. Please contact System Administrator"); e.printStackTrace();
		return obj;
		}
		finally
		{
		try {
		stmt.close();
		rs.close();
		} catch (SQLException e) {
		e.printStackTrace(); }
		}
		return obj; }
		    //deposit money to a account
		public AccountObject depositMoney(Connection conn,String toAcc,double amount, String userName ){
		AccountObject obj = new AccountObject(); double oldDestBal=0;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
		stmt = conn.prepareStatement(ACC_BAL_QUERY); stmt.setString(1, toAcc); System.out.println("Runnin SQL :" + stmt); rs = stmt.executeQuery();
		
		while(rs.next()){
			oldDestBal = rs.getFloat(1);
			}
			double newDestBal =oldDestBal+amount;
			Calendar currenttime = Calendar.getInstance();
			Date sqldate = new Date((currenttime.getTime()).getTime());
			stmt = conn.prepareStatement(BAL_UPDATE_QUERY); stmt.setDouble(1, newDestBal);
			stmt.setDate(2, sqldate);
			stmt.setString(3, toAcc); System.out.println("Runnin SQL :" + stmt); stmt.executeUpdate();
			stmt = conn.prepareStatement(GET_USERID_FROM_NAME_QUERY); stmt.setString(1, userName);
			System.out.println("Runnin SQL :" + stmt);
			rs = stmt.executeQuery();
			int user_id=0; while(rs.next()){
			user_id=rs.getInt(1); }
			stmt = conn.prepareStatement(INSERT_TRANSACTION_HIST); stmt.setInt(1, user_id);
			stmt.setDate(2, sqldate);
			stmt.setDouble(3, amount);
			stmt.setString(4, "CREDIT"); stmt.setString(5, toAcc); System.out.println("Runnin SQL :" + stmt); stmt.executeUpdate();
			obj.setMessage("Transaction Successful");
			} catch (SQLException e) {
			obj.setMessage("Error in Transaction. Please contact System Administrator"); e.printStackTrace();
			return obj;
			}
			finally
			{
			try {
			stmt.close();
			rs.close();
			} catch (SQLException e) {
			e.printStackTrace(); }
			}
			return obj; }
			public AccountObject createNewAccount(Connection conn,AccountObject acctObj ){
				AccountObject obj = new AccountObject(); PreparedStatement stmt = null;
				ResultSet rs=null;
				try {
					stmt = conn.prepareStatement(INSERT_USER_INFO_QUERY); 
					stmt.setString(1, acctObj.getUserName()); 
					stmt.setString(2, acctObj.getPasswd()); 
					stmt.setString(3, acctObj.getEmailID()); 
					stmt.setString(4, "0");
					stmt.setString(5, acctObj.getFirstName()); 
					stmt.setString(6, acctObj.getLastName()); 
					stmt.setString(7, acctObj.getAddress()); 
					stmt.setString(8, acctObj.getOccupation()); 
					stmt.setString(9, acctObj.getSsn());
					System.out.println("Runnin SQL :" + stmt); 
					stmt.executeUpdate();
					
					stmt = conn.prepareStatement(GET_USERID_QUERY); 
					stmt.setString(1, acctObj.getSsn()); System.out.println("Runnin SQL :" + stmt);
					rs = stmt.executeQuery();
					int user_id=0; while(rs.next()){
					user_id=rs.getInt(1); }
					stmt = conn.prepareStatement(INSERT_ACC_INFO_QUERY);
					stmt.setString(1, acctObj.getAcctType());
					stmt.setInt(2, getNextAvailableAcctNumber(conn,acctObj.getAcctType())); stmt.setDouble(3, acctObj.getBalance());
					stmt.setString(4, null);
					stmt.setInt(5, user_id);
					stmt.setInt(6, 1);
					System.out.println("Runnin SQL :" + stmt);
					stmt.executeUpdate();
					obj.setMessage("UserAccount " +acctObj.getUserName() + " successfully Created.");
					} catch (SQLException e) {
						obj.setMessage("Error in Transaction. Please contact System Administrator"); 
						e.printStackTrace();
						return obj;
						}
						finally
						{
						try {
						stmt.close();
						} catch (SQLException e) {
						e.printStackTrace(); }
						}
				return obj;
			} 
		private int getNextAvailableAcctNumber(Connection conn,String acctType){
			PreparedStatement stmt = null;
			int number=0;
			ResultSet rs=null;
			try {
			stmt = conn.prepareStatement(GET_ACCOUNT_NUM_QUERY); 
			stmt.setString(1, acctType); 
			System.out.println("Runnin SQL :" + stmt);
			rs = stmt.executeQuery();
			while(rs.next()){ number=rs.getInt(1);
			}
			return number+1;
			} catch (SQLException e) {
			e.printStackTrace(); }
			finally
			{
			try {
			stmt.close();
			rs.close();
			} catch (SQLException e) {
			e.printStackTrace(); }
			}
			return 0;
			}
			public AccountObject getTransactionHistory(Connection conn, String userName){
			PreparedStatement stmt = null;
			ArrayList<TransactionHistory> checking=new ArrayList<TransactionHistory>(); 
			ArrayList<TransactionHistory> saving=new ArrayList<TransactionHistory>(); 
			ResultSet rs = null;
			AccountObject obj=new AccountObject();
			TransactionHistory hist;
			try {
			stmt = conn.prepareStatement(TRANSACTION_HIST_QUERY); stmt.setString(1, userName); System.out.println("Running SQL :" + stmt);
			rs = stmt.executeQuery();
			while(rs.next()){
			hist=new TransactionHistory();
			hist.setTransactionDate(rs.getDate(1)); 
			hist.setAmount(rs.getDouble(2)); 
			hist.setTransactionType(rs.getString(3));
			hist.setAcctNum(rs.getString(4)); 
			if(rs.getString(5).equalsIgnoreCase("CHECKING")){
			checking.add(hist); }else{
				saving.add(hist); }
			} obj.setCheckingHist(checking);
			obj.setSavingsHist(saving);
			return obj;
			} catch (SQLException e) { e.printStackTrace();
			}
			finally
			{
			try {
			stmt.close();
			rs.close();
			} catch (SQLException e) {
			e.printStackTrace(); }
			}
			return null;
			}
			public AccountObject getAccountDetailsByID(Connection conn, String acctNum){
			PreparedStatement stmt = null; 
			ResultSet rs = null; 
			AccountObject obj=null;
			try {
			stmt = conn.prepareStatement(ACC_DETAILS_BY_ID_QUERY); 
			stmt.setString(1, acctNum);
			rs = stmt.executeQuery();
			
			System.out.println("Runnin SQL :" + stmt);
			while(rs.next()){
			obj = new AccountObject();
			obj.setAcctType(rs.getString(1));
			obj.setAcctNumber(rs.getInt(2));
			obj.setBalance(rs.getDouble(3)); 
			obj.setLast_trans_date(rs.getString(4)); 
			
			if(rs.getInt(5)==1){
			obj.setActive(true); }else{
			obj.setActive(false); }
			obj.setUserName(rs.getString(6)); obj.setEmailID(rs.getString(7)); obj.setFirstName(rs.getString(8)); obj.setLastName(rs.getString(9)); obj.setAddress(rs.getString(10)); obj.setOccupation(rs.getString(11));
			}
			
			return obj;
			} catch (SQLException e) {
				e.printStackTrace(); }
			finally
			{
			try {
				stmt.close(); 
				rs.close();
			} catch (SQLException e) {
			e.printStackTrace(); }
			}
			return null; }
			
			public AccountObject freezeOrActivateAccount(Connection conn, String acctNum){
				PreparedStatement stmt = null; ResultSet rs = null;
				AccountObject obj=new AccountObject(); 
				boolean active=false;
				try {
				stmt = conn.prepareStatement(ACC_DETAILS_BY_ID_QUERY); 
				stmt.setString(1, acctNum);
				rs = stmt.executeQuery();
				System.out.println("Runnin SQL :" + stmt);
				while(rs.next()){ if(rs.getInt(5)==1){
				active=true; }else{
				active=false; }
				}
				if(active){
				stmt = conn.prepareStatement(UPDATE_ACC_FREEZE); 
				stmt.setString(1, acctNum);
				stmt.executeUpdate();
				System.out.println("Runnin SQL :" + stmt); 
				obj.setMessage("Account # : " +acctNum + " Freeze complete");
				}else{
				stmt = conn.prepareStatement(UPDATE_ACC_ACTIVATE); 
				stmt.setString(1, acctNum);
				stmt.executeUpdate();
				System.out.println("Runnin SQL :" + stmt); 
				obj.setMessage("Account # : " +acctNum+ " UnFreeze complete");
				}
				return obj;
				} catch (SQLException e) {
				e.printStackTrace(); }
				finally
				{
				try {
				stmt.close(); rs.close();
				} catch (SQLException e) { e.printStackTrace();
				} }
				return null; }
			
			public AccountObject updateAccountDetails(Connection conn,AccountObject acctObj ){
				
				AccountObject obj = new AccountObject();
				PreparedStatement stmt = null; try {
					stmt = conn.prepareStatement(UPDATE_USER_INFO_QUERY);
					stmt.setString(1, acctObj.getUserName());
					stmt.setString(2, acctObj.getEmailID()); 
					stmt.setString(3, acctObj.getFirstName()); 
					stmt.setString(4, acctObj.getLastName());
					stmt.setString(5, acctObj.getAddress()); 
					stmt.setString(6, acctObj.getOccupation() ); 
					stmt.setInt(7, acctObj.getAcctNumber()); 
					System.out.println("Runnin SQL :" + stmt);
			stmt.executeUpdate();
			obj.setMessage("Account Details updated successfully");
			} catch (SQLException e) {
			obj.setMessage("Error in Transaction. Please contact System Administrator");
			e.printStackTrace();
			return obj;
			}
			finally
			{
			try {
			stmt.close();
			} catch (SQLException e) {
			e.printStackTrace(); }
			}
			return obj;
			}
			
			
		
}	
	

