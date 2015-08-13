package college.sem7.css;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

public class ColumnarCeaserCipher {

	private static final int MIN = 0, MAX = 128;
	private static HashMap<Character, Character> map;
	private static HashMap<Character, Character> inverseMap;
	private static Random random = new Random();

	private static int rowsize;
	private static int colsize = 5;

	private static char NULL_CHAR = (char) 129;

	private static final String KEYS = "31452";

	public static void main(String[] args) throws IOException {
		loadMaps();

		BufferedReader bb = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("Input String : ");
		String input = bb.readLine();

		String encryptedString = encrypt(input);
		System.out.println("Encrypted String (Using Random Hashmap): "
				+ encryptedString);

		// Column Cipher
		String plain = encryptedString;
		computeCaesarRowSize(plain);

		char[][] a = new char[rowsize][colsize];
		char[][] b = new char[rowsize][colsize];
		int[] key2 = new int[colsize];
		String cipher1 = "";
		String cipher2 = "";
		String decipher1 = "";
		String decipher2 = "";

		System.out.println("Enter keys or 'D' for Default [31452]): ");
		String key = bb.readLine();

		if(key.contains("D"))
			key = KEYS;
			
		for (int i = 0; i < colsize; i++)
			key2[i] = Integer.parseInt(String.valueOf(key.charAt(i)));

		int k = 0;
		plain = plain.replaceAll(" ", "");
		System.out.println("Matrix: ");

		// Create matrix
		for (int i = 0; i < rowsize; i++) {
			for (int j = 0; j < colsize; j++) {
				if (k < plain.length())
					a[i][j] = plain.charAt(k);
				else
					a[i][j] = NULL_CHAR;

				k++;
				System.out.print(a[i][j] + " ");
			}
			System.out.println();
		}

		// Create permuted matrix
		System.out.println("Permuted by key:");
		for (int i = 0; i < key2.length; i++) {
			for (int j = 0; j < rowsize; j++) {
				b[j][i] = a[j][key2[i] - 1];
			}
		}

		// Create cipher1
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < rowsize; j++) {
				cipher1 += b[j][i];
			}
		}

		System.out.println(cipher1);

		// 2nd Columnization
		System.out.println("Second Columnized matrix");
		k = 0;
		for (int i = 0; i < rowsize; i++) {
			for (int j = 0; j < colsize; j++) {
				a[i][j] = cipher1.charAt(k);

				k++;
				System.out.print(a[i][j] + " ");
			}
			System.out.println();
		}

		// Create second permuted matrix
		System.out.println("Permuted by key:");
		for (int i = 0; i < key2.length; i++) {
			for (int j = 0; j < rowsize; j++) {
				b[j][i] = a[j][key2[i] - 1];
			}
		}

		// Create cipher2
		for (int i = 0; i < colsize; i++) {
			for (int j = 0; j < rowsize; j++) {
				cipher2 += b[j][i];
			}
		}

		System.out.println(cipher2);

		// Deciphering
		// 1st deciphering column
		System.out.println("First Columnized matrix deciphering");
		k = 0;
		for (int i = 0; i < colsize; i++) {
			for (int j = 0; j < rowsize; j++) {
				a[j][i] = cipher2.charAt(k);
				k++;
			}
		}

		// First permutation
		for (int i = 0; i < key2.length; i++) {
			for (int j = 0; j < rowsize; j++) {
				b[j][key2[i] - 1] = a[j][i];
			}
		}

		for (int i = 0; i < rowsize; i++) {
			for (int j = 0; j < colsize; j++) {
				System.out.print(b[i][j] + " ");
			}
			System.out.println();
		}

		// Deciphered1
		System.out.println("Deciphered 1");
		for (int i = 0; i < rowsize; i++) {
			for (int j = 0; j < colsize; j++) {
				decipher1 += b[i][j];
			}
		}
		System.out.println(decipher1);

		// Decipher matrix 2
		System.out.println("Matrix 2");
		k = 0;
		for (int i = 0; i < colsize; i++) {
			for (int j = 0; j < rowsize; j++) {
				a[j][i] = decipher1.charAt(k);
				k++;
			}
		}

		// Second permutation
		for (int i = 0; i < key2.length; i++) {
			for (int j = 0; j < rowsize; j++) {
				b[j][key2[i] - 1] = a[j][i];
			}
		}

		for (int i = 0; i < rowsize; i++) {
			for (int j = 0; j < colsize; j++) {
				System.out.print(b[i][j] + " ");
			}
			System.out.println();
		}

		for (int i = 0; i < rowsize; i++) {
			for (int j = 0; j < colsize; j++) {
				decipher2 += b[i][j];
			}
		}
		decipher2 = decipher2.replace("" + NULL_CHAR, "");
		System.out.println("Deciphered Text:");
		System.out.println(decipher2);
		// Column end

		String decryptedString = decrypt(decipher2);
		System.out.println("Decrypted String : " + decryptedString);
	}

	public static void loadMaps() {
		map = new HashMap<Character, Character>();
		inverseMap = new HashMap<Character, Character>();

		for (int i = 0; i <= 128; i++) {
			map.put((char) i, (char) getNextRandom());
		}

		if (map != null && inverseMap != null) {
			Set<Entry<Character, Character>> set = map.entrySet();
			for (Entry<Character, Character> entry : set) {
				inverseMap.put(entry.getValue(), entry.getKey());
			}
		}
	}

	private static int getNextRandom() {
		int rand = random.nextInt(MAX - MIN + 1) + MIN;
		while (rand >= MIN && rand <= MAX && map.containsValue((char) rand)) {
			rand = random.nextInt(MAX - MIN + 1) + MIN;
		}

		return rand;
	}

	public static String encrypt(String input) {
		StringBuffer sb = new StringBuffer();
		//input = input.toUpperCase();

		char x = ' ';
		for (int i = 0; i < input.length(); i++) {
			x = input.charAt(i);
			sb.append(map.get(x));
		}

		return sb.toString();
	}

	public static String decrypt(String input) {
		StringBuffer sb = new StringBuffer();

		char x = ' ';
		for (int i = 0; i < input.length(); i++) {
			x = input.charAt(i);
			sb.append(inverseMap.get(x));
		}

		return sb.toString();
	}

	private static int computeCaesarRowSize(String input) {
		int rsize = (int) Math.ceil(input.length() / (double) colsize);
		rowsize = rsize;
		return rsize;
	}
}

/*
Input String : Enemy attacks tonight!
Encrypted String (Using Random Hashmap): z`x"ZZ*[pZ7(|ZP
Enter key (Column Size = 5): 
31452
Matrix: 
z  ` x " 
  Z Z  
 * [ p  Z 
7   ( | 
Z P ? ? ? 
Permuted by key:
`Zp?z*7ZxZ(?"Z|?[P
Second Columnized matrix
` Z p  ? 
z  * 7 Z 
x Z  ( ? 
"  Z | ? 
  [  P 
Permuted by key:
p*Z[`zx"7(|?Z??PZZ
First Columnized matrix deciphering
` Z p  ? 
z  * 7 Z 
x Z  ( ? 
"  Z | ? 
  [  P 
Deciphered 1
`Zp?z*7ZxZ(?"Z|?[P
Matrix 2
z  ` x " 
  Z Z  
 * [ p  Z 
7   ( | 
Z P ? ? ? 
Deciphered Text:
z`x"ZZ*[pZ7(|ZP
Decrypted String : Enemy attacks tonight!
 */