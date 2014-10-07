package college.sem5.sooad;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class NetBankUtils {

	public static final String HOST = "jdbc:mysql://sql3.freemysqlhosting.net";
	public static final String DB_NAME = "sql353714";
	public static final String DB_USER = "sql353714";
	public static final String DB_PASS = "vW3!xL9*";
	
	private static final String TABLE_ACCOUNT = DB_NAME + "." + "AccountDB";
	private static final String TABLE_TRANSACTIONS = DB_NAME + "."+ "TransactionDB";
	public static final int PORT = 3306;
	
	private static final String COL_ACCID = "accountID";
	private static final String COL_ACCSECUREPASSWORD = "securePassword";
	private static final String COL_ACCCREDITMAX = "creditMaxLimit";
	private static final String COL_ACCCREDITCONSUMED = "creditConsumed";
	
	private static final String COL_TRANID = "transactionID";
	private static final String COL_TRANTONAME = "transactionToName";
	private static final String COL_TRANFROMNAME = "transactionFromName";
	private static final String COL_TRANSAMMOUNT = "transactionAmount";
	
	private static Connection conn;

	public static String getSecurePassword(String insecurePassword) {
		String generatedPassword = null;
		/*try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(insecurePassword.getBytes());
			byte[] bytes = md.digest();
			StringBuilder sb = new StringBuilder();
			for(int i=0; i< bytes.length ;i++) {
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			}
			generatedPassword = sb.toString();
		} 
		catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}*/
		return insecurePassword;
	}

	public static Connection connect() {
		try {
			if(conn == null || conn.isClosed()) {
				try {
					Class.forName("com.mysql.jdbc.Driver");
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				try {
					conn = DriverManager.getConnection(HOST, DB_USER, DB_PASS);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}

	public static boolean insertData(NetBankAccountData data) {
		conn = connect();
		try {
			String sql = "insert into " + TABLE_ACCOUNT + " values(?,?,?,?)";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setLong(1, data.getAccountID());
			stmt.setString(2, data.getSecurePassword());
			stmt.setDouble(3, data.getCreditMaxLimit());
			stmt.setDouble(4, data.getCreditConsumed());
			
			stmt.executeQuery();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public static boolean insertData(NetBankTransactionData data) {
		conn = connect();
		try {
			String sql = "insert into " + TABLE_TRANSACTIONS + " values(?,?,?,?)";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setLong(1, data.getTransactionID());
			stmt.setString(2, data.getTransactionToName());
			stmt.setString(3, data.getTransactionFromName());
			stmt.setDouble(4, data.getTransactionAmount());
			
			stmt.executeQuery();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public static boolean updateData(NetBankAccountData data) {
		conn = connect();
		try {
			String sql = "update " + TABLE_ACCOUNT + " set " + COL_ACCID + " = ? , " + COL_ACCSECUREPASSWORD + " = ? , " + COL_ACCCREDITMAX + " = ? , " + COL_ACCCREDITCONSUMED + " = ? where " + COL_ACCID + " = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setLong(1, data.getAccountID());
			stmt.setString(2, data.getSecurePassword());
			stmt.setDouble(3, data.getCreditMaxLimit());
			stmt.setDouble(4, data.getCreditConsumed());
			stmt.setLong(5, data.getAccountID());
			
			stmt.executeQuery();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static boolean updateData(NetBankTransactionData data) {
		conn = connect();
		try {
			String sql = "update " + TABLE_TRANSACTIONS + " set " + COL_TRANID + " = ? , " + COL_TRANTONAME + " = ? , " + COL_TRANFROMNAME + " = ? , " + COL_TRANSAMMOUNT + " = ? where " + COL_TRANID + " = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setLong(1, data.getTransactionID());
			stmt.setString(2, data.getTransactionToName());
			stmt.setString(3, data.getTransactionFromName());
			stmt.setDouble(4, data.getTransactionAmount());
			stmt.setLong(5, data.getTransactionID());
			
			stmt.executeQuery();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static boolean deleteAccount(long id) {
		conn = connect();
		try{
			String sql = "delete from " + TABLE_ACCOUNT + " where " + COL_ACCID + " = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setLong(1, id);
			
			stmt.executeQuery();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public static boolean deleteTransaction(long id) {
		conn = connect();
		try{
			String sql = "delete from " + TABLE_TRANSACTIONS + " where " + COL_TRANID + " = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setLong(1, id);
			
			stmt.executeQuery();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public static NetBankAccountData queryAccount(long accountID) {
		conn = connect();
		try{
			String sql = "select * from " + TABLE_ACCOUNT + " where " + COL_ACCID + " = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setLong(1, accountID);
			
			ResultSet result = stmt.executeQuery();
			result.first();
			String accPass = result.getString(COL_ACCSECUREPASSWORD);
			double accMax = result.getDouble(COL_ACCCREDITMAX);
			double accCon = result.getDouble(COL_ACCCREDITCONSUMED);
			
			NetBankAccountData data = new NetBankAccountData(accountID, accPass, accMax, accCon);
			return data;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static NetBankAccountData[] queryAccount() {
		conn = connect();
		try{
			String sql = "select * from " + TABLE_ACCOUNT;
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			ResultSet result = stmt.executeQuery();
			long accID;
			String accPass;
			double accMax;
			double accCon;
			NetBankAccountData data;
			
			ArrayList<NetBankAccountData> list = new ArrayList<NetBankAccountData>();
			while(result.next()) {
				accID = result.getLong(COL_ACCID);
				accPass = result.getString(COL_ACCSECUREPASSWORD);
				accMax = result.getDouble(COL_ACCCREDITMAX);
				accCon = result.getDouble(COL_ACCCREDITCONSUMED);
				data = new NetBankAccountData(accID, accPass, accMax, accCon);
				
				list.add(data);
			}
			
			
			return list.toArray(new NetBankAccountData[list.size()]);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static NetBankTransactionData queryTransaction(long transactionID) {
		conn = connect();
		try{
			String sql = "select * from " + TABLE_TRANSACTIONS + " where " + COL_TRANID + " = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setLong(1, transactionID);
			
			ResultSet result = stmt.executeQuery();
			result.first();
			String tranTo = result.getString(COL_TRANTONAME);
			String tranFrom = result.getString(COL_TRANFROMNAME);
			double tranAmount = result.getDouble(COL_TRANSAMMOUNT);
			
			NetBankTransactionData data = new NetBankTransactionData(transactionID, tranTo, tranFrom, tranAmount);
			return data;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static NetBankTransactionData[] queryTransaction() {
		conn = connect();
		try{
			String sql = "select * from " + TABLE_TRANSACTIONS;
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			ResultSet result = stmt.executeQuery();
			long transID;
			String transTo;
			String transFrom;
			double transAmt;
			NetBankTransactionData data;
			
			ArrayList<NetBankTransactionData> list = new ArrayList<NetBankTransactionData>();
			while(result.next()) {
				transID = result.getLong(COL_TRANID);
				transTo = result.getString(COL_TRANTONAME);
				transFrom = result.getString(COL_TRANFROMNAME);
				transAmt = result.getDouble(COL_TRANSAMMOUNT);
				data = new NetBankTransactionData(transID, transTo, transFrom, transAmt);
				
				list.add(data);
			}
			
			
			return list.toArray(new NetBankTransactionData[list.size()]);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static void closeConnection() {
		try {
			if(conn != null && !conn.isClosed()) {
				conn.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
