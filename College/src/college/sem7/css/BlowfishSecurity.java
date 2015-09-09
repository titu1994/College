package college.sem7.css;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class BlowfishSecurity {
	
	private static final int KEY_SIZE = 128;

	private static KeyGenerator KEY;
	private static SecretKey secretKey;
	private static Cipher encryptCipher;
	private static Cipher decryptCipher;
	
	static {
		try {
			KEY = KeyGenerator.getInstance("Blowfish");
			KEY.init(KEY_SIZE);
			secretKey = KEY.generateKey();
			///ECB/PKCS5Padding
			encryptCipher = Cipher.getInstance("Blowfish");
			encryptCipher.init(Cipher.ENCRYPT_MODE, secretKey);
			
			decryptCipher = Cipher.getInstance("Blowfish");
			decryptCipher.init(Cipher.DECRYPT_MODE, secretKey);
		} catch (NoSuchAlgorithmException e) {
			System.out.println("Could not load Blowfish Algorithm");
			System.exit(0);
		} catch (NoSuchPaddingException e) {
			System.out.println("Could not load padding PKCS5");
			System.exit(0);
		} catch (InvalidKeyException e) {
			System.out.println("Key is considered Invalid");
			System.exit(0);
		}
	}
	
	public static void main(String[] args) throws IOException {
		BufferedReader bb = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("Enter input string : " );
		String ip = bb.readLine();
		
		String encrypted = encryptString(ip);
		System.out.println("Encrypted String : " + encrypted);
		
		String decrypt = decryptString(encrypted);
		System.out.println("Decrypted String : " + decrypt);

	}
	
	private static String encryptString(String input) {
		String output = "";
		
		try {
			byte hold[] = input.getBytes("UTF8");
			byte encryptedBytes[] = encryptCipher.doFinal(hold);
			output = Base64.getEncoder().encodeToString(encryptedBytes);
			
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return output;
	}
	
	private static String decryptString(String input) {
		String output = "";
		
		byte decryptedBytes[];
		try {
			byte hold[] = Base64.getDecoder().decode(input);
			decryptedBytes = decryptCipher.doFinal(hold);
			output = new String(decryptedBytes);
			
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} 
		return output;
	}

}
