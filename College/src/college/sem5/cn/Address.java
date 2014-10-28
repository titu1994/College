package college.sem5.cn;

import java.io.*;
import java.util.*;
import java.net.*;

public class Address {

	public static void main(String args[]) throws IOException,NumberFormatException {

		printIPAndClass();
		printMACAddress();
		printSubnetMask();

	}

	private static void printIPAndClass() throws IOException{

		String ip = InetAddress.getLocalHost().getHostAddress();
		System.out.println("IP Address is : " + ip);
		String ips[] = ip.split("\\.");

		int classVal = Integer.parseInt(ips[0]);
		printIPClass(classVal);

	}

	private static void printIPClass(int classVal) {

		if(classVal >= 0 && classVal <= 127)
			System.out.println("IP Address is of class A");
		else if(classVal >= 128 && classVal <= 191)
			System.out.println("IP Address is of class B");
		else if(classVal >= 192 && classVal <= 223)
			System.out.println("IP Address is of class C");
		else if(classVal >= 224 && classVal <= 239)
			System.out.println("IP Address is of class D");
		else if(classVal >= 240 && classVal <= 255)
			System.out.println("IP Address is of class E");

	}

	private static void printMACAddress() {
		try {
			Enumeration<NetworkInterface> networks = NetworkInterface.getNetworkInterfaces();
			int count = 0;
			while(networks.hasMoreElements()) {
				NetworkInterface network = networks.nextElement();
				byte[] mac = network.getHardwareAddress();
				if(count == 0) {
					count++;
					continue;
				}	
				if(mac != null) {
					System.out.print("Current MAC address : ");

					StringBuilder sb = new StringBuilder();
					for (int i = 0; i < mac.length; i++) {
						sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
					}
					System.out.println(sb.toString());
					break;
				}
			}
		} catch (SocketException e){
			e.printStackTrace();
		}	
	}

	private static void printSubnetMask() throws IOException{
		String ip = InetAddress.getLocalHost().getHostAddress();
		String ips[] = ip.split("\\.");

		int classVal = Integer.parseInt(ips[0]);
		printSubnet(classVal);


	}

	private static void printSubnet(int classVal) {
		int count = 0;
		if(classVal >= 0 && classVal <= 127)
			count = 1;
		else if(classVal >= 128 && classVal <= 191)
			count = 2;
		else if(classVal >= 192 && classVal <= 223)
			count = 3;
		else if(classVal >= 224 && classVal <= 239)
			count = 4;
		else if(classVal >= 240 && classVal <= 255)
			count = 5;

		StringBuilder sb = new StringBuilder();	

		if(classVal != 4 || classVal != 5) {

			for(int i = 0; i < count; i++) {
				sb.append("255.");
			}

			for(int i = 0; i < 4 - count; i++) {
				sb.append("0.");
			}
			String s = sb.substring(0,sb.length()-1).toString();
			System.out.println("Subnet Mask is : " + s);
		}
	}

}