package com.carleton;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.sql.Date;
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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.carleton.util.ConnectionFactory;
import com.carleton.util.DbUtil;
import com.carleton.User;


import javax.swing.JMenu;

public class Tester extends User implements ActionListener {
	/**
	 * 
	 */
	/*@ public 
    @ invariant this.userId > 0
    @
    @ && (\forall int userId; userId > 0 ; userId != this.userId)
	@ && role.equals("Tester") == true;
	@
	@*/
	
	private Connection connection;
	private PreparedStatement preparedStatment;
	private static final long serialVersionUID = 9104811318735213684L;

	JButton viewBug, assignBug, deleteBug;

	JMenu mnExport;
	private static String[] columnNames = { "Bug ID", "Bug Title", "Bug Description", "Bug Priority", "Bug Status",
			"Bug Due Date" };

	ArrayList<JPanel> panels = new ArrayList<JPanel>();
	int cPanel = 0;
	public static JButton btnSubmit;

	private String password;
	private String username;
	public static String assignto;
	public static String projectId;
	public static String bugName;
	public static String bugDesc;
	public static String bugPriority;
	public static String bugDate;
	public String role;
	public int userId;
	private String name;
	public String email;
	public int emailSuccess = 0;
	private String developer_name;
	private String developer_email;
	private String Bug_name;
	private String Bug_Description;
	Permissions permissionList;
	public Session mailSession;

	public int bugId,bugtesterID,bugdevID;
	public String bugStatus, bugTitle, bugDescription,bugDueDate;

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
	protected void GUI() {

		setTitle("Tester Panel");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
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
		if (e.getSource() == viewBug) {
			viewBugJframe();
		}

		else if (e.getSource() == assignBug) {
			assignBugJframe();
		} else if (e.getSource() == deleteBug) {
			closeBugJframe();
		}

		else if (e.getActionCommand().equals("Logout")) {
			this.dispose();
		}
	}

		
	/**
     *  \fn public void viewBug(String bugID)
     *  
     *  @param [in] bugID String value holding the bug ID of the bug to be viewed.
     *  
     */

	/*@
	 @ public normal_behavior
	 @ 
	 @ requires bugId > 0 && bugStatus.equals( "Closed") == false && bugdevID == this.userId;
	 @ 
	 @ ensures bugTitle != null && bugDescription != null && bugPriority != null
	 @ && bugStatus != null && bugDueDate != null ;
	 @ 
	 @
	 @*/
	public void viewBug(int bugID) {

		JFrame frame1 = new JFrame("Bug Search Result");

		frame1.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		frame1.setLayout(new BorderLayout());

		DefaultTableModel model = new DefaultTableModel();

		model.setColumnIdentifiers(columnNames);

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
					+ ",a.bugStatus ,a.bugDueDate from bugs a where  "
					+ " a.bugId = ?" ;

			PreparedStatement pstm = connection.prepareStatement(sql);
			int i = 0;
			pstm.setInt(1, bugID);
			rs = pstm.executeQuery();

			if (rs.next()) {
				bugId = rs.getString("bugId");
				bugTitle = rs.getString("bugTitle");
				bugDescription = rs.getString("bugDescription");
				bugPriority = rs.getString("bugPriority");
				bugStatus = rs.getString("bugStatus");
				bugDueDate = rs.getString("bugDueDate");

				model.addRow(new Object[] { bugId, bugTitle, bugDescription, bugPriority, bugStatus, bugDueDate });

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

		
	/**
     *  \fn protected void assignBug(String assignto2, String projectId, String bugName2, String bugDesc2, String bugPriority,
			String bugDateField)
     *  
     *  @param [in] assignto2 String value holding the user ID of the user to whom bug is to be assigned.
     *  
     *  @param [in] projectId String value holding the project ID of project to which bug belongs.
     *  
     *  @param [in] bugName2 String value holding the name of the bug.
     *  
     *  @param [in] bugDesc2 String value holding the bug description.
     *  
     *  @param [in] bugPriority String value holding bug priority.
     *  
     *  @param [in] bugDateField String value holding the bug due date.
     *  
     */

	/*@
	 @ public normal_behavior
	 @ 
	 @ requires assignto2 != null && projectId != null && bugName2 != null &&
	 @ bugDesc2 != null && bugPriority != null && bugDateField != null &&
	 @ bugtesterID == this.userId && bugDateField.matches("\\d{4}-\\d{2}-\\d{2}");
	 @ 
	 @ ensures bugId > 0 && bugStatus.equals ("Open") == true;
	 @ 
	 @
	 @*/

	protected void assignBug(String assignto2, String projectId, String bugName2, String bugDesc2, String bugPriority,
			String bugDateField) {

		Date date = Date.valueOf(bugDateField);

		ResultSet rs = null;
		int ids = 0;
		try {

			connection = ConnectionFactory.getConnection();
			String sql = "select id from bug_tracking_user where username = ?";

			PreparedStatement pstm = connection.prepareStatement(sql);

			pstm.setString(1, assignto2);

			rs = pstm.executeQuery();

			while (rs.next()) {

				ids = rs.getInt(1);

			}

		} catch (SQLException e) {
			System.out.println("SQLException in get() method");
			e.printStackTrace();
		} finally {
			DbUtil.close(rs);
			DbUtil.close(preparedStatment);
			DbUtil.close(connection);
		}

		int rs1;
		try {
			connection = ConnectionFactory.getConnection();
			String sql = "insert into bugs (bugTitle,bugDescription,bugPriority,bugProjectID,bugtesterID,bugdevID,bugDueDate) values (?,?,?,?,?,?,?)";

			PreparedStatement pstm = connection.prepareStatement(sql);
			String testerId = Integer.toString(this.userId);
			pstm.setString(1, bugName2);
			pstm.setString(2, bugDesc2);
			pstm.setString(3, bugPriority);
			pstm.setString(4, projectId);
			pstm.setString(5, testerId);
			pstm.setInt(6, ids);

			pstm.setString(7, bugDateField);

			rs1 = pstm.executeUpdate();

		} catch (SQLException e) {
			System.out.println("SQLException in get() method");
			e.printStackTrace();
		} finally {

			DbUtil.close(preparedStatment);
			DbUtil.close(connection);
		}
		try {
			notifyUser(bugName2, assignto2);
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


	
	/**
     *  \fn private void closeBug(String bugID)
     *  
     *  @param [in] bugID String value holding the bug ID of the bug to be closed.
     *  
     */

	/*@
	 @ public normal_behavior
	 @ 
	 @ requires bugId > 0 && bugStatus.equals("Closed") == false;
	 @ 
	 @ ensures bugStatus.equals("Closed") == true ;
	 @ 
	 @
	 @*/
	private void closeBug(int bugID) {
		int rs;
		try {
			connection = ConnectionFactory.getConnection();
			String sql = "Update bugs set bugStatus = 'Closed' where  bugID = ?";

			PreparedStatement pstm = connection.prepareStatement(sql);
			pstm.setInt(1, bugID);

			rs = pstm.executeUpdate();

		} catch (SQLException e) {
			System.out.println("SQLException in get() method");
			e.printStackTrace();
		} finally {

			DbUtil.close(preparedStatment);
			DbUtil.close(connection);
		}

	}
	
	/**
     *  \fn public void notifyUser(String bugTitle, String username)
     *  
     *  @param [in] bugTitle String value holding the bug title.
     *  
     *  @param [in] username String value holding username of user who has to be notified.
     *  
     */

	/*@
	 @ public normal_behavior
	 @ 
	 @ requires email.equals(null) ==false && username.equals(null) ==false;
	 @ 
	 @ ensures emailSuccess == 1;
	 @ 
	 @ also
	 @ 
	 @ exceptional_behavior
	 @
	 @ requires mailSession == null;
	 @ 
	 @ signals (AddressException e) emailSuccess == 0;
	 @ signals (MessagingException e) emailSuccess == 0;
	 @
	 @*/
	public void notifyUser(String bugTitle, String username) throws AddressException, MessagingException {
		
		String userEmail = null;
		ResultSet rs = null;
		try {
			connection = ConnectionFactory.getConnection();
			String sql = "Select email from bug_tracking_user where  username = ?";

			PreparedStatement pstm = connection.prepareStatement(sql);
			pstm.setString(1, username);

			rs = pstm.executeQuery();

			while (rs.next()) {

				userEmail = rs.getString(1);

			}

		} catch (SQLException e) {
			System.out.println("SQLException in get() method");
			e.printStackTrace();
		} finally {
			DbUtil.close(rs);
			DbUtil.close(preparedStatment);
			DbUtil.close(connection);
		}

		Properties emailProperties;
		
		MimeMessage emailMessage;

		String emailPort = "587";// gmail's smtp port

		emailProperties = System.getProperties();
		emailProperties.put("mail.smtp.port", emailPort);
		emailProperties.put("mail.smtp.auth", "true");
		emailProperties.put("mail.smtp.starttls.enable", "true");

		String[] toEmails = { userEmail };
		String emailSubject = "Email from bug tracker";
		String emailBody = "A bug with name '" + bugTitle + "' has been created and assigned to you for your review.";

		mailSession = Session.getDefaultInstance(emailProperties, null);
		emailMessage = new MimeMessage(mailSession);

		for (int i = 0; i < toEmails.length; i++) {
			emailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmails[i]));
		}

		emailMessage.setSubject(emailSubject);
		emailMessage.setContent(emailBody, "text/html");// for a html email
		// emailMessage.setText(emailBody);// for a text email

		String emailHost = "smtp.gmail.com";
		String fromUser = "bugtrackerdbc@gmail.com";// just the id alone without @gmail.com
		String fromUserEmailPassword = "abcd98825";

		Transport transport = mailSession.getTransport("smtp");

		transport.connect(emailHost, fromUser, fromUserEmailPassword);
		transport.sendMessage(emailMessage, emailMessage.getAllRecipients());
		transport.close();
		System.out.println("Email sent successfully.");
		emailSuccess = 1;
	}
	public void closeBugJframe() {
		JFrame frame1 = new JFrame("Solve Bug");
		JPanel panel = new JPanel(new GridBagLayout());

		frame1.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		frame1.setLayout(new BorderLayout());
		final JPanel contentPane;
		final JComboBox bugselect;

		JLabel title = new JLabel("Select Bug assigned to you to set status 'Closed'");

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
			String sql = "Select bugId,bugTitle from bugs where bugStatus != 'CLOSED' and bugStatus != 'closed' and bugStatus != 'Closed' and bugtesterID = " + this.userId;

			PreparedStatement pstm = connection.prepareStatement(sql);

			rs = pstm.executeQuery();

			Vector v = new Vector();

			while (rs.next()) {

				String ids = rs.getString(1);
				String bugTitle = rs.getString("bugTitle");

				v.add(ids.concat("-").concat(bugTitle));

			}

			bugselect.setModel(new DefaultComboBoxModel(v));
		} catch (SQLException e) {
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

		frame1.setSize(700, 500);
		search.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				String bugID = (String) bugselect.getSelectedItem();
				String[] arrOfStr = bugID.split("-", 2); 
				closeBug(Integer.parseInt(arrOfStr[0]));
				success.setText("Successfully Closed " + bugID);

			}

		});
	}
	public void viewBugJframe() {
		JFrame frame1 = new JFrame("View Bug");
		JPanel panel = new JPanel(new GridBagLayout());
		frame1.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		frame1.setLayout(new BorderLayout());

		JLabel title = new JLabel("Select Bug assigned to you from the list");

		// title.setForeground(Color.red);

		title.setFont(new Font("Tahoma", Font.PLAIN, 25));

		JLabel select = new JLabel("Select Bug :");
		select.setFont(new Font("Tahoma", Font.PLAIN, 18));
		select.setBounds(110, 225, 100, 25);

		JButton search = new JButton("Search");
		final JComboBox bugselect = new JComboBox();

		title.setBounds(80, 91, 2000, 35);

		search.setBounds(282, 300, 89, 23);

		search.addActionListener(this);

		panel.setBounds(282, 141, 200, 20);
		// setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		frame1.add(title);

		frame1.add(select);
		frame1.add(search);
		panel.add(bugselect);
		frame1.add(panel);

		ResultSet rs = null;
		try {
			connection = ConnectionFactory.getConnection();

			String sql = "Select bugId,bugTitle from bugs where  bugStatus != 'CLOSED' and  bugStatus != 'closed' and bugStatus != 'Closed' and bugtesterID =" + this.userId;

			PreparedStatement pstm = connection.prepareStatement(sql);
			int i = 0;
			rs = pstm.executeQuery();

			Vector v = new Vector();

			while (rs.next()) {

				String bugId = rs.getString("bugId");
				String bugTitle = rs.getString("bugTitle");

				v.add(bugId.concat("-").concat(bugTitle));

			}

			bugselect.setModel(new DefaultComboBoxModel(v));
		} catch (SQLException e) {
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
				String[] arrOfStr = bugID.split("-", 2); 
				
				viewBug(Integer.parseInt(arrOfStr[0]));

			}
		});

		frame1.setVisible(true);

		frame1.setSize(700, 500);

	}
	public void assignBugJframe() {

		final JComboBox projectselect;
		JPanel panel = new JPanel(new GridBagLayout());
		JPanel panel2 = new JPanel(new GridBagLayout());
		JPanel panel3 = new JPanel(new GridBagLayout());
		JFrame frame1 = new JFrame("Assign Bug  ");
		frame1.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

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

		JLabel lblBugDate = new JLabel("Bug Due Date :\r\n");
		lblBugDate.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblBugDate.setBounds(110, 225, 150, 25);
		frame1.add(lblBugDate);

		final JTextField BugDateField = new JTextField();
		BugDateField.setBounds(282, 225, 200, 20);
		frame1.add(BugDateField);
		BugDateField.setColumns(10);
		JLabel lblBugDateformat = new JLabel("Only (yyyy-mm-dd) Date format accepted\r\n");
		lblBugDateformat.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblBugDateformat.setBounds(500, 225, 300, 25);
		frame1.add(lblBugDateformat);

		JLabel lblpriority = new JLabel("Bug Priority :\r\n");
		lblpriority.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblpriority.setBounds(110, 250, 100, 25);
		frame1.add(lblpriority);
		Vector v = new Vector();
		v.add("CRITICAL");
		v.add("HIGH");
		v.add("LOW");

		final JComboBox priorityselect = new JComboBox(v);
		panel2.setBounds(282, 250, 200, 20);

		JLabel lblBugDec = new JLabel("Bug Description :\r\n");
		lblBugDec.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblBugDec.setBounds(110, 280, 200, 25);
		frame1.add(lblBugDec);

		final JTextField BugDecField = new JTextField();
		BugDecField.setBounds(282, 280, 200, 60);
		frame1.add(BugDecField);
		BugDecField.setColumns(100);

		ResultSet rs = null;
		try {
			connection = ConnectionFactory.getConnection();
			String sql = "Select projectID from project";

			PreparedStatement pstm = connection.prepareStatement(sql);

			rs = pstm.executeQuery();

			Vector v1 = new Vector();

			while (rs.next()) {

				String ids = rs.getString(1);

				v1.add(ids);

			}

			projectselect.setModel(new DefaultComboBoxModel(v1));
		} catch (SQLException e) {
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
			String sql = "Select username from bug_tracking_user where role = 'developer'";

			PreparedStatement pstm = connection.prepareStatement(sql);

			rs2 = pstm.executeQuery();

			Vector v2 = new Vector();

			while (rs2.next()) {

				String ids = rs2.getString(1);

				v2.add(ids);

			}

			userselect.setModel(new DefaultComboBoxModel(v2));
		} catch (SQLException e) {
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

		success.setBounds(282, 400, 200, 60);

		frame1.add(success);

		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				assignto = (String) userselect.getSelectedItem();
				bugDate = BugDateField.getText().toString().toLowerCase();
				projectId = (String) projectselect.getSelectedItem();
				bugName = BugNmField.getText().toString().toLowerCase();
				bugDesc = BugDecField.getText().toString().toLowerCase();
				bugPriority = (String) priorityselect.getSelectedItem();
				if (bugDate.equals("") || bugName.equals("") || bugDesc.equals("")) {
					success.setForeground(Color.RED);
					success.setText("Empty Fields are not Allowed!!");
				} else {
					if (bugDate.matches("\\d{4}-\\d{2}-\\d{2}")) {

						// pojectIdField.setText("");
						BugNmField.setText("");
						BugDecField.setText("");
						BugDateField.setText("");

						assignBug(assignto, projectId, bugName, bugDesc, bugPriority, bugDate);
						success.setForeground(Color.GREEN);
						success.setText("Bug Successfully Added");
					} else {
						success.setForeground(Color.RED);
						success.setText("WRONG DATE FORMAT!!");
					}
				}
			}
		});

		btnSubmit.setBounds(282, 360, 89, 23);
		frame1.add(btnSubmit);

		panel.add(projectselect);

		frame1.add(panel);

		panel2.add(priorityselect);

		frame1.add(panel2);
		panel3.add(userselect);

		frame1.add(panel3);
		frame1.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		frame1.setLayout(new BorderLayout());

		frame1.setVisible(true);

		frame1.setSize(700, 900);

	}

}
