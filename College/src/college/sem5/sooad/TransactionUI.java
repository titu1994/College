package college.sem5.sooad;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.table.AbstractTableModel;

public class TransactionUI extends JFrame {
	
	private static final long serialVersionUID = 1653398564068135279L;
	
	private ArrayList<DisplayTransaction> transList;
	private TransactionDataModel tmodel;
	
	private JTable table;
	private JButton transAddButton, viewTransButton, alterAccPassButton, submitAdd;
	private JTextField transIDText,  transToText, transAmtText;
	private JLabel transIDLabel, transToLabel, transAmtLabel;

	public TransactionUI() {
		setExtendedState(JFrame.MAXIMIZED_BOTH); 
		transList = new ArrayList<DisplayTransaction>();
		initView();
	}
	
	private void initView() {

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		transAddButton = new JButton();
		viewTransButton = new JButton();
		alterAccPassButton = new JButton();
		submitAdd = new JButton();
		transIDText = new JTextField();	
		transToText = new JTextField();
		transAmtText = new JTextField();
		transIDLabel = new JLabel();
		transToLabel = new JLabel();
		transAmtLabel = new JLabel();
		
		transAddButton.setText("Add Transaction");
		transAddButton.setBounds(100, 0, 100, 50);
		transAddButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				transactionAddHandler();
			}
		});
		getContentPane().add(transAddButton);
		
		viewTransButton.setText("View all Transactions");
		viewTransButton.setBounds(250, 0, 100, 50);
		viewTransButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				viewAllTransactionsHandler();
			}
		});
		getContentPane().add(viewTransButton);
		
		alterAccPassButton.setText("Alter Account Password");
		alterAccPassButton.setBounds(400, 0, 100, 50);
		alterAccPassButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				alterAccountPasswordHandler();
			}
		});
		getContentPane().add(alterAccPassButton);
		
		submitAdd.setText("Submit");
		submitAdd.setBounds(200, 300, 100, 50);
		submitAdd.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ClientUI.client.addTransaction(Long.parseLong(transIDText.getText()), transToText.getText(), Double.parseDouble(transAmtText.getText()));
			}
		});
		
		transIDLabel.setBounds(0, 100, 100, 50);
		transIDLabel.setText("TransactionID");
		transToLabel.setBounds(125, 100, 100, 50);
		transToLabel.setText("To");
		transAmtLabel.setBounds(250, 100, 100, 50);
		transAmtLabel.setText("Amount");
		
		transIDText.setBounds(0, 200, 100, 50);
		transToText.setBounds(125, 200, 100, 50);
		transAmtText.setBounds(250, 200, 100, 50);
		
	}

	protected void alterAccountPasswordHandler() {
		clearScreen();
		
		
	}

	protected void viewAllTransactionsHandler() {
		clearScreen();
		tableSetup();
		ClientUI.client.viewAllTransactions();
	}

	boolean first = false;
	protected void transactionAddHandler() {
		clearScreen();
		System.out.println("Entered Transaction Handler");
		if(!first) {
			getContentPane().add(transIDLabel);
			getContentPane().add(transToLabel);
			getContentPane().add(transAmtLabel);
			getContentPane().add(transIDText);
			getContentPane().add(transToText);
			getContentPane().add(transAmtText);
			getContentPane().add(submitAdd);
			first = true;
		}
		
		transIDText.setVisible(true);
		transToText.setVisible(true);
		transAmtText.setVisible(true);
		
		transIDLabel.setVisible(true);
		transToLabel.setVisible(true);
		transAmtLabel.setVisible(true);
	}
	
	public void clearScreen() {
		if(table != null)
			table.setVisible(false);
		transIDText.setVisible(false);
		transToText.setVisible(false);
		transAmtText.setVisible(false);
		
		transIDLabel.setVisible(false);
		transToLabel.setVisible(false);
		transAmtLabel.setVisible(false);
	}

	private void tableSetup() {		
		
		if(table == null) {
			DisplayTransaction d = new DisplayTransaction();
			d.transactionID =  "Transaction ID";
			d.userID = "User ID";
			d.toName = "Send To";
			d.amount = "Amount";
			transList.add(d);
			
			tmodel = new TransactionDataModel();
			table = new JTable(tmodel);
			table.setBounds(300, 100, 800, 800);
			getContentPane().add(table);
		}
		else {
			tmodel.fireTableDataChanged();
		}
		table.setVisible(true);
	}
	
	public void clientTransactionAdded(boolean succesful) {
		
	}

	public void clientAllTransactionData(NetBankTransactionData[] datas) {
		transList.clear();
		
		DisplayTransaction d = new DisplayTransaction();
		d.transactionID =  "Transaction ID";
		d.userID = "User ID";
		d.toName = "Send To";
		d.amount = "Amount";
		transList.add(d);
		
		for(NetBankTransactionData data : datas) {
			d = new DisplayTransaction();
			d.transactionID =  "" + data.transactionID;
			d.userID = "" + data.userID;
			d.toName = data.getTransactionToName();
			d.amount = "" + data.transactionAmount;
			transList.add(d);
		}
		tableSetup();
	}

	public void clientOldPasswordMatchesNewPassword() {
		
	}

	public void clientOldPasswordNotEnteredCorrectly() {
		
	}
	
	
	public void clientNewPasswordEmpty() {
		
	}

	public void clientOldPasswordNotFound() {
		
	}
	
	private class DisplayTransaction {
		String transactionID;
		String userID;
		String toName;
		String amount;
	}
	
	private class TransactionDataModel extends AbstractTableModel {

		private static final long serialVersionUID = -1962279468479145799L;

		@Override
		public int getColumnCount() {
			return 4;
		}

		@Override
		public int getRowCount() {
			return transList.size();
		}

		@Override
		public Object getValueAt(int row, int col) {
			if(col == 0) 
				return transList.get(row).transactionID;
			else if(col == 1) 
				return transList.get(row).userID; 
			else if(col == 2) 
				return transList.get(row).toName; 
			else if(col == 3) 
				return transList.get(row).amount;
			
			return col; 
		}

		@Override
		public boolean isCellEditable(int rowIndex, int columnIndex) {
			return false;
		}
	}

}
