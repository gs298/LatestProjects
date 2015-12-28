package businesslogic;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Date;

import javax.swing.*;

public class OnlineBank implements ActionListener{
	protected JFrame baseFrame; protected JLabel header;
	protected JLabel status;
	protected JPanel controlPanel;
	protected CardLayout cards; protected JTextField username; 
	protected JPasswordField password; protected JButton submit; 
	protected DBClient dbClient; protected AccountObject acctObj; 
	protected AccountObject returnObj;
	OnlineBank(){ 
		initialize();
	}
	public void actionPerformed(ActionEvent e){
		if(e.getSource()==submit){
			
			String user = username.getText();
			String pass = password.getText();
			
			if(user==null || user.equals("") || pass==null || pass.equals("")){
				JOptionPane.showMessageDialog(controlPanel,"Please enter both username and password","ERROR",JOptionPane.ERROR_MESSAGE);
			}else { 
				dbClient = new DBClient();
			}
				acctObj = new AccountObject(); 
				acctObj.setUserName(user);
				acctObj.setMethodToInvoke("isUserExists"); 
				returnObj=dbClient.makeServerCall(acctObj);
				
				if(returnObj!=null && !(returnObj.getMessage().equals("Error"))){
					acctObj = new AccountObject();
				acctObj.setUserName(user);
				acctObj.setPasswd(pass); acctObj.setMethodToInvoke("validateUser");
				returnObj=null; returnObj=dbClient.makeServerCall(acctObj);
				
				       if(returnObj!=null && !(returnObj.getMessage().equals("Error"))){
					
				acctObj = new AccountObject();
				acctObj.setUserName(user); acctObj.setMethodToInvoke("isAdmin");
				returnObj=null; returnObj=dbClient.makeServerCall(acctObj);
				
				             if(returnObj!=null)
					             if(returnObj.getMessage().equals("true")){
					cards.show(controlPanel, "AdminOptions"); 
					controlPanel.setSize(1000,600);
					baseFrame.setLayout(new BorderLayout());
					header.setText("User Logged in : Administrator"); 
					status.setText(new Date().toString());
					}else{
						cards.show(controlPanel, "UserOptions"); 
						controlPanel.setSize(1000,600); 
						baseFrame.setLayout(new BorderLayout());
						header.setText("User Logged in : " + user);
						status.setText(new Date().toString());
					}
				
			}else{
				JOptionPane.showMessageDialog(controlPanel,"Incorrect Password",
						"Error",JOptionPane.ERROR_MESSAGE);
				}}else{
					JOptionPane.showMessageDialog(controlPanel," Incorrect UserName",
					"Error",JOptionPane.ERROR_MESSAGE);
					}
				}		
			}
		
	
		private void initialize(){
			//code for initializing login screen
			baseFrame = new JFrame("Online Banking Application"); baseFrame.setSize(1000,800);
			baseFrame.setLayout(new GridLayout(3,5)); baseFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent){ if(dbClient!=null){
			dbClient.closeDBClient(); }
			System.exit(0); } });
			
			header = new JLabel("Welcome to Online Banking", JLabel.CENTER); 
			header.setFont(new Font(header.getFont().getFontName(), Font.BOLD, 17));
			status = new JLabel(new Date().toString(),JLabel.CENTER); 
			status.setFont(new Font(status.getFont().getFontName(),Font.BOLD, 17));
			status.setSize(350,100);
			//controlpanel is set to CardLayout so that multiple windows dont pop up
			cards = new CardLayout();
			controlPanel = new JPanel(); 
			controlPanel.setLayout(cards);
			cards.show(controlPanel, "UserSelection");
			
			baseFrame.add(header);
			
			
			baseFrame.add(controlPanel,BorderLayout.CENTER);
			baseFrame.add(status);
			baseFrame.getContentPane().setBackground(new Color(96,204,204));
			JPanel userSelectionPanel = new JPanel();
			//userSelectionPanel for username/password fields. GridBagLayout is used to position the fields. 
			GridBagLayout g = new GridBagLayout();
			GridBagConstraints c = new GridBagConstraints();
			userSelectionPanel.setLayout(g);
			userSelectionPanel.setBackground(new Color(96,204,204));
			userSelectionPanel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
			username= new JTextField(15);
			password= new JPasswordField(15);
			JLabel userLbl = new JLabel("Username: ");
			JLabel passLbl = new JLabel("Password: ");
			submit = new JButton ("Login");
			
			c.gridx = 0;
			c.gridy = 0;
			userSelectionPanel.add(userLbl,c);
			c.gridx = 1;
			c.gridy = 0;
			userSelectionPanel.add(username,c);
			c.gridx = 0;
			c.gridy = 1;
			userSelectionPanel.add(passLbl,c);
			c.insets = new Insets(10,0,0,0);
			c.gridx = 1;
			c.gridy = 1;
			userSelectionPanel.add(password,c);
			c.gridx = 1;
			c.gridy = 3;
			userSelectionPanel.add(submit,c);
			submit.addActionListener(this);
			controlPanel.add(userSelectionPanel, "UserSelection");
			baseFrame.setVisible(true);
		}
	}
			
			
	
	