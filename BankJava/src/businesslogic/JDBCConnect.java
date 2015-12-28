package businesslogic;

import java.sql.Connection;
import java.sql.DriverManager;
public class JDBCConnect {
	
	//Class for getting DBConnection.Making "conn" object static, to avoid multiple connections being opened
	
	private static Connection conn;
	
	
	public static Connection getDBConnect() {
	
	String url = "sql.njit.edu";
	String ucid = "at353";
	String dbpassword = "********";
	
	
	if(conn==null){
		try {
		
		Class.forName("org.gjt.mm.mysql.Driver").newInstance();
		conn = DriverManager.getConnection("jdbc:mysql://"+url+"/"+ucid+"?user="+ucid+"&password="+dbpassword);
		
		return conn;
		}
		catch (Exception e) {
		e.printStackTrace();
		}
		}else{
		return conn;
		}
		return null;
	
		}
	
}
