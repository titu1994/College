package college;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BinaryAdder {

	
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader bb = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Enter the 2 no in Hex");
		
		int xi = Integer.parseInt(bb.readLine(), 16);
		int yi = Integer.parseInt(bb.readLine(), 16);
		
		String xs = Integer.toBinaryString(xi);
		String ys = Integer.toBinaryString(yi);
		
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
		
		System.out.println(val);
	}

}
