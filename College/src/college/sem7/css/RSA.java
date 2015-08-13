package college.sem7.css;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class RSA {

	private static long p, q, n, phi_n;
	private static long e[], d[];
	private static StringBuilder en, m, temp;
	private static boolean flag;
	private static String msg;

	public static void main(String[] args) throws IOException {
		BufferedReader bb = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("Enter the first prime no : ");
		p = Integer.parseInt(bb.readLine());
		flag = prime(p);
		if (flag == false) {
			System.out.println("WRONG INPUT");
			return;
		}
		System.out.print("Enter another prime number : ");
		q = Integer.parseInt(bb.readLine());
		flag = prime(q);
		if (flag == false || p == q) {
			System.out.println("WRONG INPUT");
			return;
		}
		System.out.print("Enter message : ");
		msg = bb.readLine();
		msg = msg.toLowerCase();
		
		n = p * q;
		phi_n = (p - 1) * (q - 1);
		calculateE();
		System.out.println("Possible values of e and d are : ");
		for (int i = 0; i < e.length; i++)
			System.out.println(e[i] + " phi_n " + d[i]);

		encrypt();
		decrypt();
	}

	private static boolean prime(long pr) {
		int j = (int) Math.sqrt(pr);
		for (int i = 2; i <= j; i++) {
			if (pr % i == 0)
				return false;
		}
		return true;
	}

	private static void calculateE() {
		int k = 0;
		long flagD = 0;
		ArrayList<Long> eList = new ArrayList<>();
		ArrayList<Long> dList = new ArrayList<>();
		
		for (int i = 2; i < phi_n; i++) {
			if (phi_n % i == 0)
				continue;
			flag = prime(i);
			if (flag == true && i != p && i != q) {
				eList.add((long) i);
				flagD = calculateD(i);
				if (flagD > 0) {
					dList.add(flagD);
					k++;
				}
			}
		}
		
		e = new long[eList.size()];
		d = new long[dList.size()];
		
		for(int i = 0; i < e.length; i++) {
			e[i] = eList.get(i);
			d[i] = dList.get(i);
		}
	}

	private static long calculateD(long x) {
		long k = 1;
		while (true) {
			k = k + phi_n;
			if (k % x == 0)
				return (k / x);
		}
	}

	private static void encrypt() {
		long pt, ct, key = e[0], k, len;
		len = msg.length();
		
		temp = new StringBuilder();
		en = new StringBuilder();
		
		for (int i = 0; i < len; i++) {
			pt = msg.charAt(i);
			pt = pt - 96;
			k = 1;
			for (int j = 0; j < key; j++) {
				k = k * pt;
				k = k % n;
			}
			temp.append((char) k);
			ct = k + 96;
			en.append((char) ct);
		}

		System.out.println("The encrypted message is : " + en);
	}

	private static void decrypt() {
		long pt, ct, key = d[0], k;
		
		m = new StringBuilder();
		for (int i = 0; i < en.length(); i++) {
			ct = temp.charAt(i);
			k = 1;
			for (int j = 0; j < key; j++) {
				k = k * ct;
				k = k % n;
			}
			pt = k + 96;
			m.append((char) pt);
		}

		System.out.println("The decrypted message  : " + m);
	}
}
