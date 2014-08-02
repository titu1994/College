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
	
	public static class Database {
		private static HashMap<Long, NetBankTransactionData> map;
		
		@SuppressWarnings("unchecked")
		public HashMap<Long, NetBankTransactionData> getDataStore() throws IOException {
			if(map!= null)
				return map;
			else {
				FileInputStream fos = new FileInputStream("/database/transactions.ser");
				ObjectInputStream oos = new ObjectInputStream(fos);
				try {
					map = (HashMap<Long, NetBankTransactionData>) oos.readObject();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				oos.close();
				fos.close();
				return map;
			}
		}
		
		public void storeData() throws IOException {
			FileOutputStream fos = new FileOutputStream("/database/transactions.ser", false);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(map);
			oos.close();
			fos.close();
		}
		
		public boolean insertData(NetBankTransactionData data) {
			try {
				map = getDataStore();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(!map.containsKey(data.transactionID)) {
				map.put(data.transactionID, data);
				return true;
			}
			else {
				return false;
			}
		}
		
		public boolean updateData(NetBankTransactionData data) {
			try {
				map = getDataStore();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(map.containsKey(data.transactionID)) {
				map.remove(data.transactionID);
				map.put(data.transactionID, data);
				return true;
			}
			else {
				return false;
			}
		}
		
		public boolean removeData(long id) {
			try {
				map = getDataStore();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(map.containsKey(id)) {
				map.remove(id);
				return true;
			}
			else {
				return false;
			}
		}

		
	}
	
}
