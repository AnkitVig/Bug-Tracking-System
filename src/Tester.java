
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import util.*;
import javax.swing.JMenu;

	

	public class Tester extends User implements ActionListener {
		/**
		 * 
		 */
		private Connection connection;
		private static final long serialVersionUID = 9104811318735213684L;

		JButton viewBug, assignBug, deleteBug;
		JMenu mnExport;
		private static String[] columnNames = {"Bug ID", "Bug Title", "Bug Description", "Bug Priority", "Bug Status", "Bug Due Date"};
		
		ArrayList<JPanel> panels=new ArrayList<JPanel>();
		int cPanel=0;
	

		
		private String password;
		private String username;
		private String role;
		private int userId;
		private String name;
		private String email;
		private String developer_name;
		private String developer_email;
		private String Bug_name;
		private String Bug_Description;
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
		/**
		 * Create the frame.
		 */
		public Tester() {
			super();
			
		
		}
		public Tester(int user_id) {
			setUserId(user_id);
	
	}
		@Override
		protected void GUI()
		{
			//setIconImage(Toolkit.getDefaultToolkit().getImage("F:\\Working Directory\\fianl project with sql\\Bill\\logo.png"));
			setTitle("Tester Panel");
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			setBounds(100, 100, 840, 619);
			
			JMenuBar menuBar = new JMenuBar();
			setJMenuBar(menuBar);
		
			
			mnExport = new JMenu("Account");
			menuBar.add(mnExport);
			JMenuItem logout = new JMenuItem("Logout");
			mnExport.add(logout);
			logout.addActionListener(this);
			viewBug = new JButton("View Bug");
			assignBug = new JButton("Assign Bug");
			deleteBug = new JButton("Delete Bug");
			
				viewBug.setBounds(150, 150, 150, 20);

				viewBug.addActionListener(this);
				assignBug.setBounds(150, 200, 150, 20);

				assignBug.addActionListener(this);
				deleteBug.setBounds(150, 250, 150, 20);

				deleteBug.addActionListener(this);
		        add(viewBug);
		        add(assignBug);
		        add(deleteBug);
			getContentPane().setLayout(new BorderLayout(0, 0));
			
		//	panels.add(new addBug());
		/*	panels.add(new updateProduct());
			panels.add(new deleteProduct());
			panels.add(new addCashier());
			panels.add(new deleteCashier());
			panels.add(new showStock());
			panels.add(new searchProduct());
			panels.add(new searchCashier());
			panels.add(new Sale());
			getContentPane().add(panels.get(0));
		*/	
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			System.out.println("Selected: " + e.getActionCommand());   
			if(e.getSource() == viewBug)
			{
				viewBug();
			}
			
			else if(e.getSource() == assignBug)
			{
				assignBug();
			}
			else if(e.getSource() == deleteBug)
			{
				
			}

			else if(e.getActionCommand().equals("Logout"))
			{
				this.dispose();
			}
		}
		public void viewBug()
		{
			 
				
		}
		public static void viewBug(String bugID)
		{
		}
		
		
		public static void assignBug()
		{
			
			final JPanel contentPane = null ;
			final JTextField developer_namefield;
			
			 JFrame frame1 = new JFrame("Select bug ID to View Bug");

		       frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		        frame1.setLayout(new BorderLayout());
		        
		        frame1.setVisible(true);

		        frame1.setSize(400, 300);
		        
				
				JLabel lblUserName = new JLabel("Developer Name");
				lblUserName.setFont(new Font("Tahoma", Font.PLAIN, 14));
				lblUserName.setBounds(154, 141, 91, 14);
				contentPane.add(lblUserName);
				
				developer_namefield = new JTextField();
				developer_namefield.setBounds(282, 140, 129, 20);
				contentPane.add(developer_namefield);
				developer_namefield.setColumns(10);
		}
		public static void closeBug()
		{
			 JFrame frame1 = new JFrame("Select bug ID to View Bug");

		       frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		        frame1.setLayout(new BorderLayout());
		        
		        frame1.setVisible(true);

		        frame1.setSize(400, 300);
		}
		public static void notifyUser(int userID)
		{
		
		}
	}
