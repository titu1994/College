package college.sem5.sooad;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import college.sem5.sooad.NetBankServer.NetBankServerProtocols;

public class NetBankClient {
	private static ExecutorService executor;
	private Socket client = null;

	private String password;
	private long accountID;

	private double creditLimit, creditConsumed; 

	public NetBankClient(long accountID, String password) {
		if(!isExecutorAvailable())
			executor = Executors.newCachedThreadPool();

		this.password = password;
		this.accountID = accountID;

		System.out.println("Client : Starting");
		try {
			client = new Socket(InetAddress.getLocalHost(), NetBankServerProtocols.PORT);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Client : Started");
	}

	public void startCommunication() {
		Runnable r = new Runnable() {

			@Override
			public void run() {
				initiateCommunication();
			}
		};

		if(isExecutorAvailable()) {
			executor.submit(r);
		}
		else {
			restartExecutor();
			executor.submit(r);
		}
	}

	private NetBankAccountData initiateCommunication() {
		synchronized (client) {
			try {
				PrintWriter pr = new PrintWriter(new OutputStreamWriter(client.getOutputStream()), true);
				BufferedReader bb = new BufferedReader(new InputStreamReader(client.getInputStream()));

				String protocol;
				StringBuilder sb = new StringBuilder();

				if((protocol = bb.readLine()).equals(NetBankServerProtocols.serverReadyToReceive)) {
					sendCredentialsToUser(client, pr);
					pr.println(NetBankServerProtocols.clientReadyToRecieve);
				}
				else {
					pr.println(NetBankServerProtocols.clientFinishCommunication);
					return null;
				}

				if((protocol = bb.readLine()).equals(NetBankServerProtocols.serverReadyToSend)) {
					while(!(protocol = bb.readLine()).equals(NetBankServerProtocols.serverReadyToReceive)) {
						sb.append(protocol);
					}
					getSecureCredentials(client, sb.toString());
				}

				handleUserChoice(client, pr);


			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {

				try {
					if(client != null)
						client.close();

					System.out.println("Client : Closed");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
		return null;
	}

	private void handleUserChoice(Socket client, PrintWriter pr) {
		synchronized (client) {
			do {
				System.out.println("User ID : " + accountID + "\nCredit Limit : " + creditLimit + " Credit Consumed : " + creditConsumed);
				System.out.println("Please enter operation you wish to perform : \n");
				System.out.println("1:Add Transaction 2:View all transactions 3:Alter Credentials 4:Quit");
				BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
				int choice = -1;
				try {
					choice = Integer.parseInt(br.readLine());
				} catch (NumberFormatException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				switch(choice) {

				case 1: {
					pr.println(NetBankClientProtocols.clientAddTransaction);
					System.out.println("Enter transaction id, to which name, from name and the amount");
					try {
						long id = Long.parseLong(br.readLine());
						String to = br.readLine();
						String from = br.readLine();
						double amt = Double.parseDouble(br.readLine());

						StringBuilder sb = new StringBuilder();
						sb.append(id + "\n");
						sb.append(to + "\n");
						sb.append(from + "\n");
						sb.append(amt + "\n");
						pr.println(sb.toString());

					} catch (NumberFormatException | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					break;
				}
				case 2: {

					break;
				}
				case 3: {

					break;
				}
				default: {
					System.out.println("User : "+accountID + ", cancel all clients?");
					try {
						String allTransactionsFinished = br.readLine();
						if(allTransactionsFinished.contains("Y"))
							pr.println(NetBankServerProtocols.allClientsServed);
						else 
							pr.println(NetBankServerProtocols.clientFinishCommunication);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					return;
				}
				}

			} while(true);
		}
	}

	private void getSecureCredentials(Socket client, String data) {
		synchronized (client) {
			String arr[] = data.split("\n");
			password = arr[0];
			creditLimit = Double.parseDouble(arr[1]);
			creditConsumed = Double.parseDouble(arr[2]);
		}
	}

	private void sendCredentialsToUser(Socket client, PrintWriter pr) {
		synchronized (client) {
			pr.println(accountID);
			pr.println(password);
		}
	}

	public interface NetBankClientProtocols {
		String clientAlterCredentials = "clientAlterCredentials";
		String clientAddTransaction = "clientAddTransaction";
		String clientViewAllTransactions = "clientViewAllTransactions";
	}

	public static boolean isExecutorAvailable() {
		return executor != null && !executor.isShutdown();
	}

	public boolean restartExecutor() {
		if(!isExecutorAvailable()) {
			executor = Executors.newCachedThreadPool();
			return true;
		}
		else
			return false;
	}

	public boolean stopExecutor() {
		if(isExecutorAvailable()) {
			executor.shutdown();
			executor = null;
			return true;
		}
		else {
			return false;
		}
	}

}
