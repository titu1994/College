package college.sem5.sooad;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class ClientUI extends JFrame {
	private static final long serialVersionUID = 12321L;
	private static ClientUI ui;

	private static NetBankServer server;
	private static NetBankClient client;

	public static void main(String[] args) {
		server = new NetBankServer();
		server.startCommunication();

		ui = new ClientUI();
		//ui.setSize(ui.getMaximumSize().width, ui.getMaximumSize().height);


		ui.setSize(600, 400);
		ui.setVisible(true);
	}

	public ClientUI() {
		initView();
	}
	private JLabel username, password;
	private JTextField user;
	private JPasswordField pass;
	private JButton authenticate;

	private void initView() {

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);

		username = new JLabel();
		password = new JLabel();
		user = new JTextField();
		pass = new JPasswordField();
		authenticate = new JButton();

		username.setText("Username");
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

				long id = Long.parseLong(user.getText().toString());
				String p = new String(pass.getPassword());
				client = new NetBankClient(id, p);
				client.startCommunication();

			}
		});

		getContentPane().add(authenticate);

	}

}
