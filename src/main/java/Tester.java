
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
		public static JButton btnSubmit;
		

		
		private String password;
		private String username;
		public static String assignto;
		public static String projectId;
		public static String bugName;
		public static String bugDesc;
		public static String bugPriority;
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
		
		
		public  void assignBugJframe()
		{
			
			final JComboBox projectselect ;
			JPanel panel = new JPanel(new GridBagLayout());
			JPanel panel2 = new JPanel(new GridBagLayout());
			JPanel panel3 = new JPanel(new GridBagLayout());
			JFrame frame1 = new JFrame("Assign Bug  ");
			frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

			frame1.setLayout(new BorderLayout());

			JLabel lblAssignto = new JLabel("Assign To :");
			lblAssignto.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblAssignto.setBounds(110, 141, 100, 14);
			frame1.add(lblAssignto);
			final JComboBox userselect = new JComboBox();
			panel3.setBounds(282, 140, 200, 20);
			


			JLabel lblpojectId = new JLabel("Project Id :\r\n");
			lblpojectId.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblpojectId.setBounds(110, 174, 100, 25);
			frame1.add(lblpojectId);
			projectselect = new JComboBox();
			panel.setBounds(282, 174, 200, 20);
		    


			JLabel lblBugNm = new JLabel("Bug Title :\r\n");
			lblBugNm.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblBugNm.setBounds(110, 200, 100, 25);
			frame1.add(lblBugNm);

			final JTextField BugNmField = new JTextField();
			BugNmField.setBounds(282, 200, 200, 20);
			frame1.add(BugNmField);
			BugNmField.setColumns(10);

			
			JLabel lblpriority = new JLabel("Bug Priority :\r\n");
			lblpriority.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblpriority.setBounds(110, 230, 100, 25);
			frame1.add(lblpriority);
			Vector v = new Vector();
		    v.add("CRITICAL"); 
			v.add("HIGH"); 
	        v.add("LOW"); 
	    
			final JComboBox priorityselect = new JComboBox(v);
			panel2.setBounds(282, 230, 200, 20);
			
			
			JLabel lblBugDec = new JLabel("Bug Description :\r\n");
			lblBugDec.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblBugDec.setBounds(110, 260, 200, 25);
			frame1.add(lblBugDec);

			final JTextField BugDecField = new JTextField();
			BugDecField.setBounds(282, 260, 200, 60);
			frame1.add(BugDecField);
			BugDecField.setColumns(100);
			
			
			
			 ResultSet rs = null;
				try {
					 connection = ConnectionFactory.getConnection();
						String sql = "Select projectID from project" ;
						
						PreparedStatement pstm = connection.prepareStatement(sql);
					
						
						rs = pstm.executeQuery();


							Vector v1 = new Vector();
				

				            while (rs.next()) {

				                String ids = rs.getString(1);

				                v1.add(ids);

				            	}

				projectselect.setModel(new DefaultComboBoxModel(v1));
			}
				catch (SQLException e) {
					System.out.println("SQLException in get() method");
					e.printStackTrace();
				} finally {
					DbUtil.close(rs);
					DbUtil.close(preparedStatment);
					DbUtil.close(connection);
				}
			
				 ResultSet rs2 = null;
					try {
						 connection = ConnectionFactory.getConnection();
							String sql = "Select username from bug_tracking_user where role = 'developer'" ;
							
							PreparedStatement pstm = connection.prepareStatement(sql);
						
							
							rs2 = pstm.executeQuery();


								Vector v2 = new Vector();
					

					            while (rs2.next()) {

					                String ids = rs.getString(1);

					                v2.add(ids);

					            	}

					userselect.setModel(new DefaultComboBoxModel(v2));
				}
					catch (SQLException e) {
						System.out.println("SQLException in get() method");
						e.printStackTrace();
					} finally {
						DbUtil.close(rs);
						DbUtil.close(preparedStatment);
						DbUtil.close(connection);
					}
				

			BugDecField.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
			btnSubmit.doClick();
			}
			});

			JButton btnSubmit = new JButton("Submit");
			final JLabel success = new JLabel("");
			success.setForeground(Color.GREEN);
			success.setBounds(282, 400, 200, 60);

			frame1.add(success);

			btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {


			assignto=(String) userselect.getSelectedItem();
			projectId=(String) projectselect.getSelectedItem();
			bugName=BugNmField.getText().toString().toLowerCase();
			bugDesc=BugDecField.getText().toString().toLowerCase();
			bugPriority = (String) priorityselect.getSelectedItem();
		//	AssigntoField.setText("");
		//	pojectIdField.setText("");
			BugNmField.setText("");
			BugDecField.setText("");
			
			assignBug(assignto, projectId, bugName, bugDesc,bugPriority);

			success.setText("Bug Successfully Added");
			}
			});

			btnSubmit.setBounds(282, 360, 89, 23);
			frame1.add(btnSubmit);

			panel.add(projectselect);
			
	        frame1.add(panel);
	        
	        panel2.add(priorityselect);
			
	        frame1.add(panel2);
			      frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

			       frame1.setLayout(new BorderLayout());
			       
			       frame1.setVisible(true);

			       frame1.setSize(600, 600);
			
		}
		protected void assignBug(String assignto2, String projectId, String bugName2, String bugDesc2, String bugPriority) {
			
			ResultSet rs = null;
			int ids;
			try {
				
				 connection = ConnectionFactory.getConnection();
					String sql = "select id from bug_tracking_user where username = ?"  ;

					PreparedStatement pstm = connection.prepareStatement(sql);
					
					pstm.setString(1, assignto2);
				
					rs = pstm.executeQuery();


		            while (rs.next()) {

		                ids = rs.getInt(1);


		            	}
			
			}
			catch (SQLException e) {
				System.out.println("SQLException in get() method");
				e.printStackTrace();
			} finally {
				
				DbUtil.close(preparedStatment);
				DbUtil.close(connection);
			}
			
			int rs1 ;
			try {
				 connection = ConnectionFactory.getConnection();
					String sql = "insert into bugs (bugTitle,bugDescription,bugPriority,bugProjectID,bugtesterID,bugdevID) values (?,?,?,?,?,?)"  ;

					PreparedStatement pstm = connection.prepareStatement(sql);
					String testerId = Integer.toString(this.userId);
					pstm.setString(1, bugName2);
					pstm.setString(2, bugDesc2);
					pstm.setString(3, bugPriority);
					pstm.setString(4, projectId);
					pstm.setString(5, testerId);
					pstm.setInt(6, ids);

					rs1 = pstm.executeUpdate();

			}
			catch (SQLException e) {
				System.out.println("SQLException in get() method");
				e.printStackTrace();
			} finally {

				DbUtil.close(preparedStatment);
				DbUtil.close(connection);
			}

			
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
		    success.setForeground(Color.GREEN);
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