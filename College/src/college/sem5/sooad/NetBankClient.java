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
	private String protocol;

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

				StringBuilder sb = new StringBuilder();

				//Send User credentials to Server to authenticate
				if((protocol = bb.readLine()).equals(NetBankServerProtocols.serverReadyToReceive)) {
					sendCredentialsToUser(client, pr);
				}

				//Recieve full data from server 
				if((protocol = bb.readLine()).equals(NetBankServerProtocols.serverReadyToSend)) {
					while(!(protocol = bb.readLine()).equals(NetBankServerProtocols.serverReadyToReceive)) {
						sb.append(protocol);
					}
					getSecureCredentials(client, sb.toString());
				}

				handleUserChoice(client, pr, bb);


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

	private void sendCredentialsToUser(Socket client, PrintWriter pr) {
		synchronized (client) {
			pr.println(accountID);
			pr.println(password);
			pr.println(NetBankServerProtocols.clientReadyToRecieve);
		}
	}

	private void getSecureCredentials(Socket client, String data) {
		synchronized (client) {
			String arr[] = data.split("[\r\n]+");
			if(NetBankServerProtocols.serverError.equals(arr[0])) {
				if(NetBankServerProtocols.errorIdDoesNotExist.equals(arr[1])) {
					//TODO: Handle client if Account does not exist
					//For now immediately create a new account:
					
				}
			}
			else {
				creditLimit = Double.parseDouble(arr[0]);
				creditConsumed = Double.parseDouble(arr[1]);
			}

		}
	}


	private void handleUserChoice(Socket client, PrintWriter pr, BufferedReader bb) {
		synchronized (client) {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			do {
				System.out.println("User ID : " + accountID + "\nCredit Limit : " + creditLimit + " Credit Consumed : " + creditConsumed);
				System.out.println("Please enter operation you wish to perform : \n");
				System.out.println("1:Add Transaction 2:View all transactions 3:Alter Credentials 4:Quit.");
				int choice = -1;
				try {
					choice = Integer.parseInt(br.readLine());
				} catch (NumberFormatException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				switch(choice) {

				case 1: {
					System.out.println("Enter transaction id, to which name, from name and the amount.");
					try {
						long id = Long.parseLong(br.readLine());
						String to = br.readLine();
						double amt = Double.parseDouble(br.readLine());

						StringBuilder sb = new StringBuilder();
						pr.println(NetBankClientProtocols.clientAddTransaction);
						sb.append(id + "\n");
						sb.append(accountID + "\n");
						sb.append(to + "\n");
						sb.append(amt + "\n");
						pr.println(sb.toString());
						pr.println(NetBankServerProtocols.clientReadyToRecieve);


					} catch (NumberFormatException | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					break;
				}
				case 2: {
					pr.println(NetBankClientProtocols.clientViewAllTransactions);
					pr.println(accountID);
					
					StringBuilder sb = new StringBuilder();
					String protocol = "";
					
					try {
						while(!(protocol = bb.readLine()).equals(NetBankServerProtocols.serverReadyToReceive)) {
							sb.append(protocol);
						}
						
						String data = sb.substring(0, sb.length()-1);
						String[] rawDatas = data.split(",");
						
						NetBankTransactionData datas[] = new NetBankTransactionData[rawDatas.length];
						String temp[] = null;
						for(int i = 0; i < rawDatas.length; i++) {
							temp = rawDatas[i].split("[\r\n]+");
							datas[i] = new NetBankTransactionData(temp[0], temp[1], temp[2], temp[3]);
						}
						
						//TODO: Display datas[] to the user

						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				}
				case 3: {
					System.out.println("Enter previous password and new password.");
					String oldPass = "", newPass = "";
					try {
						oldPass = br.readLine();
						newPass = br.readLine();

						if(oldPass != null && oldPass.length() != 0) {
							pr.println(oldPass);
						}
						else if(oldPass.equals(newPass)){
							System.out.println("New password matches old one");
						}
						else {
							System.out.println("Enter old password correctly.");
						}


						if((protocol = bb.readLine()).equals(NetBankServerProtocols.serverReadyToReceive)) {
							if(newPass != null && newPass.length() != 0)
								pr.println(newPass);
							else {
								System.out.println("New password cannot be empty.");
								pr.println(oldPass);
							}
						}
						else {
							System.out.println("Old password did not match or was not found.");
						}

					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

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
