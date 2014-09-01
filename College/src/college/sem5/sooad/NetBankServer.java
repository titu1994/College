package college.sem5.sooad;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import college.sem5.sooad.NetBankAccountData.DataBase;
import college.sem5.sooad.NetBankClient.NetBankClientProtocols;

public class NetBankServer {
	private static ExecutorService executor;
	private ServerSocket server = null;
	private String protocol;
	
	private long id;

	public NetBankServer() {
		if(!isExecutorAvailable())
			executor = Executors.newCachedThreadPool();

		System.out.println("Server : Starting");
		try {
			server = new ServerSocket(NetBankServerProtocols.PORT, 50, InetAddress.getLocalHost());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Server : Started");

	}

	public void startCommunication() {
		Runnable r = new Runnable() {

			@Override
			public void run() {
				serveClient();
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

	private void serveClient() {

		Socket client;
		boolean clientWaiting = true;

		try {

			while(clientWaiting) {
				System.out.println("Server : Accepting conections");
				client = server.accept();

				synchronized (client) {
					System.out.println("Server : Accepted connection");
					PrintWriter pr = new PrintWriter(new OutputStreamWriter(client.getOutputStream()), true);
					BufferedReader bb = new BufferedReader(new InputStreamReader(client.getInputStream()));

					StringBuilder sb = new StringBuilder();

					System.out.println("Server : Ready to recieve data");
					pr.println(NetBankServerProtocols.serverReadyToReceive);

					System.out.println("Server : Recieving until client is ready to recieve");
					while(!(protocol = bb.readLine()).equals(NetBankServerProtocols.clientReadyToRecieve)) {
						sb.append(protocol);
					}

					//TODO: Parse sb here
					System.out.println("Server : Parsing details");
					NetBankAccountData data = parseClientUserPass(client, sb.toString());

					System.out.println("Server : Ready to send information");
					pr.println(NetBankServerProtocols.serverReadyToSend);

					//TODO: Send data here
					sendClientAllData(client, pr, data);

					System.out.println("Server : Ready to recieve additional requests");
					pr.println(NetBankServerProtocols.serverReadyToReceive);

					handleUserChoice(client, pr, bb);


					if((protocol = bb.readLine()).equals(NetBankServerProtocols.allClientsServed)) {
						System.out.println("Server : Stopping");
						clientWaiting = false;
						break;
					} else if (protocol.equals(NetBankServerProtocols.clientFinishCommunication)) {
						System.out.println("Server : Acknowledge end of communication");
						pr.println(NetBankServerProtocols.serverFinishCommunication);
					}

					pr.close();
					bb.close();
					client.close();
				}
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void sendClientAllData(Socket client, PrintWriter pr, NetBankAccountData data) {
		synchronized (client) {
			pr.println(data.getSecurePassword());
			pr.println(data.getCreditMaxLimit());
			pr.println(data.getCreditConsumed());
		}
	}

	private NetBankAccountData parseClientUserPass(Socket client, String input) {
		synchronized (client) {
			String arr[] = input.split("\n");
			long id = Long.parseLong(arr[0]);
			String password = arr[1];
			String generatedPassword = NetBankUtils.getSecurePassword(password);
			NetBankAccountData data = null; 
			try {
				data = DataBase.getDataStore().get(id);
				if(data.getSecurePassword().equals(generatedPassword)) {
					return data;
				}
				else {
					return null;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
	}

	private void handleUserChoice(Socket client, PrintWriter pr, BufferedReader bb) throws IOException {
		synchronized (client) {
			String data = null;
			String segs[] = null;
			do {
				int choice = decodeProtocolAction(bb.readLine());

				switch(choice) {
				case 1: {
					data = bb.readLine();
					segs = data.split("\n");
					NetBankTransactionData transaction = new NetBankTransactionData(Long.parseLong(segs[0]), 
							segs[1], segs[2], Double.parseDouble(segs[3]));
					NetBankTransactionData.Database.insertData(transaction);

					if((protocol = bb.readLine()).equals(NetBankServerProtocols.clientReadyToRecieve))
						System.out.println("Transaction " + Long.parseLong(segs[0]) + " succesfull saved.");
					break;
				}
				case 2: {
					HashMap<Long, NetBankTransactionData> transactions = NetBankTransactionData.Database.getDataStore();

					for(Map.Entry<Long, NetBankTransactionData> entry : transactions.entrySet()) {
						System.out.println(entry.getValue());
					}
					break;
				}
				case 3: {
					String oldPass = bb.readLine();
					String generatedPassword = NetBankUtils.getSecurePassword(oldPass);
					NetBankAccountData account = null; 
					try {
						account = DataBase.getDataStore().get(id);
						if(account.getSecurePassword().equals(generatedPassword)) {
							pr.println(NetBankServerProtocols.serverReadyToReceive);
							String newPass = bb.readLine();
							
							account.setSecurePassword(NetBankUtils.getSecurePassword(newPass));
							DataBase.updateData(account);
						}
						else {
							pr.println(NetBankServerProtocols.serverFinishCommunication);
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				}
				default: {

				}

				}

			} while(!protocol.equals(NetBankServerProtocols.allClientsServed) || !protocol.equals(NetBankServerProtocols.clientFinishCommunication));
		}
	}

	private int decodeProtocolAction(String protocol) {
		if(protocol.equals(NetBankClientProtocols.clientAddTransaction))
			return 1;
		else if(protocol.equals(NetBankClientProtocols.clientViewAllTransactions))
			return 2;
		else if(protocol.equals(NetBankClientProtocols.clientAlterCredentials))
			return 3;


		return -1;
	}


	public interface NetBankServerProtocols {
		int PORT = 12121;
		String localHost = "127.0.0.1";

		String serverReadyToSend = "ServerSend";
		String serverReadyToReceive = "ServerReceive";
		String serverFinishCommunication = "ServerFinished";

		String clientReadyToSend = "ClientSend";
		String clientReadyToRecieve = "ClientRecieve";
		String clientFinishCommunication = "ClientFinish";

		String allClientsServed = "AllClientsServed";
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
