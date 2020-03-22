import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JComboBox;

import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.ImageIcon;
import java.awt.Toolkit;
//import Database;

public class User extends JFrame {

	private JPanel contentPane;
	private JTextField usernameField;
	private JTextField roleField;
	private JPasswordField passwordField;
	private String password,username, role;
	private JLabel error;
	private String errorText="Invalid user name or password!";
	private JLabel lblManagerLogin;
	JButton btnLogin;
	private JLabel label;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
//		if(!getMac().equals("90-48-9A-AC-21-17"))
	//	{
		//	JOptionPane.showMessageDialog(null,"Unknown Computer, Can not run!");
			//return;
//		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
//					Process process = Runtime.getRuntime().exec("E:\\xampp\\apache_start.bat");
	//				Process process2 = Runtime.getRuntime().exec("E:\\xampp\\mysql_start.bat");
					
					User frame = new User();
					frame.setIconImage(Toolkit.getDefaultToolkit().getImage("F:\\Working Directory\\final project with sql\\Bill\\logo.png"));
					frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public User()
	{
		//setIconImage(Toolkit.getDefaultToolkit().getImage(System.getProperty("user.dir")+"/logo.png"));
		GUI();
	}
	protected void GUI()
	{
		setTitle("User");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 531, 387);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		JLabel lblUserName = new JLabel("User name");
		lblUserName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblUserName.setBounds(154, 141, 91, 14);
		contentPane.add(lblUserName);
		
		usernameField = new JTextField();
		usernameField.setBounds(282, 140, 129, 20);
		contentPane.add(usernameField);
		usernameField.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password\r\n");
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPassword.setBounds(154, 174, 91, 14);
		contentPane.add(lblPassword);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(282, 173, 129, 20);
		contentPane.add(passwordField);
		
		JLabel lblRole = new JLabel("Role\r\n");
		lblRole.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblRole.setBounds(154, 200, 91, 14);
		contentPane.add(lblRole);		
		
		roleField = new JTextField();
		roleField.setBounds(282, 200, 129, 20);
		contentPane.add(roleField);
		
		passwordField.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
					btnLogin.doClick();
				}
		});
	//}
//}
	
		btnLogin = new JButton("Login");
	
		 btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				password=passwordField.getText().toString().toLowerCase();
				username=usernameField.getText().toString().toLowerCase();
				role=roleField.getText().toString().toLowerCase();
				passwordField.setText("");
				usernameField.setText("");
				roleField.setText("");
				
				if(password.equals("")||username.equals("")||role.equals(""))
					error.setText(errorText);
				else
				{
					error.setText("");
					if(role.equals("manager")) 
					{
						if(Database.varifyLogin(username,password,role))
							{
								error.setText("");
								Manager p=new Manager();
								
								p.setVisible(true);
								

							}
						else
							error.setText(errorText);
					}
					else if(  role.equals("tester"))
					{
						if(Database.varifyLogin(username,password,role))
						{
							error.setText("");
							 Tester t=new Tester();
							 t.setVisible(true);
							
						}
						else
							error.setText(errorText);
					}
					else if(  role.equals("developer"))
						{
							if(Database.varifyLogin(username,password,role))
							{
								error.setText("");
								Developer d=new Developer();
								d.setVisible(true);
								
							}
					else
						error.setText(errorText);
					}
					
				}
			}
		});
		btnLogin.setBounds(282, 250, 89, 23);
		contentPane.add(btnLogin);
		
		error = new JLabel("");
		error.setForeground(Color.RED);
		error.setBounds(104, 236, 220, 14);
		contentPane.add(error);
		
		lblManagerLogin = new JLabel("User Login");
		lblManagerLogin.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblManagerLogin.setBounds(204, 26, 167, 28);
		contentPane.add(lblManagerLogin);
		
		label = new JLabel("");
		label.setIcon(new ImageIcon("E:\\XAMPP\\htdocs\\logo.png"));
		label.setBounds(10, 11, 167, 91);
		contentPane.add(label);
		
		
	}
	public static String getMac()
	{
		InetAddress ip;
		String mc="";
		try {
			ip = InetAddress.getLocalHost();
			NetworkInterface network = NetworkInterface.getByInetAddress(ip);

			byte[] mac = network.getHardwareAddress();

			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < mac.length; i++) {
				sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
			}
		
			mc= sb.toString();

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mc;
		
	
	}
	
}