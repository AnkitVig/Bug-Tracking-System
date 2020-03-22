
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.ImageIcon;
import java.awt.Toolkit;
//import Database;
enum Permissions
{
	AddProject,EditProject,DeleteProject,AddBug,EditBug,DeleteBug;
}

public class User extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String password;
	protected String username;
	private String role;
	private int userId;
	private String name;
	private String email;
	Permissions permissionList;
	
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Permissions getPermissionList() {
		return permissionList;
	}

	public void setPermissionList(Permissions permissionList) {
		this.permissionList = permissionList;
	}


	 private JButton btnLogin;
	 private JLabel error;

	
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
			
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
		GUI();
	}
	protected void GUI()
	{
		 final JPanel contentPane;
		 final JTextField usernameField;
		 final JTextField roleField;
		 final JPasswordField passwordField;
		 final String errorText="Invalid user name or password!";
		 final JLabel lblManagerLogin;

		 final JLabel label;
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
								Manager p=new Manager( userId);
								
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
							 Tester t=new Tester( userId);
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
								Developer d=new Developer( userId);
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