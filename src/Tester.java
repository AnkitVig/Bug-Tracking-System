
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagLayout;
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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import util.*;
import javax.swing.JMenu;

	

	public class Tester extends User implements ActionListener {
		/**
		 * 
		 */
		private Connection connection;
		private PreparedStatement preparedStatment;
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
			

		}
		@Override
		public void actionPerformed(ActionEvent e) {
			
			System.out.println("Selected: " + e.getActionCommand());   
			if(e.getSource() == viewBug)
			{
				viewBugJframe();
			}
			
			else if(e.getSource() == assignBug)
			{
				assignBugJframe();
			}
			else if(e.getSource() == deleteBug)
			{
				closeBugJframe();
			}

			else if(e.getActionCommand().equals("Logout"))
			{
				this.dispose();
			}
		}
		public void viewBugJframe()
		{
			JFrame frame1 = new JFrame("View Bug");
			JPanel panel = new JPanel(new GridBagLayout());
			frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

			frame1.setLayout(new BorderLayout());

			JLabel title = new JLabel("Select Bug assigned to you from the list");

			title.setForeground(Color.red);

			title.setFont(new Font("Tahoma", Font.PLAIN, 25));

			JLabel select = new JLabel("Select Bug");
			select.setFont(new Font("Tahoma", Font.PLAIN, 20));
			JButton search = new JButton("Search");
			final JComboBox bugselect = new JComboBox();

			title.setBounds(20, 150, 500, 40);

			select.setBounds(50, 210, 500, 40);

			search.setBounds(50, 280, 150, 20);

			search.addActionListener(this);

			bugselect.setBounds(50, 150, 75, 20);
			// setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

			frame1.add(title);

			frame1.add(select);
			frame1.add(search);
			panel.add(bugselect);
	        frame1.add(panel);
			

			 ResultSet rs = null;
			try {
				 connection = ConnectionFactory.getConnection();
					String sql = "Select bugId from bugs where  bugtesterID ="+this.userId  ;

					PreparedStatement pstm = connection.prepareStatement(sql);
					int i = 0;
					rs = pstm.executeQuery();

//			            Class.forName("oracle.jdbc.driver.OracleDriver");

//			            con = DriverManager.getConnection("jdbc:oracle:thin:@mcndesktop07:1521:xe", "sandeep", "welcome");

//			            st = con.createStatement();

//			            rs = st.executeQuery("select uname from emp");

						Vector v = new Vector();
			

			            while (rs.next()) {

			                String ids = rs.getString(1);

			                v.add(ids);

			            	}

			bugselect.setModel(new DefaultComboBoxModel(v));
		}
			catch (SQLException e) {
				System.out.println("SQLException in get() method");
				e.printStackTrace();
			} finally {
				DbUtil.close(rs);
				DbUtil.close(preparedStatment);
				DbUtil.close(connection);
			}

			search.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {

					String bugID = (String) bugselect.getSelectedItem();

					viewBug(bugID);

				}
			});

			frame1.setVisible(true);

			frame1.setSize(700, 500);
				
		}
		public void viewBug(String bugID)
		{

			JFrame frame1 = new JFrame("Bug Search Result");

			frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

			frame1.setLayout(new BorderLayout());

			// TableModel tm = new TableModel();

			DefaultTableModel model = new DefaultTableModel();

			model.setColumnIdentifiers(columnNames);

			// DefaultTableModel model = new DefaultTableModel(tm.getData1(),
			// tm.getColumnNames());

			// table = new JTable(model);

			JTable table = new JTable();

			table.setModel(model);

			table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

			table.setFillsViewportHeight(true);

			JScrollPane scroll = new JScrollPane(table);

			scroll.setHorizontalScrollBarPolicy(

					JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

			scroll.setVerticalScrollBarPolicy(

					JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

			// String textvalue = textbox.getText();

			String bugId = "";

			String bugTitle = "";

			String bugDescription = "";

			String bugPriority = "";

			String bugStatus = "";

			String bugDueDate = "";
			
			ResultSet rs = null;
			try {
			
				
					connection = ConnectionFactory.getConnection();
					String sql = "Select a.bugId, a.bugTitle, a.bugDescription,a.bugPriority"
							+ ",a.bugStatus ,a.bugDueDate from bugs a where a.bugId ='"+bugID +"' and bugtesterID ="+this.userId ;

					PreparedStatement pstm = connection.prepareStatement(sql);
					int i = 0;
					rs = pstm.executeQuery();

					if (rs.next()) {
						 bugId = rs.getString("bugId");
						 bugTitle = rs.getString("bugTitle");
						 bugDescription = rs.getString("bugDescription");
						 bugPriority = rs.getString("bugPriority");
						 bugStatus = rs.getString("bugStatus");
						 bugDueDate = rs.getString("bugDueDate");

						
		                model.addRow(new Object[]{bugId, bugTitle, bugDescription, bugPriority, bugStatus, bugDueDate});

		                i++;


					}
			



				if (i < 1) {

					JOptionPane.showMessageDialog(null, "No Record Found", "Error", JOptionPane.ERROR_MESSAGE);

				}

				if (i == 1) {

					System.out.println(i + " Record Found");

				} else {

					System.out.println(i + " Records Found");

				}

			} catch (SQLException e) {
				System.out.println("SQLException in get() method");
				e.printStackTrace();
			} finally {
				DbUtil.close(rs);
				DbUtil.close(preparedStatment);
				DbUtil.close(connection);
			}

			frame1.add(scroll);

			frame1.setVisible(true);

			frame1.setSize(700, 500);
		}
		
		
		public static void assignBugJframe()
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
		public  void closeBugJframe()
		{
			JFrame frame1 = new JFrame("Solve Bug");
			JPanel panel = new JPanel(new GridBagLayout());
			
			frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

			frame1.setLayout(new BorderLayout());
			final JPanel contentPane;
			final JComboBox bugselect ;
			
			JLabel title = new JLabel("Select Bug assigned to you to set status 'Close'");

			title.setForeground(Color.red);

			title.setFont(new Font("Tahoma", Font.PLAIN, 20));

		
			JButton search = new JButton("Change status to 'Close'");
		

			title.setBounds(20, 150, 500, 40);

			search.setBounds(50, 280, 300, 20);

			search.addActionListener(this);


			// setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

			frame1.add(title);
			frame1.add(search);
			JLabel select = new JLabel("Select Bug");
			select.setFont(new Font("Tahoma", Font.PLAIN, 15));
			select.setBounds(50, 210, 500, 40);
			bugselect = new JComboBox();
		    bugselect.setBounds(154, 150, 129, 20);
		    final JLabel success = new JLabel("");
		    success.setForeground(Color.RED);
		    success.setBounds(110, 310, 220, 14);
			frame1.add(success);

			 ResultSet rs = null;
				try {
					 connection = ConnectionFactory.getConnection();
						String sql = "Select bugId from bugs where  bugtesterID = "+ this.userId  ;
						
						PreparedStatement pstm = connection.prepareStatement(sql);
					
						
						rs = pstm.executeQuery();


							Vector v = new Vector();
				

				            while (rs.next()) {

				                String ids = rs.getString(1);

				                v.add(ids);

				            	}

				bugselect.setModel(new DefaultComboBoxModel(v));
			}
				catch (SQLException e) {
					System.out.println("SQLException in get() method");
					e.printStackTrace();
				} finally {
					DbUtil.close(rs);
					DbUtil.close(preparedStatment);
					DbUtil.close(connection);
				}
			

			panel.add(bugselect);
			frame1.add(select);
	        frame1.add(panel);
	        
			frame1.setVisible(true);

			frame1.setSize(700,500);
			search.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {

					String bugID = (String) bugselect.getSelectedItem();
					
					closeBug(bugID);
					success.setText("Successfully Closed " + bugID);

				}
			
				
			});
		}
		
		private void closeBug(String bugID) {
			int rs ;
			try {
				 connection = ConnectionFactory.getConnection();
					String sql = "Update bugs set bugStatus = 'Close' where  bugID = ?"  ;

					PreparedStatement pstm = connection.prepareStatement(sql);
					pstm.setString(1, bugID);
				
					rs = pstm.executeUpdate();
			
			}
			catch (SQLException e) {
				System.out.println("SQLException in get() method");
				e.printStackTrace();
			} finally {
				
				DbUtil.close(preparedStatment);
				DbUtil.close(connection);
			}
			
		}

	
		public  void notifyUser(int userID)
		{
		
		}
	}
