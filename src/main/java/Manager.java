package main.java;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
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
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JMenu;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import main.java.util.*;

public class Manager extends User implements ActionListener {
	/**
	 * 
	 */
	/*
	 * @ invariant userId != null
	 * 
	 * @ && (\forall Integer userId; userId != null ; u1.userId != u2.userId)
	 * 
	 * @ && role == "Manager";
	 * 
	 * @
	 */
	private Connection connection;
	private PreparedStatement preparedStatment;
	private static final long serialVersionUID = 9104811318735213684L;
	JMenuItem itmAddProject;
	JMenu mnProjects;
	public static JButton btnSubmit;
	// JMenuItem itmUpdateProject;
	JMenuItem itmDeleteProject;
	JMenu mnTeamMember;
	JMenuItem itmGrantPermission;
	JMenuItem itmDeleteMember;
	JMenuItem itmAddMember;
	JMenu mnBug;
	JMenuItem itmShowBug;
	JMenuItem itmShowBugList;
	JMenu mnExport;
	ArrayList<JPanel> panels = new ArrayList<JPanel>();
	private static String[] columnNames = { "Bug ID", "Bug Title", "Bug Description", "Bug Priority", "Bug Status",
			"Bug Due Date" };
	private static String[] columnNames_user = { "Name", "ID", "Username", "Email", "Role", "Permission List" };
	private static String[] columnNames_report = { "Project ID", "Project Title", "Project Description", "Bug Count",
			"Bug ID", "Bug Title", "Bug Description", "Bug Priority", "Bug Status", "Bug Due Date", "Bug Tester Id",
			" Bug Developer Id", "Bug Project Id" };
	int cPanel = 0;
	private JMenu mnSearch;
	private JMenuItem mntmSearchProject;
	private JMenuItem mntmSearchMember;
	private JMenu mnReports;
	private JMenuItem mntmPrintSale;

	private String password;

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

	private String username;
	private String role;
	private int userId;
	private String name;
	private String email;
	Permissions permissionList;

	/**
	 * Create the frame.
	 */
	public Manager() {
		super();

	}

	public Manager(int user_id) {
		setUserId(user_id);

	}

	@Override
	protected void GUI()

	{

		final JPanel contentPane;
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		setTitle("Manager Panel");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 840, 619);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		JLabel lblUserName = new JLabel("Welcome !!");
		lblUserName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblUserName.setBounds(154, 141, 91, 14);
		contentPane.add(lblUserName);

		mnProjects = new JMenu("Projects");
		menuBar.add(mnProjects);

		itmAddProject = new JMenuItem("Assign Projects");
		mnProjects.add(itmAddProject);
		itmAddProject.addActionListener(this);

		itmGrantPermission = new JMenuItem("Grant Permissions");
		mnProjects.add(itmGrantPermission);
		itmGrantPermission.addActionListener(this);

		mnBug = new JMenu("Bugs");
		menuBar.add(mnBug);

		itmShowBug = new JMenuItem("View Bug");
		mnBug.add(itmShowBug);
		itmShowBug.addActionListener(this);

		itmShowBugList = new JMenuItem("View Bug List");
		mnBug.add(itmShowBugList);
		itmShowBugList.addActionListener(this);

		mnSearch = new JMenu("Team");
		menuBar.add(mnSearch);

		mntmSearchProject = new JMenuItem("View Team members");
		mnSearch.add(mntmSearchProject);
		mntmSearchProject.addActionListener(this);

		mnReports = new JMenu("Reports");
		menuBar.add(mnReports);

		mntmPrintSale = new JMenuItem("See Reports");
		mnReports.add(mntmPrintSale);
		mntmPrintSale.addActionListener(this);

		mnExport = new JMenu("Account");
		menuBar.add(mnExport);

		JMenuItem logout = new JMenuItem("Logout");
		mnExport.add(logout);
		logout.addActionListener(this);

		getContentPane().setLayout(new BorderLayout(0, 0));

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		System.out.println("Selected: " + e.getActionCommand());
		if (e.getActionCommand().equals("Assign Projects")) {
			assignProjectJFrame();
		} else if (e.getActionCommand().equals("Grant Permissions")) {
			grantPermsiionsJFrame();
		} else if (e.getActionCommand().equals("View Bug")) {
			viewBugJFrame();
		} else if (e.getActionCommand().equals("View Bug List")) {
			viewBugListJFrame();
		} else if (e.getActionCommand().equals("View Team members")) {
			ViewTeamMembers();
		} else if (e.getActionCommand().equals("See Reports")) {
			generateReportsJFrame();
		} else if (e.getActionCommand().equals("Logout")) {
			this.dispose();
		}
	}

	private void grantPermsiionsJFrame() {
		// TODO Auto-generated method stub
		JPanel panel = new JPanel(new GridBagLayout());
		JPanel panel2 = new JPanel(new GridBagLayout());
		JFrame Frame2 = new JFrame("Grant Permission");
		Frame2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		Frame2.setLayout(new BorderLayout());

		JLabel select = new JLabel("Select user to set permission!!!");
		select.setFont(new Font("Tahoma", Font.PLAIN, 18));
		select.setBounds(80, 91, 2000, 25);
		Frame2.add(select);

		JLabel lblUser = new JLabel(" Select User :");
		lblUser.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblUser.setBounds(110, 141, 100, 14);
		Frame2.add(lblUser);
		final JComboBox UserField = new JComboBox();
		panel2.setBounds(282, 140, 200, 20);
		Frame2.add(panel2);
		panel2.add(UserField);

		JLabel lblPermission = new JLabel("Select Permission :\r\n");
		lblPermission.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPermission.setBounds(110, 174, 100, 25);
		Frame2.add(lblPermission);

		List<Permissions> permissionList = Arrays.asList(Permissions.values());
		Vector list = new Vector(permissionList);

		final JComboBox permissionField = new JComboBox(list);
		panel.setBounds(282, 174, 200, 20);
		Frame2.add(panel);
		panel.add(permissionField);

		JLabel lblDesc = new JLabel("Remarks :\r\n");
		lblDesc.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDesc.setBounds(110, 200, 200, 25);
		Frame2.add(lblDesc);

		final JTextField DesField = new JTextField();
		DesField.setBounds(282, 200, 200, 60);
		Frame2.add(DesField);
		DesField.setColumns(10);
		ResultSet rs2 = null;
		try {
			connection = ConnectionFactory.getConnection();
			String sql = "Select username from bug_tracking_user";

			PreparedStatement pstm = connection.prepareStatement(sql);

			rs2 = pstm.executeQuery();

			Vector v2 = new Vector();

			while (rs2.next()) {

				String ids = rs2.getString(1);

				v2.add(ids);

			}

			UserField.setModel(new DefaultComboBoxModel(v2));
		} catch (SQLException e) {
			System.out.println("SQLException in get() method");
			e.printStackTrace();
		} finally {
			DbUtil.close(rs2);
			DbUtil.close(preparedStatment);
			DbUtil.close(connection);
		}

		permissionField.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				btnSubmit.doClick();
			}
		});
		JButton btnSubmit = new JButton("Submit");

		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String user = (String) UserField.getSelectedItem();
				Permissions permission = (Permissions) permissionField.getSelectedItem();
				String remark = DesField.getText().toString().toLowerCase();

				DesField.setText("");

				grantPermission(user, permission);
			}
		});

		btnSubmit.setBounds(282, 300, 89, 23);
		Frame2.add(btnSubmit);

		Frame2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		Frame2.setLayout(new BorderLayout());

		Frame2.setVisible(true);

		Frame2.setSize(700, 700);

	}

	private void viewBugJFrame() {

		JFrame frame1 = new JFrame("View Bug");
		JPanel panel = new JPanel(new GridBagLayout());
		frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

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

		// select.setBounds(50, 210, 500, 40);

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
			String sql = "Select bugId,bugTitle from bugs";

			PreparedStatement pstm = connection.prepareStatement(sql);
			int i = 0;
			rs = pstm.executeQuery();

//			            Class.forName("oracle.jdbc.driver.OracleDriver");

//			            con = DriverManager.getConnection("jdbc:oracle:thin:@mcndesktop07:1521:xe", "sandeep", "welcome");

//			            st = con.createStatement();

//			            rs = st.executeQuery("select uname from emp");

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

				viewBug(bugID);

			}
		});

		frame1.setVisible(true);

		frame1.setSize(700, 500);

	}

	private void viewBugListJFrame() {
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
					+ ",a.bugStatus ,a.bugDueDate from bugs a ";

			PreparedStatement pstm = connection.prepareStatement(sql);
			int i = 0;
			rs = pstm.executeQuery();

			while (rs.next()) {
				bugId = rs.getString("bugId");
				bugTitle = rs.getString("bugTitle");
				bugDescription = rs.getString("bugDescription");
				bugPriority = rs.getString("bugPriority");
				bugStatus = rs.getString("bugStatus");
				bugDueDate = rs.getString("bugDueDate");

				model.addRow(new Object[] { bugId, bugTitle, bugDescription, bugPriority, bugStatus, bugDueDate });

			}

			table.setModel(model);

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

	private void generateReportsJFrame() {

		JFrame frame1 = new JFrame("Bug Search Result");

		frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		frame1.setLayout(new BorderLayout());

		// TableModel tm = new TableModel();

		DefaultTableModel model = new DefaultTableModel();

		model.setColumnIdentifiers(columnNames_report);

		// DefaultTableModel model = new DefaultTableModel(tm.getData1(),
		// tm.getColumnNames());

		// table = new JTable(model);

		JTable table = new JTable();

		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

		table.setFillsViewportHeight(true);

		JScrollPane scroll = new JScrollPane(table);

		scroll.setHorizontalScrollBarPolicy(

				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		scroll.setVerticalScrollBarPolicy(

				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

		// String textvalue = textbox.getText();
		String ProjectID = "";
		String ProjectTitle = "";
		String ProjectDescription = "";
		String BugCount = "";
		String BugTesterId = "";
		String BugDeveloperId = "";
		String BugProjectId = "";

		String bugId = "";

		String bugTitle = "";

		String bugDescription = "";

		String bugPriority = "";

		String bugStatus = "";

		String bugDueDate = "";

		ResultSet rs = null;
		try {

			connection = ConnectionFactory.getConnection();
			String sql = "select p.projectID,p.projectTitle,p.projectDescription,p.bug_count,b.* from project p inner join bugs b on p.projectID = b.bugProjectID";

			PreparedStatement pstm = connection.prepareStatement(sql);
			int i = 0;
			rs = pstm.executeQuery();

			while (rs.next()) {
				bugId = rs.getString("bugId");
				bugTitle = rs.getString("bugTitle");
				bugDescription = rs.getString("bugDescription");
				bugPriority = rs.getString("bugPriority");
				bugStatus = rs.getString("bugStatus");
				bugDueDate = rs.getString("bugDueDate");
				ProjectID = rs.getString("projectID");
				ProjectTitle = rs.getString("projectTitle");
				ProjectDescription = rs.getString("projectDescription");
				BugCount = rs.getString("bug_count");
				BugTesterId = rs.getString("bugtesterID");
				BugDeveloperId = rs.getString("bugdevID");
				BugProjectId = rs.getString("bugProjectID");

				model.addRow(new Object[] { ProjectID, ProjectTitle, ProjectDescription, BugCount, bugId, bugTitle,
						bugDescription, bugPriority, bugStatus, bugDueDate, BugTesterId, BugDeveloperId,
						BugProjectId });

			}

			table.setModel(model);

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

		frame1.setSize(900, 600);

	}

	private void ViewTeamMembers() {

		JFrame frame1 = new JFrame("Bug Search Result");

		frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		frame1.setLayout(new BorderLayout());

		// TableModel tm = new TableModel();

		DefaultTableModel model = new DefaultTableModel();
		model.setColumnIdentifiers(columnNames_user);

		// DefaultTableModel model = new DefaultTableModel(tm.getData1(),
		// tm.getColumnNames());

		// table = new JTable(model);

		JTable table = new JTable();

		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

		table.setFillsViewportHeight(true);

		JScrollPane scroll = new JScrollPane(table);

		scroll.setHorizontalScrollBarPolicy(

				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		scroll.setVerticalScrollBarPolicy(

				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

		// String textvalue = textbox.getText();
		String name, id, username, password, email, role, permissionlist;

		ResultSet rs = null;
		try {

			connection = ConnectionFactory.getConnection();
			String sql = "select name, id, username, email, role, permissionlist from bug_tracking_user where role = 'developer' or role = 'tester' ";

			PreparedStatement pstm = connection.prepareStatement(sql);
			int i = 0;
			rs = pstm.executeQuery();

			while (rs.next()) {
				name = rs.getString("name");
				id = rs.getString("id");
				username = rs.getString("username");

				email = rs.getString("email");
				role = rs.getString("role");
				permissionlist = rs.getString("permissionlist");

				model.addRow(new Object[] { name, id, username, email, role, permissionlist });

			}

			table.setModel(model);

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

	/*
	 * @ public normal_behavior
	 * 
	 * @ requires bugId > 0 && bugStatus != "Closed" && bugdevID = this.userId
	 * 
	 * @ ensures bugTitle != NULL && bugDescription != NULL && bugPriority != NULL
	 * && bugStatus != NULL && bugDueDate != NULL ;
	 * 
	 * @
	 */
	public void viewBug(String bugID) {
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
					+ ",a.bugStatus ,a.bugDueDate from bugs a where  bugStatus != 'CLOSED' or  bugStatus != 'closed' or bugStatus != 'Closed' and"
					+ " a.bugId ='" + bugID + "'";

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

	public void assignProjectJFrame() {
		JPanel panel = new JPanel(new GridBagLayout());
		JPanel panel2 = new JPanel(new GridBagLayout());

		JFrame PFrame = new JFrame("Assign Project");
		PFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		PFrame.setLayout(new BorderLayout());

		JLabel select = new JLabel("Select Project and User!!!");
		select.setFont(new Font("Tahoma", Font.PLAIN, 18));
		select.setBounds(80, 91, 2000, 25);
		PFrame.add(select);

		JLabel lblAssignto = new JLabel("Assign To :");
		lblAssignto.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblAssignto.setBounds(110, 141, 100, 14);
		PFrame.add(lblAssignto);

		final JComboBox AssigntoField = new JComboBox();
		panel2.setBounds(282, 140, 200, 20);
		PFrame.add(panel2);
		panel2.add(AssigntoField);

		JLabel lblProject = new JLabel("Projet :\r\n");
		lblProject.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblProject.setBounds(110, 174, 100, 25);
		PFrame.add(lblProject);

		final JComboBox ProjectField = new JComboBox();

		panel.setBounds(282, 174, 200, 20);
		PFrame.add(panel);
		panel.add(ProjectField);

		JLabel lblDesc = new JLabel("Remarks :\r\n");
		lblDesc.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDesc.setBounds(110, 200, 200, 25);
		PFrame.add(lblDesc);

		final JTextField DesField = new JTextField();
		DesField.setBounds(282, 200, 200, 60);
		PFrame.add(DesField);
		DesField.setColumns(10);

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

			ProjectField.setModel(new DefaultComboBoxModel(v1));
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
			String sql = "Select username from bug_tracking_user";

			PreparedStatement pstm = connection.prepareStatement(sql);

			rs2 = pstm.executeQuery();

			Vector v2 = new Vector();

			while (rs2.next()) {

				String ids = rs2.getString(1);

				v2.add(ids);

			}

			AssigntoField.setModel(new DefaultComboBoxModel(v2));
		} catch (SQLException e) {
			System.out.println("SQLException in get() method");
			e.printStackTrace();
		} finally {
			DbUtil.close(rs2);
			DbUtil.close(preparedStatment);
			DbUtil.close(connection);
		}

		AssigntoField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnSubmit.doClick();
			}
		});
		JButton btnSubmit = new JButton("Submit");

		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String assignto = (String) AssigntoField.getSelectedItem();
				String project = (String) ProjectField.getSelectedItem();
				String desc = DesField.getText().toString().toLowerCase();

				DesField.setText("");
				assignProject(assignto, project);

			}
		});

		btnSubmit.setBounds(282, 300, 89, 23);
		PFrame.add(btnSubmit);

		PFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		PFrame.setLayout(new BorderLayout());

		PFrame.setVisible(true);

		PFrame.setSize(700, 700);

	}

	public void assignProject(String username, String project_id) {
		int rs1;
		try {
			connection = ConnectionFactory.getConnection();
			String sql = "update project set userslist= concat( concat (userslist ,',' ),?) where projectID = ?";

			PreparedStatement pstm = connection.prepareStatement(sql);
			String testerId = Integer.toString(this.userId);
			pstm.setString(1, username);
			pstm.setString(2, project_id);

			rs1 = pstm.executeUpdate();

		} catch (SQLException e) {
			System.out.println("SQLException in get() method");
			e.printStackTrace();
		} finally {

			DbUtil.close(preparedStatment);
			DbUtil.close(connection);
		}

	}

	public void grantPermission(String username, Permissions permission) {
		int rs1;
		try {
			connection = ConnectionFactory.getConnection();
			String sql = "update bug_tracking_user set permissionlist= concat( concat (permissionlist ,',' ),'"
					+ permission + "') where username = ?";

			PreparedStatement pstm = connection.prepareStatement(sql);
			String testerId = Integer.toString(this.userId);

			pstm.setString(1, username);

			rs1 = pstm.executeUpdate();

		} catch (SQLException e) {
			System.out.println("SQLException in get() method");
			e.printStackTrace();
		} finally {

			DbUtil.close(preparedStatment);
			DbUtil.close(connection);
		}

	}
}
