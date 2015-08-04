package college.sem6.css;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

public class SubstitutionCipher {

	private static final int MIN = 0, MAX = 128;
	private static HashMap<Character, Character> map;
	private static HashMap<Character, Character> inverseMap;
	private static Random random = new Random();
	
	public static void main(String[] args) throws IOException {
		loadMaps();
		
		BufferedReader bb = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("Input String : ");
		String input = bb.readLine();
		
		String encryptedString = encrypt(input);
		System.out.println("Encrypted String : " + encryptedString);
		
		String decryptedString = decrypt(encryptedString);
		System.out.println("Decrypted String : " + decryptedString);
		
	}
	
	public static void loadMaps() {
		map = new HashMap<>();
		inverseMap = new HashMap<>();
		
		for(int i = 0; i <= 128; i++) {
			map.put((char) i, (char) getNextRandom());
		}
		
		if(map != null && inverseMap != null) {
			Set<Entry<Character, Character>> set = map.entrySet();
			for(Entry<Character, Character> entry : set) {
				inverseMap.put(entry.getValue(), entry.getKey());
			}
		}
	}

	private static int getNextRandom() {
		int rand = random.nextInt(MAX - MIN + 1) + MIN;
		while(rand >= MIN && rand <= MAX && map.containsValue((char) rand)) {
			rand = random.nextInt(MAX - MIN + 1) + MIN;
		}
		
		return rand;
	}
	
	public static String encrypt(String input) {
		StringBuffer sb = new StringBuffer();
		input = input.toUpperCase();
		
		char x = ' ';
		for(int i = 0; i < input.length(); i++) {
			x = input.charAt(i);
			sb.append(map.get(x));
		}
		
		return sb.toString();
	}
	
	public static String decrypt(String input) {
		StringBuffer sb = new StringBuffer();
		
		char x = ' ';
		for(int i = 0; i < input.length(); i++) {
			x = input.charAt(i);
			sb.append(inverseMap.get(x));
		}
		
		return sb.toString();
	}

}
