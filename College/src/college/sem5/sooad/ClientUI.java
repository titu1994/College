package college.sem5.sooad;

import javax.swing.JFrame;

import college.sem5.sooad.NetBankClient.ClientListener;

public class ClientUI extends JFrame {
	private static final long serialVersionUID = 12321L;
	
	private static LogIn login;
	private static TransactionUI trans;
	
	private static long id;
	private static String password;

	private static NetBankServer server;
	public static NetBankClient client;
	private static ClientEventListener listener;

	public static void main(String[] args) {
		listener = new ClientEventListener();
		
		server = new NetBankServer();
		server.startCommunication();
	
		login = new LogIn();
		login.setVisible(true);
	}
	
	public static void startClient(long id, String password) {
		ClientUI.id = id; 
		ClientUI.password = password;
		
		client = new NetBankClient(id, password, listener);
		client.startCommunication();
	}
	
	public static void TransactionScreen() {
		login.setVisible(false);
		trans = new TransactionUI();
		trans.setVisible(true);
	}

	public static class ClientEventListener implements ClientListener {

		@Override
		public void clientLogInFailed() {
			login.logInFailed();
		}

		@Override
		public void clientLogInSuccess(double limit, double consumed) {
			TransactionScreen();
		}

		@Override
		public void clientTransactionAdded(boolean succesful) {
			if(succesful) {
				System.out.println("Wait");
			}
			else {
				System.out.println("Trasaction failed to add");
			}
		}

		@Override
		public void clientAllTransactionData(NetBankTransactionData[] datas) {
			trans.clientAllTransactionData(datas);
		}

		@Override
		public void clientOldPasswordMatchesNewPassword() {
			
		}

		@Override
		public void clientOldPasswordNotEnteredCorrectly() {
			
		}

		@Override
		public void clientNewPasswordEmpty() {
			
		}

		@Override
		public void clientOldPasswordNotFound() {
			
		}
		
	}
}
