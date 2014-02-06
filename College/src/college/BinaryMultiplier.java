package college;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BinaryMultiplier {

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader bb = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Enter the 2 no in Hex");
		
		int xi = Integer.parseInt(bb.readLine(), 16);
		int yi = Integer.parseInt(bb.readLine(), 16);
		
		String xs = Integer.toBinaryString(xi);
		String ys = Integer.toBinaryString(yi);
		
		String result = multiplyHexString(xs, ys);
		
		System.out.println(result);
		
	}
	
	private static String addHexStringInBinary(String xs, String ys){
		
		int xl = xs.length();
		int yl = ys.length();
		int len = xl > yl? xl : yl;
		
		for(int i = 0; i < len - xl; i++){
			xs = "0" + xs;
		}
		for(int i = 0; i < len - yl; i++){
			ys = "0" + ys;
		}
		
		StringBuilder sb = new StringBuilder();
		int s = 0,cin = 0, xt,yt;
		
		for(int i = 0; i < len; i++){
			xt = Integer.parseInt(xs.charAt(len-1-i) + "", 2);
			yt = Integer.parseInt(ys.charAt(len-1-i) + "", 2);
			
			s = xt ^ yt ^ cin;
			cin = (xt & cin) | (yt & cin) | (xt & yt);
			
			sb.append(s);
		}
		sb.append(cin);
		sb.reverse();
	
		int val = Integer.parseInt(sb.toString(), 2);
		
		return Integer.toHexString(val);
	}
	
	private static String multiplyHexString(String m, String q) {
		
		int xl = m.length();
		int yl = q.length();
		int len = xl > yl? xl : yl;
		
		for(int i = 0; i < len - xl; i++){
			m = "0" + m;
		}
		for(int i = 0; i < len - yl; i++){
			q = "0" + q;
		}
		
		StringBuilder sb;
		String A = "";
		char C;
		int q0;
		
		for(int i = 0; i < len; i++){
			A = "0"+A;
		}
		
		A = addHexStringInBinary(A, m);
		A = A.substring(1);
		
		for(int i = 0; i < len; i++){
			q0 = Integer.parseInt(q.charAt(len-1)+"", 2);
			
			if(q0 == 1){
				A = addHexStringInBinary(A, m);
			}
			
			C = A.charAt(0);
			sb = new StringBuilder(A);
			sb.deleteCharAt(0);
			
			sb.deleteCharAt(len-1);
			sb.insert(0, C);
			
			sb = new StringBuilder(q);
			sb.deleteCharAt(len-1);
			sb.insert(0, "0");
		}
		int val = Integer.parseInt(A, 2);
		return Integer.toHexString(val);
	}

}
