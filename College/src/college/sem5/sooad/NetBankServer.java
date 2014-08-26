package college.sem5.sooad;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NetBankServer {
	private static ExecutorService executor;
	private ServerSocket server = null;
	private static NetBankAccountData.DataBase database;
	
	public NetBankServer() {
		if(!isExecutorAvailable())
			executor = Executors.newCachedThreadPool();
		
		database = new NetBankAccountData.DataBase();
		
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

					String protocol;
					StringBuilder sb = new StringBuilder();

					System.out.println("Server : Ready to recieve data");
					pr.println(NetBankServerProtocols.serverReadyToReceive);

					System.out.println("Server : Recieving until client is ready to recieve");
					while(!(protocol = bb.readLine()).equals(NetBankServerProtocols.clientReadyToRecieve)) {
						if(protocol.equals(NetBankServerProtocols.clientFinishCommunication)) {
							pr.close();
							bb.close();
							client.close();
							return;
						}
							
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
			pr.println(NetBankServerProtocols.serverReadyToSend);
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
				data = database.getDataStore().get(id);
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
