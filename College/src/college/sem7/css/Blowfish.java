package college.sem7.css;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Blowfish {

	private static double PI = Math.PI;
	private static int mantissa;
	
	private static int p[] = new int[18], k[] = new int[14];
	private static int s[][] = new int[4][256];
	
	private static BufferedReader bb = new BufferedReader(new InputStreamReader(System.in));
	
	public static void main(String[] args) throws IOException {
		System.out.println("Enter 14 keys seperated by spaces");
		String ip = bb.readLine();
		String parts[] = ip.split(" ");
		
		for(int i = 0; i < k.length; i++) {
			k[i] = Integer.parseInt(parts[i].trim());
		}
		
		initialize();
		

	}
	
	private static void initialize() {
		int ex = 3;
		String indent = "";
		for(int i = 0; i < p.length; i++) {
			for(int k = 0; k < 8; k++) {
				indent += Integer.toHexString(getNextIndent());
			}
			p[i] = Integer.parseInt(indent, 16);
			indent = "";
		}
		
		for(int i = 0; i < s.length; i++) {
			for(int j = 0; j < s[i].length; j++) {
				s[i][j] = (int) Math.floor((PI - ex) * 16);
				ex = p[i];
			}
		}
	}
	
	private static int getNextIndent() {
		double x = (PI - mantissa) * 16;
		mantissa = (int) x;
		PI = x;
		return mantissa;
	}

}
