package college.sem7.css;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

public class TranspositionCipher {

	private static char columnar[][];
	private static char permutationColumnar[][];

	private static HashMap<Integer, Integer> keymap = new HashMap<>();
	private static HashMap<Integer, Integer> inverseKeymap = new HashMap<>();

	private static int colsize = 5;

	public static void main(String[] args) throws IOException{
		BufferedReader bb = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("Enter the input string : ");
		String input = bb.readLine();
		input = input.replace(" ",  "");
		
		//generateKeys(colsize);
		keymap.put(2, 0); keymap.put(0, 1); keymap.put(3, 2); keymap.put(4, 3); keymap.put(1,  4);
		inverseKeymap.put(0, 2); inverseKeymap.put(1, 0); inverseKeymap.put(2, 3); inverseKeymap.put(3, 4); inverseKeymap.put(4, 1);
		
		columnar = toColumnarForm(input);
		columnar = shiftColumnarForm(columnar);
		
		String firstEncryptedString = columnarFormString(columnar);
		System.out.println("First output : " + firstEncryptedString);
		
		permutationColumnar = toColumnarForm(firstEncryptedString);
		permutationColumnar = shiftColumnarForm(permutationColumnar);
		//printArr(permutationColumnar);
		
		String finalEncryptedString = columnarFormString(permutationColumnar);
		System.out.println("Final Output : " + finalEncryptedString);
		
		System.out.println("Beginning decryption : ");
		
		permutationColumnar = reverseShiftColumnarForm(permutationColumnar);
		printArr(permutationColumnar);
		
		String reversedString = columnarFormString(permutationColumnar);
		System.out.println("First Decryption : " + reversedString);
		
		columnar = toColumnarForm(reversedString);
		printArr(columnar);
		columnar = reverseShiftColumnarForm(columnar);
		
		String finalDecryptedString = columnarFormString(columnar);
		
		System.out.println("Final Decrypted String : " + finalDecryptedString);
		
		
	}

	private static void generateKeys(int keysize) {
		Random r = new Random();
		int x = r.nextInt(keysize);
		for(int i = 0; i < keysize; i++) {
			while(keymap.containsValue(x))
				x = r.nextInt(keysize);

			keymap.put(i,  x);
			inverseKeymap.put(x,  i);
		}
	}

	private static char[][] toColumnarForm(String input) {
		int rowsize = (int) Math.ceil(input.length() / (double) colsize);
		
		//System.out.println("String length : " + input.length() + " Row Size : " + rowsize);

		char holder[][] = new char[rowsize][colsize];

		for(int i = 0; i < rowsize; i++) {
			for(int j = 0; j < colsize; j++) {
				if(i * colsize + j < input.length()) 
					holder[i][j] = input.charAt(i * colsize + j);
				else 
					holder[i][j] = '.';
			}
		}

		return holder;
	}
	
	private static char[][] shiftColumnarForm(char input[][]) {
		char output[][] = new char[input.length][colsize];
		int i = 0, j = 0;
		
		Set<Entry<Integer, Integer>> entries = keymap.entrySet();
		for(Entry<Integer, Integer> entry : entries) {
			int col = entry.getValue();
			for(int row = 0; row < input.length; row++) {
				output[row][col] = input[i++ % input.length][j];
			}
			j++;
		}
		
		return output;
	}
	
	private static void printArr(char x[][]) {
		StringBuilder sb = new StringBuilder();
		
		for(int i = 0; i < x.length; i++)  {
			for(int j = 0; j < x[i].length; j++) {
				sb.append(x[i][j]);
			}
			sb.append("\n");
		}
		System.out.println(sb);
	}
	
	private static char[][] reverseShiftColumnarForm(char input[][]) {
		char output[][] = new char[input.length][colsize];
		int i = 0, j = 0;
		
		Set<Entry<Integer, Integer>> entries = inverseKeymap.entrySet();
		for(Entry<Integer, Integer> entry : entries) {
			int col = entry.getValue();
			for(int row = 0; row < input.length; row++) {
				output[row][col] = input[i++ % input.length][j];
			}
			j++;
		}
		
		return output;
	}

	private static String columnarFormString(char input[][]) {
		StringBuffer sb = new StringBuffer();
		
		for(int i = 0; i < input[0].length; i++) {
			for(int j = 0; j < input.length; j++) {
				sb.append(input[j][i]);
			}
		}
		return sb.toString();
	}

}
