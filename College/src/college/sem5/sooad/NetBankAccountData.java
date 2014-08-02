package college.sem5.sooad;

import java.io.FileInputStream;
import java.io.FileOutputStream;
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
	private final String securePassword;
	private double creditMaxLimit;
	private double creditConsumed;

	public NetBankAccountData(long accountID, String securePassword) {
		this.accountID = accountID;
		this.securePassword = securePassword;
	}

	public String getSecurePassword() {
		return securePassword;
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
		private static HashMap<Long, NetBankAccountData> map;

		@SuppressWarnings("unchecked")
		public HashMap<Long, NetBankAccountData> getDataStore() throws IOException {
			if(map!= null)
				return map;
			else {
				FileInputStream fos = new FileInputStream("/database/account.ser");
				ObjectInputStream oos = new ObjectInputStream(fos);
				try {
					map = (HashMap<Long, NetBankAccountData>) oos.readObject();
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
			FileOutputStream fos = new FileOutputStream("/data/account.ser", false);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(map);
			oos.close();
			fos.close();
		}

		public boolean insertData(NetBankAccountData data) {
			try {
				map = getDataStore();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(!map.containsKey(data.accountID)) {
				map.put(data.accountID, data);
				return true;
			}
			else {
				return false;
			}
		}
		
		public boolean updateData(NetBankAccountData data) {
			try {
				map = getDataStore();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(map.containsKey(data.accountID)) {
				map.remove(data.accountID);
				map.put(data.accountID, data);
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
