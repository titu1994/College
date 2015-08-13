package college.sem7.css;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TranspositionCipher {
	
	public static void main(String[] args) throws IOException{
		char a[][] = new char[5][5];
		char b[][] = new char[5][5];
		char c[][] = new char[5][5];
		char d[][] = new char[5][5];
		char e[][] = new char[5][5];
		char f[][] = new char[5][5];
		char g[][] = new char[5][5];
		char q[] = new char[5 * 5];
		int key[] = new int[5];
		BufferedReader bb = new BufferedReader(new InputStreamReader(System.in));

		System.out.println("Enter input string of max 25 characters without a space");
		String w = bb.readLine();
		w = w.replace(" ", "").trim();
		
		if(w.length() > 25) {
			System.out.println("Cannnot accept greater than 25 characters.");
			return;
		}
		
		System.arraycopy(w.toCharArray(), 0, q, 0, w.length());
		
		for (int i = w.length(); i < 25; i++)
			q[i] = '.';
		
		System.out.println("Enter 5 digit key with a space in between digits");
		String keys = bb.readLine();
		String keyArr[] = keys.split(" ");
		for (int i = 0; i < 5; i++) {
			key[i] = Integer.parseInt(keyArr[i]);
		}
		
		int i, j, k;
		for (i = 0, k = 0; i < 5; i++) {
			for (j = 0; j < 5; j++, k++) {
				a[i][j] = q[k];
			}
		}
		for (i = 0, k = 0; i < 5; i++) {
			k = key[i] - 1;
			for (j = 0; j < 5; j++) {
				b[j][i] = a[j][k];
			}
		}
		System.out.println("Encryption :");
		System.out.print("Ct is :");
		for (i = 0; i < 5; i++) {
			for (j = 0; j < 5; j++)
				System.out.print("" + b[j][i]);
		}
		for (i = 0; i < 5; i++) {
			for (j = 0; j < 5; j++)
				c[i][j] = b[j][i];
		}
		for (i = 0, k = 0; i < 5; i++) {
			k = key[i] - 1;
			for (j = 0; j < 5; j++) {
				d[j][i] = c[j][k];
			}
		}
		System.out.println();
		System.out.print("Ct2 is :");
		for (i = 0; i < 5; i++) {
			for (j = 0; j < 5; j++)
				System.out.print("" + d[j][i]);
		}
		System.out.println();
		System.out.println("Decryption :");
		for (i = 0, k = 0; i < 5; i++) {
			k = key[i] - 1;
			for (j = 0; j < 5; j++) {
				e[j][k] = d[j][i];
			}
		}
		for (i = 0; i < 5; i++) {
			for (j = 0; j < 5; j++)
				f[i][j] = e[j][i];
		}
		System.out.print("Ctdecrpt is :");
		for (i = 0; i < 5; i++) {
			for (j = 0; j < 5; j++)
				System.out.print("" + f[j][i]);
		}
		System.out.println();
		for (i = 0, k = 0; i < 5; i++) {
			k = key[i] - 1;
			for (j = 0; j < 5; j++) {
				g[j][k] = f[j][i];
			}
		}
		System.out.print("MsgDecrypt is :");
		for (i = 0; i < 5; i++) {
			for (j = 0; j < 5; j++)
				System.out.print("" + g[i][j]);
		}

	}
}
