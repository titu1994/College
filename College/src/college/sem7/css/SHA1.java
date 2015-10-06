package college.sem7.css;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

public class SHA1 {

	public static void main(String[] args) throws IOException {
		BufferedReader bb = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("Enter a string : ");
		String ip = bb.readLine();
		System.out.println("SHA-1 Digest : " + digest(ip));
	}

	private static String digest(String ip) {
		try {
			MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
			sha1.reset();
			sha1.update(ip.getBytes(StandardCharsets.UTF_8));
			return byteToHex(sha1.digest());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} 
		return null;
	}

	private static String byteToHex(final byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			formatter.format("%02x", b);
		}
		String result = formatter.toString();
		formatter.close();
		return result;
	}

}
