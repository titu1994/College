package college.sem5.sooad;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;

public class NetBankTransactionData implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 151451L;

	public final long transactionID;
	public final String transactionToName;
	public final String transactionFromName;
	public final double transactionAmount;
	
	public NetBankTransactionData(long transactionID, String transactionToName, String transactionFromName, double transactionAmount) {
		this.transactionID = transactionID;
		this.transactionAmount = transactionAmount;
		this.transactionToName = transactionToName;
		this.transactionFromName = transactionFromName;
	}

	public long getTransactionID() {
		return transactionID;
	}

	public String getTransactionToName() {
		return transactionToName;
	}

	public String getTransactionFromName() {
		return transactionFromName;
	}

	public double getTransactionAmount() {
		return transactionAmount;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("ID : " + transactionID + "\n");
		sb.append("To : " + transactionToName + "\n");
		sb.append("From : " + transactionFromName + "\n");
		sb.append("Amount : " + transactionAmount + "\n\n");
		return sb.toString();
	}



	public static class Database {
		
		
		@SuppressWarnings("unchecked")
		public static HashMap<Long, NetBankTransactionData> getDataStore() throws IOException {
			
		}
		
		public static void storeData() throws IOException {
			
		}
		
		public static boolean insertData(NetBankTransactionData data) {
			
		}
		
		public static boolean updateData(NetBankTransactionData data) {
			
		}
		
		public static boolean removeData(long id) {
			
		}
	}
	
}
