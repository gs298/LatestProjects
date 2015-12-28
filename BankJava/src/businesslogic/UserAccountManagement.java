package businesslogic;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpringLayout;
import javax.swing.UIManager;
import javax.swing.table.JTableHeader;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

public class UserAccountManagement extends JPanel implements ActionListener{
protected OnlineBank bankObj;
JPanel summary,transfer,withdraw,deposit,history;
final JScrollPane jsp ;
JButton home,back,transferBtn,withrawBtn,depositBtn,logoutBtn; 
Object[][] data=new Object[2][5];
String[][] checkingData;
String[][] savingsData;
private static DecimalFormat df = new DecimalFormat("#0.00"); String[] beneficiaryList;
JTextField amount;
JComboBox accountList,beneficiary;
final static int GAP = 5;

UserAccountManagement(){
	
	logoutBtn= new JButton("LogOut"); logoutBtn.setAlignmentX(Component.RIGHT_ALIGNMENT); logoutBtn.addActionListener(this);
	add(logoutBtn);
	add(new JLabel(" "));
	setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); setBackground(new Color(96,204,204));
	JPanel treePanel= new JPanel();
	final DefaultMutableTreeNode top = new DefaultMutableTreeNode("Account Options");
	
	final DefaultMutableTreeNode UG = new DefaultMutableTreeNode("Account Summary");
	
	top.add(UG);
	final DefaultMutableTreeNode a1 = new DefaultMutableTreeNode("Deposit");
	top.add(a1);
    final DefaultMutableTreeNode a2 = new DefaultMutableTreeNode("Withraw");
	top.add(a2);
	final DefaultMutableTreeNode a3 = new DefaultMutableTreeNode("Transfer Funds");
	top.add(a3);
	final DefaultMutableTreeNode a4 = new DefaultMutableTreeNode("Transaction Summary");
	top.add(a4);
	
    UIManager.put("Tree.rendererFillBackground", false);
	final JTree tree = new JTree(top);
	
	int v = ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;
	int h = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
	
	jsp = new JScrollPane(tree,v,h);
	jsp.setPreferredSize(new Dimension(400,250));
	tree.setBackground(new Color(96,204,204)); jsp.setBorder(BorderFactory.createMatteBorder(1,1,2,2,Color.darkGray));
	treePanel.add(jsp);
	treePanel.setBackground(new Color(96,204,204)); add(treePanel);
	tree.addMouseListener( new MouseAdapter() {
	public void mouseClicked( MouseEvent me) {
	TreePath tp = tree.getPathForLocation(me.getX(),me.getY() ); if( tp != null ){
	if(tp.toString().endsWith("Account Summary]")){ bankObj.acctObj = new AccountObject(); bankObj.acctObj.setUserName(bankObj.username.getText()); bankObj.acctObj.setMethodToInvoke("getAccountDetails");
	List acctList =bankObj.dbClient.makeServerCallForList(bankObj.acctObj);
	if(acctList!=null && acctList.size()>0){ populateAccountSummary(acctList);
	}
	createSummaryPanel();
	bankObj.controlPanel.add(summary, "AccountSummary"); summary.setLayout(new BoxLayout(summary, BoxLayout.Y_AXIS)); bankObj.cards.show(bankObj.controlPanel, "AccountSummary");
	}else if(tp.toString().endsWith("Deposit]")){
	createDepositPanel();
	bankObj.controlPanel.add(deposit, "DepositFunds"); bankObj.cards.show(bankObj.controlPanel, "DepositFunds");
	}else if(tp.toString().endsWith("Withraw]")){
	createWithdrawPanel();
	bankObj.controlPanel.add(withdraw, "WithDrawFunds"); bankObj.cards.show(bankObj.controlPanel, "WithDrawFunds");
	}else if(tp.toString().endsWith("Transfer Funds]")){
	createTransferPanel();
	bankObj.controlPanel.add(transfer, "TransferFunds"); bankObj.cards.show(bankObj.controlPanel, "TransferFunds");
	}else if(tp.toString().endsWith("Transaction Summary]")){
	createTransactionHistoryPanel(); bankObj.controlPanel.add(history, "TransactionHistory"); bankObj.cards.show(bankObj.controlPanel, "TransactionHistory");
	}
	}
	}
	}
);
}
public void actionPerformed(ActionEvent e){
	if(e.getSource()==home || e.getSource()==back ){
		bankObj.controlPanel.setSize(800, 400); bankObj.cards.show(bankObj.controlPanel, "UserOptions");
		}else if(e.getSource()==transferBtn){
		bankObj.acctObj = new AccountObject(); 
		bankObj.acctObj.setFromAcct((String)accountList.getSelectedItem()); 
		bankObj.acctObj.setToAcct((String)beneficiary.getSelectedItem()); 
		bankObj.acctObj.setBalance(Double.parseDouble(amount.getText()));
		bankObj.acctObj.setUserName(bankObj.username.getText()); 
		bankObj.acctObj.setMethodToInvoke("transferMoney"); bankObj.returnObj=null;
		bankObj.returnObj =bankObj.dbClient.makeServerCall(bankObj.acctObj);
		JOptionPane.showMessageDialog(bankObj.controlPanel,bankObj.returnObj.getMessage(),"Message",JOptionPane.INFORMATION_MESSAGE); 
		bankObj.cards.show(bankObj.controlPanel, "UserOptions");
		}else if(e.getSource()== withrawBtn){
			bankObj.acctObj = new AccountObject(); 
			bankObj.acctObj.setFromAcct((String)accountList.getSelectedItem());
			bankObj.acctObj.setBalance(Double.parseDouble(amount.getText()));
			bankObj.acctObj.setUserName(bankObj.username.getText());
			bankObj.acctObj.setMethodToInvoke("withdrawMoney"); bankObj.returnObj=null;
			bankObj.returnObj =bankObj.dbClient.makeServerCall(bankObj.acctObj);
			
		JOptionPane.showMessageDialog(bankObj.controlPanel, bankObj.returnObj.getMessage(),"Message",JOptionPane.INFORMATION_MESSAGE);
			bankObj.cards.show(bankObj.controlPanel, "UserOptions");

		}else if(e.getSource()== depositBtn){
			bankObj.acctObj = new AccountObject(); 
			bankObj.acctObj.setToAcct((String)accountList.getSelectedItem());
			bankObj.acctObj.setBalance(Double.parseDouble(amount.getText()));
			bankObj.acctObj.setUserName(bankObj.username.getText());
			bankObj.acctObj.setMethodToInvoke("depositMoney"); 
			bankObj.returnObj=null;
			bankObj.returnObj =bankObj.dbClient.makeServerCall(bankObj.acctObj);
			JOptionPane.showMessageDialog(bankObj.controlPanel, bankObj.returnObj.getMessage(),"Message",JOptionPane.INFORMATION_MESSAGE); 
			bankObj.cards.show(bankObj.controlPanel, "UserOptions");
			}else if(e.getSource()== logoutBtn){
				if(bankObj.dbClient!=null){
					bankObj.dbClient.closeDBClient();
				}
				
				JOptionPane.showMessageDialog(bankObj.controlPanel,"ThankYou for using OnlineBanking application","Message", JOptionPane.INFORMATION_MESSAGE);
				
					System.exit(0);
			}
				}
			

public void setBankObj(OnlineBank obj) { 
	this.bankObj = obj;
}

void createSummaryPanel(){
summary = new JPanel(); 
summary.setBackground(new Color(96,204,204)); 
bankObj.controlPanel.setSize(800, 200);

home = new JButton ("Back");
home.setAlignmentX(Component.CENTER_ALIGNMENT); 
home.setMinimumSize(new Dimension(50, 25)); 
home.setPreferredSize(new Dimension(50, 25));
home.addActionListener(this);

String[] columnNames = {"AccountType","Account Number","Current Balance ","Last Transaction Date","Account Status"};

JTable table = new JTable(data, columnNames); 
JTableHeader header = table.getTableHeader();
header.setBackground(new Color(178,222,102));

table.getColumnModel().getColumn(0).setPreferredWidth(100); 
table.getColumnModel().getColumn(1).setPreferredWidth(100); 
table.getColumnModel().getColumn(2).setPreferredWidth(100);
table.getColumnModel().getColumn(3).setPreferredWidth(100);

JScrollPane scrollPane = new JScrollPane(table);

scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
scrollPane.getViewport().setBackground(Color.WHITE); 
scrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
summary.add(scrollPane);
JLabel empty= new JLabel(" "); summary.add(empty);
summary.add(home);
}

void createTransferPanel(){
	transfer= new JPanel();
	transfer.setBackground(new Color(96,204,204)); transfer.setLayout(new BoxLayout(transfer,BoxLayout.PAGE_AXIS));
	JPanel entryFields = new JPanel(new SpringLayout()); entryFields.setBackground(new Color(96,204,204));
	bankObj.controlPanel.setSize(1000, 400);
	     String[] labelStrings = {
	                "From Account Num : ",
	                "Transfer to AccountNum : ",
	"Amount : $ "
	};
	JLabel[] labels = new JLabel[labelStrings.length]; JComponent[] fields = new JComponent[labelStrings.length]; int fieldNum = 0;
	bankObj.acctObj = new AccountObject(); bankObj.acctObj.setUserName(bankObj.username.getText()); bankObj.acctObj.setMethodToInvoke("getAccountList"); bankObj.returnObj=null;
	bankObj.returnObj =bankObj.dbClient.makeServerCall(bankObj.acctObj);
	String[] accountNum =bankObj.returnObj.getAcctList(); accountList = new JComboBox(accountNum); accountList.setMaximumSize( accountList.getPreferredSize() ); accountList.setSelectedIndex(0);
	fields[fieldNum++] = accountList;
	bankObj.acctObj = new AccountObject(); bankObj.acctObj.setUserName(bankObj.username.getText()); bankObj.acctObj.setMethodToInvoke("getBeneficiariesList"); bankObj.returnObj=null;
	bankObj.returnObj =bankObj.dbClient.makeServerCall(bankObj.acctObj);
	String[] beneficiaryList = bankObj.returnObj.getBeneficiaryList();
	if(beneficiaryList!=null && beneficiaryList.length>0){ beneficiary = new JComboBox(beneficiaryList);
	beneficiary.setMaximumSize( beneficiary.getPreferredSize() ); beneficiary.setSelectedIndex(0);
	fields[fieldNum++] = beneficiary;
	}
	amount= new JTextField(15);
	amount.setMaximumSize( amount.getPreferredSize() ); fields[fieldNum++] = amount;
	

for (int i = 0; i < labelStrings.length; i++) {
	labels[i] = new JLabel(labelStrings[i],JLabel.TRAILING); 
	labels[i].setLabelFor(fields[i]);
	entryFields.add(labels[i]);
	entryFields.add(fields[i]);
}
transferBtn= new JButton ("Transfer");
transferBtn.addActionListener(this);
back= new JButton ("Back"); back.addActionListener(this);
JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
panel.add(transferBtn);
panel.add(back);
panel.setBackground(new Color(96,204,204));
	
SpringUtilities.makeCompactGrid (entryFields, labelStrings.length, 2, GAP, GAP,GAP,GAP/2);

transfer.add(entryFields);
transfer.add(panel);
}

void createWithdrawPanel(){
	
	withdraw= new JPanel();
	withdraw.setBackground(new Color(96,204,204)); 
	withdraw.setLayout(new BoxLayout(withdraw,BoxLayout.PAGE_AXIS));
	
	JPanel entryFields = new JPanel(new SpringLayout()); 
	entryFields.setBackground(new Color(96,204,204));
	
	bankObj.controlPanel.setSize(1000, 400);
	
	String[] labelStrings = { "From Account Num : ","Amount : $ "};
	
	JLabel[] labels = new JLabel[labelStrings.length];
	JComponent[] fields = new JComponent[labelStrings.length]; 
	int fieldNum = 0;
	
	bankObj.acctObj = new AccountObject(); 
	bankObj.acctObj.setUserName(bankObj.username.getText()); 
	bankObj.acctObj.setMethodToInvoke("getAccountList");
	bankObj.returnObj=null;
	bankObj.returnObj =bankObj.dbClient.makeServerCall(bankObj.acctObj);

	String[] accountNum = bankObj.returnObj.getAcctList();
	accountList = new JComboBox(accountNum);
	
	accountList.setMaximumSize( accountList.getPreferredSize() ); accountList.setSelectedIndex(0);
	fields[fieldNum++] = accountList;
	amount= new JTextField(15);
	amount.setMaximumSize( amount.getPreferredSize() ); fields[fieldNum++] = amount;
	for (int i = 0; i < labelStrings.length; i++) { labels[i] = new JLabel(labelStrings[i],
	JLabel.TRAILING); labels[i].setLabelFor(fields[i]);
	entryFields.add(labels[i]);
	entryFields.add(fields[i]); }
	withrawBtn= new JButton ("Withdraw"); withrawBtn.addActionListener(this);
	back= new JButton ("Back"); back.addActionListener(this);
	JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER)); panel.add(withrawBtn);
	panel.add(back);
	panel.setBackground(new Color(96,204,204));
	SpringUtilities.makeCompactGrid(entryFields,labelStrings.length, 2,GAP, GAP,GAP, GAP/2);
	withdraw.add(entryFields); 
	withdraw.add(panel);
 }

void createDepositPanel(){
	deposit= new JPanel(); deposit.setBackground(new Color(96,204,204)); 
	deposit.setLayout(new BoxLayout(deposit,BoxLayout.PAGE_AXIS));
	
	JPanel entryFields = new JPanel(new SpringLayout());
	entryFields.setBackground(new Color(96,204,204));
	
	bankObj.controlPanel.setSize(1000, 400);

	String[] labelStrings = {"Account Number : ","Amount : $ "};
	
	JLabel[] labels = new JLabel[labelStrings.length]; 
	JComponent[] fields = new JComponent[labelStrings.length]; 
	int fieldNum = 0;
	
	bankObj.acctObj = new AccountObject();
	
	
	bankObj.acctObj.setUserName(bankObj.username.getText()); 
	bankObj.acctObj.setMethodToInvoke("getAccountList");
	bankObj.returnObj=null;
	bankObj.returnObj =bankObj.dbClient.makeServerCall(bankObj.acctObj);
	
	String[] accountNum = bankObj.returnObj.getAcctList(); 
	accountList = new JComboBox(accountNum);
	accountList.setMaximumSize( accountList.getPreferredSize() ); 
	accountList.setSelectedIndex(0);
	fields[fieldNum++] = accountList;
	amount= new JTextField(15);
	amount.setMaximumSize( amount.getPreferredSize() ); 
	fields[fieldNum++] = amount;
	for (int i = 0; i < labelStrings.length; i++) { 
		labels[i] = new JLabel(labelStrings[i],
				JLabel.TRAILING); labels[i].setLabelFor(fields[i]);
	entryFields.add(labels[i]);
	entryFields.add(fields[i]); }
	
	depositBtn= new JButton ("Deposit"); 
	depositBtn.addActionListener(this);
	back= new JButton ("Back");
	back.addActionListener(this);
	JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
	panel.add(depositBtn);
	panel.add(back);
	panel.setBackground(new Color(96,204,204));
	SpringUtilities.makeCompactGrid(entryFields, labelStrings.length, 2,GAP, GAP, GAP, GAP/2);
	deposit.add(entryFields); deposit.add(panel);
	}
	void populateAccountSummary(List<AccountObject> acctList){
		int i=0;
		for (AccountObject obj:acctList){
			data[i][0]=obj.getAcctType(); 
			data[i][1]=obj.getAcctNumber();
			data[i][2]="$"+df.format(obj.getBalance()); 
			data[i][3]=obj.getLast_trans_date(); 
			if(obj.isActive()){
				data[i][4]="Active"; 
				}else{
					data[i][4]="InActive"; 
					i++;
					}
		}
		}
		
		void createTransactionHistoryPanel(){
			history = new JPanel();
			history.setBackground(new Color(96,204,204));
			history.setAlignmentX(Component.CENTER_ALIGNMENT); 
			bankObj.controlPanel.setSize(1000, 200); 
			history.setLayout(new BoxLayout(history, BoxLayout.Y_AXIS));
			home = new JButton ("Back"); 
			home.setAlignmentX(Component.CENTER_ALIGNMENT);
			home.setMinimumSize(new Dimension(50, 25)); 
			home.setPreferredSize(new Dimension(50, 25));
			home.addActionListener(this);
			
			String[] columnNames = {"Account Number ", "Transaction Amount  ","Transaction Date	","Transaction Type "};
			
			bankObj.acctObj = new AccountObject();
			bankObj.acctObj.setUserName(bankObj.username.getText()); 
			bankObj.acctObj.setMethodToInvoke("getTransactionHistory"); 
			bankObj.returnObj=null;
			bankObj.returnObj =bankObj.dbClient.makeServerCall(bankObj.acctObj);
			
			populateTransactionData(bankObj.returnObj.getCheckingHist(),bankObj.returnObj.getSavingsHist());
			
			if(bankObj.returnObj.getCheckingHist()!=null && bankObj.returnObj.getCheckingHist().size()>0){
				JPanel checkingPanel = new JPanel(); checkingPanel.setBackground(new Color(96,204,204)); 
				checkingPanel.setBorder(BorderFactory.createTitledBorder("CheckingAccount Details : ")); //BorderFactory.createLineBorder(Color.red) 
				checkingPanel.setLayout(new BoxLayout(checkingPanel, BoxLayout.Y_AXIS));

				checkingPanel.add(new JLabel(" "));
				
				JTable table = new JTable(checkingData, columnNames); 
				JTableHeader header = table.getTableHeader();
				header.setBackground(new Color(178,222,102));
				
				table.getColumnModel().getColumn(0).setPreferredWidth(100); 
				table.getColumnModel().getColumn(1).setPreferredWidth(100); 
				table.getColumnModel().getColumn(2).setPreferredWidth(100);
				table.getColumnModel().getColumn(3).setPreferredWidth(100);
				
				checkingPanel.add(table.getTableHeader()); 
				checkingPanel.add(table);
				
				history.add(checkingPanel);
			}
			if(bankObj.returnObj.getSavingsHist()!=null &&
					bankObj.returnObj.getSavingsHist().size()>0){
				JPanel savingsPanel = new JPanel();
				savingsPanel.setBackground(new Color(96,204,204)); 
				savingsPanel.setBorder(BorderFactory.createTitledBorder("Savings Account Details: "));

				savingsPanel.setLayout(new BoxLayout(savingsPanel, BoxLayout.Y_AXIS));
				savingsPanel.add(new JLabel(" "));
				
				JTable table = new JTable(savingsData, columnNames);
				JTableHeader header = table.getTableHeader();
				header.setBackground(new Color(178,222,102));
				
				table.getColumnModel().getColumn(0).setPreferredWidth(100);
				table.getColumnModel().getColumn(1).setPreferredWidth(100); 
				table.getColumnModel().getColumn(2).setPreferredWidth(100); 
				table.getColumnModel().getColumn(3).setPreferredWidth(100);
				
				savingsPanel.add(table.getTableHeader()); 
				savingsPanel.add(table);
				history.add(savingsPanel);
			}
		     history.add(home); 
			}
		
		
		void populateTransactionData(List<TransactionHistory> checkingHist,List<TransactionHistory> savingsHist){
			int i=0;
			if(checkingHist!=null && checkingHist.size()>0){
			checkingData = new String[checkingHist.size()][4];
			for (TransactionHistory obj:checkingHist){
				checkingData[i][0]=obj.getAcctNum();
				checkingData[i][1]="$"+df.format(obj.getAmount()); 
				checkingData[i][2]=obj.getTransactionDate().toString();
				checkingData[i][3]=obj.getTransactionType();
				i++;
			}
			}
			i=0;
			if(savingsHist!=null && savingsHist.size()>0){
			savingsData = new String[savingsHist.size()][4]; for (TransactionHistory obj:savingsHist){
			savingsData[i][0]=obj.getAcctNum(); 
			savingsData[i][1]="$"+df.format(obj.getAmount()); 
			savingsData[i][2]=obj.getTransactionDate().toString(); 
			savingsData[i][3]=obj.getTransactionType();
			i++;
			}
			}
		}
	}


			
	