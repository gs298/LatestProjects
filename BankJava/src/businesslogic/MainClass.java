package businesslogic;
public class MainClass {

public static void main(String[] args){

	OnlineBank bank= new OnlineBank();
	UserAccountManagement userOptions = new UserAccountManagement();
	userOptions.setBankObj(bank);
	bank.controlPanel.add(userOptions, "UserOptions");
	AdminOptions admOptions = new AdminOptions();
	admOptions.setBankObj(bank);
	bank.controlPanel.add(admOptions, "AdminOptions");
	
	
}

}