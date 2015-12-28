package businesslogic;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.List;
public class DBClient {

//Client class that talks to server program running in afsconnect1/3023 location.
String serverName = "";
int port = 3023;
Socket client;
ObjectOutputStream outputStream;
ObjectInputStream inputStream;


//method to get data from DB
public AccountObject makeServerCall(AccountObject obj){
	try {

		client= new Socket(serverName, port); 
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} 
	catch (IOException e) {
		e.printStackTrace(); 
		}
	try {
		outputStream = new ObjectOutputStream(client.getOutputStream());
		inputStream = new ObjectInputStream(client.getInputStream()); 
		outputStream.writeObject(obj);
		AccountObject returnObj;
		returnObj = (AccountObject)inputStream.readObject();
		return returnObj;
		} catch (ClassNotFoundException e) { 
			e.printStackTrace();
		}
	   
        catch(IOException e)
		{ 
			e.printStackTrace();
		}
		return null; 
		}
 // another method to get List data from DB
	public List makeServerCallForList(AccountObject obj){
		try {
			client = new Socket(serverName , port);
		}
		catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace(); 
			}
try {
	outputStream = new ObjectOutputStream(client.getOutputStream());
	inputStream = new ObjectInputStream(client.getInputStream());
	outputStream.writeObject(obj);
	List returnObj;
   returnObj = (List)inputStream.readObject();
	return returnObj;
	} catch (ClassNotFoundException e) {
	e.printStackTrace();
	}catch (IOException e) {
		e.printStackTrace();}
	return null;
	}
	
	
	
	//method to close all streams and socket. called when application closes
	public void closeDBClient(){
	try {
	client.close();
	outputStream.close();
	inputStream.close();
	} catch (IOException e) {
	e.printStackTrace();
	}
	}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	