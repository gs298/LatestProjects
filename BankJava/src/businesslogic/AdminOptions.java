package businesslogic;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import java.util.List;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

class AdminOptions extends JPanel implements ActionListener{
 OnlineBank bankObj;
 
 
 JPanel newAcct,freezeAcct,searchAccount,acctDetails,editAcct,branchDetails; 
 final static int GAP = 5;
//Fields for creating a new Bank Account
	JLabel desc,searchAcct;
	JTextField firstName,lastName,ssn,occupation,emailID,initialBal,userName,acctNumber,userNameForSearch ,userNameForEdit,emailForEdit,firstNameForEdit,lastNameForEdit,occupationForEdit;
	JTextArea address,addressForEdit;
	
	String[] acctTypeList={"CHECKING","SAVINGS"};
	JComboBox acctType;
	JPasswordField passwd;
	JButton createBtn,backBtn,resetBtn,freezeBtn,activateBtn,searchBtn,acctDetailsBtn,acctEditBtn,searchForFreezeBtn,updateBtn,backBtnForAcct,logoutBtn;
	JRadioButton b1,b2;
	
	private static DecimalFormat df = new DecimalFormat("#0.00");
	
   AdminOptions(){
	   
     //Creating logout button
	   logoutBtn= new JButton("LogOut"); 
	   logoutBtn.setAlignmentX(Component.RIGHT_ALIGNMENT);
	   logoutBtn.addActionListener(this);
	   add(logoutBtn);
	   
	   add(new JLabel(" "));
	   setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); 
	   setBackground(new Color(96,204,204));
	   
	   JPanel treePanel= new JPanel(); //Create top node of a tree
	   
	   final DefaultMutableTreeNode top = new DefaultMutableTreeNode("Admin Options");
	   
   //Create a subtree UG
	   final DefaultMutableTreeNode a1 = new DefaultMutableTreeNode("Create New Account");
	   top.add(a1);
	   final DefaultMutableTreeNode a2 = new DefaultMutableTreeNode("Freeze/UnFreezeAccount"); 
	   top.add(a2);
	   final DefaultMutableTreeNode a3 = new DefaultMutableTreeNode("View/Edit Account Details");
	   top.add(a3);
	   final DefaultMutableTreeNode a4 = new DefaultMutableTreeNode("View Branch Details"); top.add(a4);
	   
   //Creating tree
	   UIManager.put("Tree.rendererFillBackground", false);

	   
	   final JTree tree = new JTree(top);
	   
	   int v = ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;
	   int h = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED; 
	   final JScrollPane jsp = new JScrollPane(tree,v,h);
	   
	   
	   jsp.setPreferredSize(new Dimension(600,300));
	   tree.setBackground(new Color(96,204,204));
	   jsp.setBorder(BorderFactory.createMatteBorder(1,1,2,2,Color.darkGray)); 
	   jsp.setAlignmentX(Component.CENTER_ALIGNMENT);
	   
	   treePanel.add(jsp);
	   treePanel.setBackground(new Color(96,204,204));
	   add(treePanel);
	   
	   tree.addMouseListener( new MouseAdapter() {
	   public void mouseClicked( MouseEvent me) {
	   TreePath tp = tree.getPathForLocation(me.getX(),me.getY() );
	   
	   if( tp != null )
	   {
		   if(tp.toString().endsWith("Create New Account]"))
		   { 
			   createNewAccountPanel(); 
			   bankObj.controlPanel.add(newAcct, "NewAccount");
			   bankObj.cards.show(bankObj.controlPanel, "NewAccount");
		   }
		   else if(tp.toString().endsWith("Freeze/UnFreeze Account]"))
		   		{ 
			   		createFreezeAccountPanel(); 
			   		bankObj.controlPanel.add(freezeAcct, "Freeze/Activate");
			   		bankObj.cards.show(bankObj.controlPanel, "Freeze/Activate");
		   		}
		   else if(tp.toString().endsWith("View/Edit Account Details]"))
		   		{ 
		   			createSearchAccountPanel();
		   			bankObj.controlPanel.add(searchAccount, "SearchAccount"); 
		   			bankObj.cards.show(bankObj.controlPanel, "SearchAccount");
		   		}
		   else if(tp.toString().endsWith("View Branch Details]"))
		   		{ createBranchDetailsPanel();
		   		bankObj.controlPanel.add(branchDetails, "BranchDetails"); 
		   		bankObj.cards.show(bankObj.controlPanel, "BranchDetails");
		   		} 
		   }
	   } 
	 }); 
	   
   }

   public void setBankObj(OnlineBank obj) { 
	this.bankObj = obj;
	}

	void createNewAccountPanel(){
	newAcct.setBackground(new Color(96,204,204));
	newAcct.setLayout(new BoxLayout(newAcct,BoxLayout.PAGE_AXIS)); 
	JPanel descPanel = new JPanel();
		
	descPanel.setBackground(new Color(96,204,204));
	desc = new JLabel("Please enter the following information to create a new account:");
	desc.setForeground(Color.red); 
	descPanel.add(desc);
		
	
	JPanel entryFields = new JPanel(new SpringLayout());
	entryFields.setBackground(new Color(96,204,204));
		
	bankObj.controlPanel.setSize(1000, 400);
		     String[] labelStrings = {
		                "First Name : ",
		                "Last Name  : ",
		"Address : " ,
		"SSN : ", "Occupation : ", "EmailID :", "Account Type : " , "Initial Balance :", "User Name :", "Password :"
		};
		     
		JLabel[] labels=new JLabel[labelStrings.length];
		JComponent[] fields = new JComponent[labelStrings.length]; 
		int fieldNum = 0;
		firstName= new JTextField(15);
		firstName.setMaximumSize( firstName.getPreferredSize() ); 
		fields[fieldNum++] = firstName;
		
		lastName= new JTextField(15);
		lastName.setMaximumSize( lastName.getPreferredSize() );
		fields[fieldNum++] = lastName;
		
		address= new JTextArea(5, 5);
		address.setMaximumSize( address.getPreferredSize() ); 
		fields[fieldNum++] = address;
		
		ssn= new JTextField(10);
		ssn.setMaximumSize( ssn.getPreferredSize() );
		fields[fieldNum++] = ssn;
		
		occupation= new JTextField(20);
		occupation.setMaximumSize( occupation.getPreferredSize() );
		fields[fieldNum++] = occupation;
		
		emailID= new JTextField(10);
		emailID.setMaximumSize( emailID.getPreferredSize() );
		fields[fieldNum++] = emailID;
		
		acctType.setMaximumSize(acctType.getPreferredSize());
		acctType.setSelectedIndex(0);
		fields[fieldNum++] = acctType;
		initialBal= new JTextField(20);
		initialBal.setMaximumSize( initialBal.getPreferredSize() ); 
		fields[fieldNum++] = initialBal;
		userName= new JTextField(20);
		userName.setMaximumSize( userName.getPreferredSize() ); 
		fields[fieldNum++] = userName;
		passwd= new JPasswordField(20); passwd.setMaximumSize( passwd.getPreferredSize() );
		fields[fieldNum++] = passwd;
		
		for (int i = 0; i < labelStrings.length; i++) { 
		labels[i] = new JLabel(labelStrings[i],
		JLabel.TRAILING); labels[i].setLabelFor(fields[i]);
		entryFields.add(labels[i]);
		entryFields.add(fields[i]); 
		}
		
		createBtn= new JButton ("Create"); 
		createBtn.addActionListener(this); 
		backBtn= new JButton ("Back"); 
		backBtn.addActionListener(this);
		resetBtn=new JButton ("Reset");
		resetBtn.addActionListener(this);
		
		//JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		
		JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

		
		btnPanel.setBackground(new Color(96,204,204));
		btnPanel.add(createBtn);
		btnPanel.add(resetBtn);
		btnPanel.add(backBtn);
		
		SpringUtilities.makeCompactGrid(entryFields, labelStrings.length, 2,GAP, GAP, GAP, GAP/2);
		
		newAcct.add(descPanel); 
		newAcct.add(entryFields);
		newAcct.add(btnPanel);
		}
	
	
		public void actionPerformed(ActionEvent e){
		
			if(e.getSource()==backBtn )
			{ 
				bankObj.controlPanel.setSize(800, 400); 
				bankObj.cards.show(bankObj.controlPanel, "AdminOptions");
			}
			else if(e.getSource()==backBtnForAcct)
			{ 
				bankObj.controlPanel.setSize(800, 400);
				bankObj.cards.show(bankObj.controlPanel, "SearchAccount");
			}
			else if(e.getSource()==createBtn)
			{
				bankObj.acctObj.setMethodToInvoke("createNewAccount"); 
				bankObj.returnObj=null;
				bankObj.returnObj =bankObj.dbClient.makeServerCall(bankObj.acctObj);
				
				JOptionPane.showMessageDialog(bankObj.controlPanel,bankObj.returnObj.getMessage(), "Message",JOptionPane.INFORMATION_MESSAGE);
				bankObj.cards.show(bankObj.controlPanel, "AdminOptions");
			}
			else if(e.getSource()== resetBtn)
			{ 
				firstName.setText(""); 
				lastName.setText(""); address.setText(""); 
				ssn.setText(""); occupation.setText("");
				emailID.setText(""); initialBal.setText(""); 
				userName.setText(""); passwd.setText("");
				acctType.setSelectedIndex(0);
			}
			else if(e.getSource()== searchBtn)
			{
				bankObj.acctObj = new AccountObject(); 
				bankObj.acctObj.setUserName(userNameForSearch.getText());
				bankObj.acctObj.setMethodToInvoke("getAccountDetails");
				List acctList =bankObj.dbClient.makeServerCallForList(bankObj.acctObj);
				int i=0;
				String[] options =new String[acctList.size()];
				
				if(acctList!=null && acctList.size()>0)
				{ 
					for (Object obj:acctList)
					{
					AccountObject obj1=(AccountObject)obj;
					options[i]=String.valueOf(obj1.getAcctNumber()); 
					i++;
					}
				}
				
				b1=new JRadioButton(options[0]);
				b2=new JRadioButton(options[1]); 
				b1.setBackground(new Color(96,204,204)); 
				b2.setBackground(new Color(96,204,204));
				
				ButtonGroup bg = new ButtonGroup(); 
				bg.add(b1);
				bg.add(b2);
				searchAccount.add(b1); 
				searchAccount.add(b2);
				
				acctDetailsBtn=new JButton("View Details"); 
				acctDetailsBtn.addActionListener(this); 
				searchAccount.add(acctDetailsBtn);
				
				acctEditBtn=new JButton("Edit Details"); 
				acctEditBtn.addActionListener(this); 
				searchAccount.add(acctEditBtn);
				
				backBtn= new JButton ("Back");
				searchAccount.add(backBtn);
				searchBtn.setEnabled(false);
				bankObj.controlPanel.add(searchAccount, "SearchAccount");
				bankObj.cards.show(bankObj.controlPanel, "SearchAccount");
			}
			else if(e.getSource()== acctDetailsBtn)
			{ 
				createViewAccountDetailsPanel();
				bankObj.controlPanel.add(acctDetails, "AccountDetails"); 
				bankObj.cards.show(bankObj.controlPanel, "AccountDetails");
			}
			else if(e.getSource()== acctEditBtn)
			{ 
				createEditAccountPanel(); 
				bankObj.controlPanel.add(editAcct, "AccountEdit");
				bankObj.cards.show(bankObj.controlPanel, "AccountEdit");
			}
			else if(e.getSource()== searchForFreezeBtn)
			{
				bankObj.acctObj = new AccountObject();
				bankObj.acctObj.setAcctNumber(Integer.parseInt(acctNumber.getText()));
				bankObj.acctObj.setMethodToInvoke("getAccountDetailsByID"); 
				bankObj.returnObj=null; bankObj.returnObj=bankObj.dbClient.makeServerCall(bankObj.acctObj);
				freezeAcct=repopulateFreezePanel();
				bankObj.controlPanel.add(freezeAcct, "Freeze/Activate");
				bankObj.cards.show(bankObj.controlPanel, "Freeze/Activate"); 
				}
			else if(e.getSource()== freezeBtn)
			{
				bankObj.acctObj = new AccountObject();
				bankObj.acctObj.setAcctNumber(Integer.parseInt(acctNumber.getText())); 
				bankObj.acctObj.setMethodToInvoke("freezeOrActivateAccount");
				bankObj.returnObj=null;
				bankObj.returnObj=bankObj.dbClient.makeServerCall(bankObj.acctObj);
				
				JOptionPane.showMessageDialog(bankObj.controlPanel,bankObj.returnObj.getMessage(), "Message",JOptionPane.INFORMATION_MESSAGE);
				bankObj.cards.show(bankObj.controlPanel, "AdminOptions");
				}
		else if(e.getSource()== updateBtn)
			{
			bankObj.acctObj = new AccountObject();
			bankObj.acctObj.setUserName(userNameForEdit.getText()); 
			bankObj.acctObj.setEmailID(emailForEdit.getText());
			bankObj.acctObj.setFirstName(firstNameForEdit.getText()); 
			bankObj.acctObj.setLastName(lastNameForEdit.getText());
			bankObj.acctObj.setOccupation(occupationForEdit.getText());
			bankObj.acctObj.setAddress(addressForEdit.getText());
			bankObj.acctObj.setAcctNumber(bankObj.returnObj.getAcctNumber());
			bankObj.acctObj.setMethodToInvoke("updateAccountDetails"); 
			bankObj.returnObj=null;
			bankObj.returnObj=bankObj.dbClient.makeServerCall(bankObj.acctObj);
			
			JOptionPane.showMessageDialog(bankObj.controlPanel,bankObj.returnObj.getMessage(), "Message",JOptionPane.INFORMATION_MESSAGE);
			bankObj.cards.show(bankObj.controlPanel, "AdminOptions");
			}
		else if(e.getSource()== logoutBtn)
		{
			if(bankObj.dbClient!=null)
			{ 
				bankObj.dbClient.closeDBClient();
			}
			JOptionPane.showMessageDialog(bankObj.controlPanel,"ThankYou for using OnlineBanking application","Message",JOptionPane.INFORMATION_MESSAGE);
			System.exit(0);
			} 
			}
		
			private JPanel repopulateFreezePanel(){
			JPanel dataPanel = new JPanel();
			dataPanel.setBackground(new Color(96,204,204)); dataPanel.setBorder(BorderFactory.createTitledBorder("Account Details : "));
			dataPanel.setLayout(new BoxLayout(dataPanel, BoxLayout.Y_AXIS));
			dataPanel.add(new JLabel(" "));
			String[] columnNames = {"Account Number ", "User Name ",
			                  "Balance          ",
			                  "Account Status  "
			};
			
			String[][] data = new String[1][4];
			data[0][0]=String.valueOf(bankObj.returnObj.getAcctNumber());
			data[0][1]=bankObj.returnObj.getFirstName()+" "+ bankObj.returnObj.getLastName();
			data[0][2]=df.format(bankObj.returnObj.getBalance()); if(bankObj.returnObj.isActive()){
			data[0][3]="Active"; }else{
			data[0][3]="InActive"; }
			JTable table = new JTable(data, columnNames); JTableHeader header = table.getTableHeader(); header.setBackground(new Color(178,222,102));
			table.getColumnModel().getColumn(0).setPreferredWidth(100); table.getColumnModel().getColumn(1).setPreferredWidth(100); table.getColumnModel().getColumn(2).setPreferredWidth(100); table.getColumnModel().getColumn(3).setPreferredWidth(100);
			dataPanel.add(table.getTableHeader()); dataPanel.add(table);
			freezeAcct.add(dataPanel); backBtn= new JButton ("Back");

			freezeBtn=new JButton ("Freeze/UnFreeze");
			freezeBtn.addActionListener(this);
			JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); 
			btnPanel.setBackground(new Color(96,204,204)); btnPanel.add(backBtn);
			btnPanel.add(freezeBtn);
			freezeAcct.add(btnPanel); 
			return freezeAcct;
			}
			
			private AccountObject populateAccountObject()
			{
				AccountObject acct = new AccountObject();
				acct.setFirstName(firstName.getText());
				acct.setLastName(lastName.getText()); 
				acct.setAddress(address.getText());
				acct.setSsn(ssn.getText());
				acct.setOccupation(occupation.getText()); 
				acct.setEmailID(emailID.getText()); 
				acct.setAcctType((String)acctType.getSelectedItem()); 
				acct.setBalance(Double.parseDouble(initialBal.getText())); 
				acct.setUserName(userName.getText());
				acct.setPasswd(passwd.getText());
				return acct;
				}
			
			void createSearchAccountPanel(){
				
				searchAccount=new JPanel(); 
				searchAccount.setBackground(new Color(96,204,204));
				bankObj.controlPanel.setSize(1000, 400);
				
				JPanel searchPanel = new JPanel(new GridLayout(5, 5, 2, 2)); 
				searchPanel.setBackground(new Color(96,204,204)); 
				searchPanel.setBorder(BorderFactory.createTitledBorder("Please select the account:"));
				
				JLabel tField = new JLabel("User Name :");
				searchPanel.add(tField);
				userNameForSearch= new JTextField(15);
				userNameForSearch.setMaximumSize( userNameForSearch.getPreferredSize() ); 
				searchPanel.add(userNameForSearch);
				searchBtn = new JButton("Search");
				searchPanel.add(searchBtn); 
				searchBtn.setSize(10, 5);
				searchBtn.addActionListener(this);
				//￼￼￼￼￼￼￼￼￼￼￼searchAccount.add(searchPanel); 
				searchAccount.add(searchPanel); 
			}

			void createFreezeAccountPanel(){
			freezeAcct=new JPanel();
			freezeAcct.setBackground(new Color(96,204,204));
			freezeAcct.setLayout(new BoxLayout(freezeAcct, BoxLayout.PAGE_AXIS)); 
			bankObj.controlPanel.setSize(1000, 400);
			JPanel searchPanel = new JPanel();
			searchPanel.setBackground(new Color(96,204,204)); 
			searchPanel.setBorder(BorderFactory.createTitledBorder("Please select the account:"));
			JLabel tField = new JLabel("Account Number :");
			searchPanel.add(tField);
			
			acctNumber= new JTextField(15);
			acctNumber.setMaximumSize( acctNumber.getPreferredSize() ); 
			searchPanel.add(acctNumber);
			searchForFreezeBtn = new JButton("Search");
			searchPanel.add(searchForFreezeBtn); 
			searchForFreezeBtn.setSize(10, 5); 
			searchForFreezeBtn.addActionListener(this);
			backBtn= new JButton ("Back"); backBtn.addActionListener(this); 
			searchPanel.add(backBtn); backBtn.setSize(10, 5); 
			freezeAcct.add(searchPanel);
			}

			void createViewAccountDetailsPanel(){
				acctDetails = new JPanel();
				acctDetails.setBackground(new Color(96,204,204));
				acctDetails.setLayout(new BoxLayout(acctDetails, BoxLayout.Y_AXIS));
				bankObj.controlPanel.setSize(1000, 400);
				bankObj.acctObj = new AccountObject();
				if(b1.isSelected())
					bankObj.acctObj.setAcctNumber(Integer.parseInt(b1.getText()));
				else if(b2.isSelected())
						bankObj.acctObj.setAcctNumber(Integer.parseInt(b2.getText())); 
				bankObj.acctObj.setMethodToInvoke("getAccountDetailsByID");
				bankObj.returnObj=null;
				bankObj.returnObj =bankObj.dbClient.makeServerCall(bankObj.acctObj);
				
				JPanel displayFields = new JPanel(new SpringLayout());
				displayFields.setBackground(new Color(96,204,204));
				displayFields.add(new JLabel("Account Number :"));
				JLabel l1 = new JLabel(String.valueOf(bankObj.returnObj.getAcctNumber()));
				l1.setFont(new Font("Courier New", Font.ITALIC, 14));
				displayFields.add(l1);
				displayFields.add(new JLabel("Account Type :"));
			
				JLabel l2 = new JLabel(bankObj.returnObj.getAcctType());
				l2.setFont(new Font("Courier New", Font.ITALIC, 14));
				displayFields.add(l2);

				displayFields.add(new JLabel("Account Balance :"));
				JLabel l3 = new JLabel("$"+String.valueOf(bankObj.returnObj.getBalance())); 
				l3.setFont(new Font("Courier New", Font.ITALIC, 14));
				displayFields.add(l3);
				displayFields.add(new JLabel("Last Transaction Date :")); 
				JLabel l4 = new JLabel(bankObj.returnObj.getLast_trans_date()); 
				l4.setFont(new Font("Courier New", Font.ITALIC, 14));
				displayFields.add(l4);
				displayFields.add(new JLabel("Account Status :")); 
				JLabel l5;
				
				if(bankObj.returnObj.isActive())
					l5= new JLabel("Active"); 
				else
						l5= new JLabel("InActive");
				l5.setFont(new Font("Courier New", Font.ITALIC, 14)); 
				displayFields.add(l5);
				
				displayFields.add(new JLabel("User Name :"));
				JLabel l6 = new JLabel(bankObj.returnObj.getUserName()); 
				l6.setFont(new Font("Courier New", Font.ITALIC, 14));
				displayFields.add(l6);
				displayFields.add(new JLabel("Email ID :"));
				
				JLabel l7 = new JLabel(bankObj.returnObj.getEmailID());
				l7.setFont(new Font("Courier New", Font.ITALIC, 14));
				displayFields.add(l7);
				displayFields.add(new JLabel("First Name :"));
				
				JLabel l8 = new JLabel(bankObj.returnObj.getFirstName()); 
				l8.setFont(new Font("Courier New", Font.ITALIC, 14)); 
				displayFields.add(l8);
				displayFields.add(new JLabel("Last Name :"));
				
				JLabel l9 = new JLabel(bankObj.returnObj.getLastName()); 
				l9.setFont(new Font("Courier New", Font.ITALIC, 14));
				displayFields.add(l9);
				displayFields.add(new JLabel("Address :"));
				
				JLabel l10 = new JLabel(bankObj.returnObj.getAddress()); 
				l10.setFont(new Font("Courier New", Font.ITALIC, 14)); 
				displayFields.add(l10);
				
				displayFields.add(new JLabel("Occupation :"));
				JLabel l11 = new JLabel(bankObj.returnObj.getOccupation()); 
				l11.setFont(new Font("Courier New", Font.ITALIC, 14)); 
				displayFields.add(l11);
				SpringUtilities.makeCompactGrid(displayFields, 11, 2,GAP, GAP, GAP, GAP/2);
				backBtnForAcct= new JButton ("Back"); 
				backBtnForAcct.addActionListener(this);
				JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));


				btnPanel.setBackground(new Color(96,204,204));
				btnPanel.add(backBtnForAcct); 
				acctDetails.add(displayFields);
				acctDetails.add(btnPanel);
			}

		void createEditAccountPanel()
			{ 
			editAcct = new JPanel();
			editAcct.setBackground(new Color(96,204,204)); 
			editAcct.setLayout(new BoxLayout(editAcct, BoxLayout.Y_AXIS));
			bankObj.controlPanel.setSize(1000, 400); 
			bankObj.acctObj = new AccountObject();
			
			if(b1.isSelected()) 
				bankObj.acctObj.setAcctNumber(Integer.parseInt(b1.getText()));
			else if(b2.isSelected())
				bankObj.acctObj.setAcctNumber(Integer.parseInt(b2.getText()));

			bankObj.acctObj.setMethodToInvoke("getAccountDetailsByID"); 
			bankObj.returnObj=null;
			bankObj.returnObj =bankObj.dbClient.makeServerCall(bankObj.acctObj);
			
			JPanel displayFields = new JPanel(new SpringLayout()); 
			displayFields.setBackground(new Color(96,204,204));
			displayFields.add(new JLabel("Account Number :"));
			JLabel l1 = new JLabel(String.valueOf(bankObj.returnObj.getAcctNumber())); 
			l1.setFont(new Font("Courier New", Font.ITALIC, 14)); displayFields.add(l1);
			displayFields.add(new JLabel("Account Type :"));
			
			JLabel l2 = new JLabel(bankObj.returnObj.getAcctType());
			l2.setFont(new Font("Courier New", Font.ITALIC, 14)); displayFields.add(l2);
			displayFields.add(new JLabel("Account Status :")); 
			
			JLabel l5;
			if(bankObj.returnObj.isActive())
				l5= new JLabel("Active"); 
			else
					l5= new JLabel("InActive");
			l5.setFont(new Font("Courier New", Font.ITALIC, 14)); 
			displayFields.add(l5);
			displayFields.add(new JLabel("User Name :"));
			
			userNameForEdit=new JTextField(15);
			userNameForEdit.setText(bankObj.returnObj.getUserName()); 
			userNameForEdit.setMaximumSize( userNameForEdit.getPreferredSize() ); 
			displayFields.add(userNameForEdit);
			
			displayFields.add(new JLabel("Email ID :"));
			emailForEdit=new JTextField(15);
			emailForEdit.setText(bankObj.returnObj.getEmailID()); 
			emailForEdit.setMaximumSize( emailForEdit.getPreferredSize() ); 
			displayFields.add(emailForEdit);
			displayFields.add(new JLabel("First Name :"));
			
			firstNameForEdit=new JTextField(15); firstNameForEdit.setText(bankObj.returnObj.getFirstName()); 
			firstNameForEdit.setMaximumSize( firstNameForEdit.getPreferredSize() ); 
			displayFields.add(firstNameForEdit);
			displayFields.add(new JLabel("Last Name :"));
            lastNameForEdit=new JTextField(15); 
            lastNameForEdit.setText(bankObj.returnObj.getLastName()); 
            lastNameForEdit.setMaximumSize( lastNameForEdit.getPreferredSize() ); 
            displayFields.add(lastNameForEdit);
            displayFields.add(new JLabel("Address :"));
            addressForEdit= new JTextArea(5, 5); 
            addressForEdit.setText(bankObj.returnObj.getAddress()); 
            addressForEdit.setMaximumSize( addressForEdit.getPreferredSize() ); 
            displayFields.add(addressForEdit);
            displayFields.add(new JLabel("Occupation :"));
            occupationForEdit= new JTextField(15); 
            occupationForEdit.setText(bankObj.returnObj.getOccupation()); 
            occupationForEdit.setMaximumSize( occupationForEdit.getPreferredSize() ); 
            displayFields.add(occupationForEdit);
            SpringUtilities.makeCompactGrid(displayFields, 9, 2, GAP, GAP, GAP, GAP/2);
            updateBtn=new JButton("Update");  
            updateBtn.addActionListener(this); 
            backBtnForAcct= new JButton ("Back");
            backBtnForAcct.addActionListener(this);
          JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); 
          btnPanel.setBackground(new Color(96,204,204)); 
          btnPanel.add(backBtnForAcct);
           btnPanel.add(updateBtn);
             editAcct.add(displayFields); 
             editAcct.add(btnPanel);
      }
            void createBranchDetailsPanel(){
              branchDetails = new JPanel(); 
              branchDetails.setLayout(new GridLayout(2, 1)); 
              branchDetails.setBackground(new Color(96,204,204)); 
              bankObj.controlPanel.setSize(1000, 600);
              JPanel btnPanel = new JPanel();
              backBtn= new JButton ("Back"); 
              backBtn.addActionListener(this); 
              btnPanel.add(backBtn); 
              btnPanel.setBackground(new Color(96,204,204));
              JTabbedPane tabbedPane = new JTabbedPane(); 
              tabbedPane.setBackground(new Color(96,204,204));



              ImageIcon flIcon = createImageIcon("florida.gif"); 
              ImageIcon nyIcon = createImageIcon("new york.gif");
              ImageIcon njIcon = createImageIcon("new jersey.gif"); 
              ImageIcon caIcon = createImageIcon("california.gif");
              BufferedImage ret1 = new BufferedImage(32,32,BufferedImage.TYPE_INT_RGB); 
              ret1.getGraphics().drawImage(njIcon.getImage(),0,0,32,32,null);
             JComponent panel1 = makeNJPanel();
              panel1.setBackground(new Color(96,204,204)); 
               tabbedPane.addTab("NewJersey", new ImageIcon(ret1), panel1,"");
               BufferedImage ret2 = new BufferedImage(32,32,BufferedImage.TYPE_INT_RGB); 
               ret2.getGraphics().drawImage(nyIcon.getImage(),0,0,32,32,null);
                 JComponent panel2 = makeNYPanel();
               panel2.setBackground(new Color(96,204,204)); 
               tabbedPane.addTab("NewYork", new ImageIcon(ret2), panel2,"");
               BufferedImage ret3 = new BufferedImage(32,32,BufferedImage.TYPE_INT_RGB); 
               ret3.getGraphics().drawImage(flIcon.getImage(),0,0,32,32,null);
               JComponent panel3 = makeFLPanel();
               panel3.setBackground(new Color(96,204,204)); 
               tabbedPane.addTab("Florida", new ImageIcon(ret3), panel3,"");
               BufferedImage ret4 = new BufferedImage(32,32,BufferedImage.TYPE_INT_RGB); 
               ret4.getGraphics().drawImage(caIcon.getImage(),0,0,32,32,null);
               JComponent panel4 = makeCAPanel();
               panel4.setBackground(new Color(96,204,204)); 
               tabbedPane.addTab("California", new ImageIcon(ret4), panel4,"");
//The following line enables to use scrolling tabs.
               tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT); 
               branchDetails.add(tabbedPane);
               branchDetails.add(btnPanel); add(branchDetails);
   }
      protected JComponent makeNJPanel() {
         JPanel panel = new JPanel(false);
         JTextArea text = new JTextArea(" 1. 500 S Frank E Rodgers Blvd \n Harrison NJ07029 \n 973-482-2989 \n Branch Hours: \n Lobby: Mon-Thu 9-4, Fri 9-6, Sat 9-1, Sun Closed \n\n" +
"2. 1 Gateway Center \n Newark NJ 07102 \n 973-648-8001 \n Branch Hours: \n Lobby: Mon-Fri 8:30-4, Sat-Sun Closed ");
         text.setAlignmentX(JTextArea.LEFT_ALIGNMENT);
         text.setBackground(new Color(96,204,204)); 
         panel.setLayout(new GridLayout(1, 1)); 
         panel.add(text);
         return panel;
         }
       protected JComponent makeNYPanel() {
         JPanel panel = new JPanel(false);
         JTextArea text = new JTextArea("1. 261 Broadway \n New York NY 10007 \n"
		+ "212-393-1030 \n Branch Hours: \n Lobby: Mon-Fri 8-6, Sat-Sun Closed \n\n" +
"2. 260 Greenwich Street \n New York NY 10007 \n 212-571-2404 \n Branch Hours: \n Lobby: Mon-Fri 8-6, Sat 10-2, Sun Closed ");

         text.setAlignmentX(JTextArea.LEFT_ALIGNMENT);
         text.setBackground(new Color(96,204,204));
         panel.setLayout(new GridLayout(1, 1));
         panel.add(text);
         return panel;
       }
       
       
       protected JComponent makeFLPanel() {
    	   JPanel panel = new JPanel(false);
    	   JTextArea text = new JTextArea("1. 9385 N. 56th St. \n Temple Terrace FL 33617 \n 813-989-7400 \n Branch Hours: \n Lobby: Mon-Thu 9-4, Fri 9-6, Sat 9-1, Sun Closed \n\n " +
"2. 1720 E. Fowler Avenue \n Tampa FL 33612 \n 813-971-5088 \n Branch Hours: \n Lobby: Mon-Thu 9-4, Fri 9-6, Sat 9-1, Sun Closed ");
    	   text.setAlignmentX(JTextArea.LEFT_ALIGNMENT);
    	   text.setBackground(new Color(96,204,204));
    	   panel.setLayout(new GridLayout(1, 1));
    	   panel.add(text);
    	   return panel;
       }
       
protected JComponent makeCAPanel() {

	JPanel panel = new JPanel(false);
	JTextArea text = new JTextArea("1. 850 N Broadway \n Los Angeles CA 90012 \n 800-432-1000 \n Branch Hours: \n Lobby: Mon-Thu 9-5, Fri 9-6, Sat 9-2, Sun Closed \n\n" +
"2. 100 S Broadway \n Los Angeles CA 90012 \n 800-432-1000 \n Branch Hours: \n Lobby: Mon-Fri 9-5, Sat-Sun Closed ");
	text.setAlignmentX(JTextArea.LEFT_ALIGNMENT);
	panel.setLayout(new GridLayout(1, 1));
	text.setBackground(new Color(96,204,204));
	panel.add(text);
	return panel;
}

	protected static ImageIcon createImageIcon(String path) {
		java.net.URL imgURL = AdminOptions.class.getResource(path);
		if (imgURL != null) {
			return new ImageIcon(imgURL);
		}
		else 
		{
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}
}

