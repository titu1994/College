package college.sem5.sooad;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class LogIn extends JFrame {
	private JLabel username, password;
	private JTextField user;
	private JPasswordField pass;
	private JButton authenticate;
	
	private long id;
	private String pas;
	
	private static final long serialVersionUID = -6234447662078402196L;

	public LogIn() {
		setExtendedState(JFrame.MAXIMIZED_BOTH); 
		initView();
	}

	private void initView() {

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);

		username = new JLabel();
		password = new JLabel();
		user = new JTextField();
		pass = new JPasswordField();
		authenticate = new JButton();

		username.setText("User-ID");
		username.setBounds(30, 0, 100, 100);
		password.setText("Password");
		password.setBounds(30, 30, 100, 100);

		getContentPane().add(username);
		getContentPane().add(password);

		user.setBounds(150, 35, 100, 30);
		pass.setBounds(150, 65, 100, 30);

		getContentPane().add(user);
		getContentPane().add(pass);

		authenticate.setBounds(75, 100, 100, 40);
		authenticate.setText("Submit");

		authenticate.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				id = Long.parseLong(user.getText().toString());
				pas = new String(pass.getPassword());
				
				ClientUI.startClient(id, pas);
			}
		});

		getContentPane().add(authenticate);
	}
	
	private Object options[] = {"Add Account", "Cancel"};
	public void logInFailed() {
		int option = JOptionPane.showOptionDialog(this, "Account does not exist. Do you want to create a new account?", "Account Not Found", 
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
		
		if(option == 0) {
			ClientUI.client.createNewAccount(id, pas);
		}
		else {
			ClientUI.client.cancelConnection();
		}
	}
	
	
}
