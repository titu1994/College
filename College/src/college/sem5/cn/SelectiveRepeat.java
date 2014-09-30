package college.sem5.cn;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class SelectiveRepeat {
	public static void main(String args[]) {
		ServerThread server = new ServerThread();
		server.start();

		try {
			Thread.sleep(1000);
		} catch(InterruptedException e) {
			System.out.println("Interrupted");	
		}

		ClientThread client = new ClientThread();
		client.start();

	}


	public static class ServerThread extends Thread {
		public static final int port = 8080;
		public static final String host	= "127.0.0.1";

		private ServerSocket server;
		private Socket client;

		public ServerThread() {
			try {
				server = new ServerSocket(port);
				server.setSoTimeout(10000);
			} catch(IOException e) {
				System.out.println("Server socket Failed");
			}
			System.out.println("Server is running");
		}

		public void run() {
			System.out.println("Server: Waiting for a client");
			try {
				client = server.accept();
			} catch(IOException e) {
				System.out.println("Server socket Failed");
			}	

			try {
				synchronized(client) {

					System.out.println("Server: Accepted a client");
					PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())), true);
					BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));

					BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

					System.out.println("Enter a string to send : ");
					String data = reader.readLine();

					System.out.println("Server : Sending output");
					String input;

					for(int i = 0; i < data.length(); i++) {
						out.println(data.charAt(i));
					}

					out.println("Finished");

					int errorPos = -1;
					while(!(input = in.readLine()).equals("Finished")) {
						errorPos = Integer.parseInt(input);
						out.println(data.charAt(errorPos));
					}

					System.out.println("Server : Data Sent. Closing connections");
					in.close();
					out.close();
					client.close();

					System.out.println("Server : Shutting down");
					server.close();	

				}
			} catch(Exception e) {
				e.printStackTrace();
			}	
		}
	}

	public static class ClientThread extends Thread{
		private Socket client;

		public ClientThread() {
			try {
				client = new Socket(ServerThread.host, ServerThread.port);
			} catch(IOException e ) {
				System.out.println("Server socket Failed");
			}
			System.out.println("Client : Connected to Server");
		}


		public void run() {
			try {	
				synchronized(client) {
					PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())), true);
					BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));

					StringBuilder buffer = new StringBuilder();
					String input;
					while(!(input = in.readLine()).equals("Finished"))
						buffer.append(input);

					input = buffer.toString();
					int len = input.length();

					Random random = new Random();

					for(int i = 0; i < len; i++) {
						if(random.nextDouble() <= 0.33) {
							System.out.println("Error occured at i = " + i);
							char t = buffer.charAt(i);

							if(t == '0') {
								buffer.setCharAt(i, '1');
							}
							else {
								buffer.setCharAt(i, '0');
							}

							out.println(i);
							buffer.setCharAt(i, in.readLine().charAt(0));
							System.out.println("Error corrected at i = " +i);
							System.out.println("Buffer : " + buffer.substring(0,i+1));
						}
					}


					System.out.println("Client : Printing data");
					System.out.println("Buffer : " + buffer.toString());

					System.out.println("Client : Closing streams");
					out.println("Finished");

					in.close();
					out.close();

					System.out.println("Client : Stopping client socket");
					client.close();					

				}
			} catch(Exception e) {
				e.printStackTrace();
			}
		}

	}
}