package college.sem5.cn;

import java.net.*;
import java.io.*;

public class ServerClientClass {
	public static void main(String args[]) {
		ServerThread server = new ServerThread();
		server.start();

		try {
			Thread.sleep(2000);
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
				System.out.println("Server socket Failed.");
			}
			System.out.println("Server is running.");
		}

		public void run() {
			System.out.println("Server: Waiting for a client.");
			try {
				client = server.accept();
			} catch(IOException e) {
				System.out.println("Server socket Failed.");
			}	

			try {
				synchronized(client) {

					System.out.println("Server: Accepted a client.");
					PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())), 

							true);
					BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));

					System.out.println("Server : Accepting input to process.");
					String input;
					StringBuilder buffer = new StringBuilder();

					while(!(input = in.readLine()).equals("Finish")) {
						System.out.println("Server : Read Line.");
						buffer.append(input);
					}

					System.out.println("Server : Recieved entire input string.");
					input = buffer.toString();
					input = input.trim().toUpperCase();
					
					System.out.println("Server : Finished performing task on input.");

					System.out.println("Server : Sendint data to client.");
					out.println(input);

					System.out.println("Server : Data Sent. Closing connections.");
					in.close();
					out.close();
					client.close();

					System.out.println("Server : Shutting down.");
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
				System.out.println("Server socket Failed.");
			}
			System.out.println("Client : Connected to Server.");
		}


		public void run() {
			try {	
				synchronized(client) {
					System.out.println("Client : Asking user for input.");	
					System.out.println("Please enter some text : ");
					BufferedReader bb = new BufferedReader(new InputStreamReader(System.in));

					String input = bb.readLine();

					PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())), true);
					BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));

					out.println(input);
					out.println("Finish");

					StringBuilder buffer = new StringBuilder();

					System.out.println("Client : Receiving new data from server.");
					while((input = in.readLine()) != null) {
						buffer.append(input);
					}

					System.out.println("Client : Recieved entire output of server.");
					
					input = buffer.toString();
					System.out.println("Client : Printing server output.");
					
					System.out.println();
					System.out.println(input);
					System.out.println();
					
					System.out.println("Client : Closing streams.");
					in.close();
					out.close();

					System.out.println("Client : Stopping client socket.");
					client.close();					

				}
			} catch(Exception e) {
				e.printStackTrace();

			}
		}

	}
}