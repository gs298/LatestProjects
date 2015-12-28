package businesslogic;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.util.List;


public class ThreadedDataObjectServer {
	//Server Class runs at port 3023 and accepts requests from Client socket.For each request
	//handler thread is initiated to service the request.
	public static void main(String[] args ) {
	int i = 1;
	AliveCounter t = new AliveCounter(); try
	{ 
		ServerSocket s = new ServerSocket(3023);
	for (;;) {
	Socket incoming = s.accept(); 
	t.setNumber(t.getNumber()+ 1); 
	System.out.println("Total Alive " + t.getNumber()); 
	new ThreadedDataObjectHandler(incoming, i, t).start(); 
	i++;
	} 
	}
	catch (Exception e) {
	System.out.println(e); }
	} }
	class ThreadedDataObjectHandler extends Thread {
	//Thread class which receives all requests, calls appropriate DAO method and returns data back to client.
	QueryDAO dao=new QueryDAO(); ObjectInputStream in; ObjectOutputStream out; 
	Connection conn;
	public ThreadedDataObjectHandler(Socket i, int c, AliveCounter a) {
	incoming = i; counter = c; Alive = a;
	}
	public void run() { try
	{
	if(conn==null || conn.isClosed())
	conn=JDBCConnect.getDBConnect();
	in = new ObjectInputStream(incoming.getInputStream());
	out= new ObjectOutputStream(incoming.getOutputStream()); obj = (AccountObject)in.readObject();
	
	if(obj!=null){
		if(obj.getMethodToInvoke()!=null && !obj.getMethodToInvoke().equals("")){
		System.out.println("Calling DAO : "+obj.getMethodToInvoke());
		if(obj.getMethodToInvoke().equals("isUserExists")){
		returnobj=dao.isUserExists(conn, obj.getUserName()); 
		out.writeObject(returnobj);
		}else if(obj.getMethodToInvoke().equals("validateUser")){ 
			returnobj=dao.validateUser(conn, obj.getUserName(),obj.getPasswd()); 
			out.writeObject(returnobj);
		}else if(obj.getMethodToInvoke().equals("isAdmin")){ 
			returnobj=dao.isAdmin(conn, obj.getUserName()); 
			out.writeObject(returnobj);
		}else if(obj.getMethodToInvoke().equals("getAccountDetails")){ 
			List<AccountObject> acctList=dao.getAccountDetails(conn,obj.getUserName());
		out.writeObject(acctList);
		}else if(obj.getMethodToInvoke().equals("transferMoney")){
			returnobj=dao.transferMoney(conn,obj.getFromAcct(),obj.getToAcct(),obj.getBalance(),obj.getUserName()); 
			out.writeObject(returnobj);
		}else if(obj.getMethodToInvoke().equals("withdrawMoney")){ 
			returnobj=dao.withdrawMoney(conn,
		obj.getFromAcct(),obj.getBalance(),obj.getUserName());
			out.writeObject(returnobj);
		
		}else if(obj.getMethodToInvoke().equals("depositMoney"))
		{ 
			returnobj=dao.depositMoney(conn,obj.getToAcct(),obj.getBalance(),obj.getUserName());
			out.writeObject(returnobj);
		}else if(obj.getMethodToInvoke().equals("getAccountList")){
		returnobj=dao.getAccountList(conn,obj.getUserName()); 
		out.writeObject(returnobj);
		}else if(obj.getMethodToInvoke().equals("getBeneficiariesList")){ 
			returnobj=dao.getBeneficiariesList(conn,obj.getUserName()); 
			out.writeObject(returnobj);
		}
		}else if(obj.getMethodToInvoke().equals("createNewAccount")){ 
			returnobj=dao.createNewAccount(conn,obj);
			out.writeObject(returnobj);
		}else if(obj.getMethodToInvoke().equals("getTransactionHistory")){ 
			returnobj=dao.getTransactionHistory(conn,obj.getUserName()); 
			out.writeObject(returnobj);
		}else if(obj.getMethodToInvoke().equals("getAccountDetailsByID")){ 
			returnobj=dao.getAccountDetailsByID(conn,String.valueOf(obj.getAcctNumber()));
			out.writeObject(returnobj);
		}else if(obj.getMethodToInvoke().equals("freezeOrActivateAccount")){
			returnobj=dao.freezeOrActivateAccount(conn,String.valueOf(obj.getAcctNumber()));
			out.writeObject(returnobj);
		}else if(obj.getMethodToInvoke().equals("updateAccountDetails")){
			returnobj=dao.updateAccountDetails(conn,obj);
			out.writeObject(returnobj);
		}
	}
	
	in.close();
	out.close();
	
	Alive.setNumber(Alive.getNumber()-1);
	System.out.println("Total Alive " + Alive.getNumber());
	}
	catch (Exception e)
	{
		System.out.println(e);
	}
	}
	AccountObject obj = null;
	AccountObject returnobj = null;
	AliveCounter Alive;
	private Socket incoming;
	private int counter;
	
}
