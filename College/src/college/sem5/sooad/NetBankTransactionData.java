package college.sem5.sooad;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;

public class NetBankTransactionData implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 151451L;

	public final long transactionID;
	public final String transactionToName;
	public final double transactionAmount;
	
	public NetBankTransactionData(long transactionID, String transactionToName, String transactionFromName, double transactionAmount) {
		this.transactionID = transactionID;
		this.transactionAmount = transactionAmount;
		this.transactionToName = transactionToName;
	}

	public long getTransactionID() {
		return transactionID;
	}

	public String getTransactionToName() {
		return transactionToName;
	}

	public double getTransactionAmount() {
		return transactionAmount;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("ID : " + transactionID + "\n");
		sb.append("To : " + transactionToName + "\n");
		sb.append("Amount : " + transactionAmount + "\n\n");
		return sb.toString();
	}

	public static class Database {
		
		public static NetBankTransactionData[] getDataStore() {
			return NetBankUtils.queryTransaction();
		}
		
		public static NetBankTransactionData getDataStore(long id){
			return NetBankUtils.queryTransaction(id);
		}
		
		public static boolean insertData(NetBankTransactionData data) {
			return NetBankUtils.insertData(data);
		}
		
		public static boolean updateData(NetBankTransactionData data) {
			return NetBankUtils.updateData(data);
		}
		
		public static boolean removeData(long id) {
			return NetBankUtils.deleteTransaction(id);
		}
	}
	
}
