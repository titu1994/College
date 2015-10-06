package college.sem7.css;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class Salt {

	private static byte[] salt = "AstBf13G".getBytes(StandardCharsets.UTF_8); // 8 Chars = 64 bits Salt
	private static MessageDigest sha1;
	
	public static void main(String[] args) throws IOException {
		try {
			sha1 = MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		BufferedReader bb = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("Enter a password to store : ");
		String originalPassword = bb.readLine();
		
		byte originalHash[] = getHash(originalPassword);
		System.out.println("Original Hash : " + Arrays.toString(originalHash));
		
		System.out.print("Enter password to authenticate : ");
		String newPassword = bb.readLine();
		
		if(authenticate(originalHash, newPassword)) {
			System.out.println("The user is authentic as passwords match");
		}
		else {
			System.out.println("The user is not authentic as passwords do not match");
		}
		
	}
	
	private static byte[] getHash(String password) {
		sha1.reset();
		sha1.update(salt);
		return sha1.digest(password.getBytes(StandardCharsets.UTF_8));
	}

	private static boolean authenticate(byte hash[], String password) {
		byte passHash[] = getHash(password);
		return Arrays.equals(hash, passHash);
	}
	
}
