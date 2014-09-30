package college.sem5.sooad;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;

public class NetBankAccountData implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 115511L;

	private final long accountID;
	private String securePassword;
	private double creditMaxLimit;
	private double creditConsumed;

	public NetBankAccountData(long accountID, String securePassword) {
		this.accountID = accountID;
		this.securePassword = securePassword;
	}

	public String getSecurePassword() {
		return securePassword;
	}
	
	public void setSecurePassword(String securePassword) {
		this.securePassword = securePassword;
	}

	public double getCreditMaxLimit() {
		return creditMaxLimit;
	}

	public void setCreditMaxLimit(double creditMaxLimit) {
		this.creditMaxLimit = creditMaxLimit;
	}

	public double getCreditConsumed() {
		return creditConsumed;
	}

	public void setCreditConsumed(double creditConsumed) {
		this.creditConsumed = creditConsumed;
	}

	public static class DataBase {
		
		@SuppressWarnings("unchecked")
		public static HashMap<Long, NetBankAccountData> getDataStore() throws IOException {
			
		}

		public static void storeData() throws IOException {
			
		}

		public static boolean insertData(NetBankAccountData data) {
			
		}
		
		public static boolean updateData(NetBankAccountData data) {
			
		}
		
		public static boolean removeData(long id) {
			
		}

	}
}
