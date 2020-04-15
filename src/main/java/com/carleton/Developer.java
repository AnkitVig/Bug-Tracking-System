package com.carleton;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
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
import javax.swing.table.DefaultTableModel;

import javax.swing.JMenu;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.carleton.util.ConnectionFactory;
import com.carleton.util.DbUtil;


public class Developer extends User implements ActionListener {
	/**
	 * 
	 */
	/*@ public 
     @ invariant this.userId > 0
     @
     @ && (\forall int userId; userId > 0 ; userId != this.userId)
	 @ && role.equals("Developer") == true;
	 @
	 @*/
	
	private Connection connection;
	private PreparedStatement preparedStatment;
	private static final long serialVersionUID = 9104811318735213684L;

	JMenu mnExport;
	ArrayList<JPanel> panels = new ArrayList<JPanel>();
	int cPanel = 0;
	JButton viewBug, solveBug;

	private static String[] columnNames = { "Bug ID", "Bug Title", "Bug Description", "Bug Priority", "Bug Status",
			"Bug Due Date" };

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
	public int bugId;
	public String bugStatus, bugTitle, bugDescription , bugPriority,bugDueDate;
	public int bugdevID;
	private String password;
	private String username;
	public String role;
	public int userId;
	private String name;
	private String email;
	Permissions permissionList;

	/**
	 * Create the frame.
	 */
	public Developer() {
		super();

	}

	public Developer(int user_id) {
		setUserId(user_id);

	}

	@Override
	protected void GUI() {

		setTitle("Developer Panel");
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
		solveBug = new JButton("Solve Bug");

		viewBug.setBounds(150, 150, 150, 20);

		viewBug.addActionListener(this);
		solveBug.setBounds(150, 200, 150, 20);

		solveBug.addActionListener(this);

		add(viewBug);
		add(solveBug);

		getContentPane().setLayout(new BorderLayout(0, 0));

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		//System.out.println("Selected: " + e.getActionCommand());
		if (e.getSource() == viewBug) {
			viewBugJframe();
		}

		else if (e.getSource() == solveBug) {
			solveBugJframe();
		} else if (e.getActionCommand().equals("Logout")) {
			this.dispose();
		}
	}

	/**
     *  \fn public void viewBug(String bugID)
     *  
     *  @param [in] bugID Integer value holding the bug ID of the bug to be viewed.
     *  
     */

	/*@
	 @ public normal_behavior
	 @ 
	 @ requires bugId > 0 && bugStatus != "Closed" && bugdevID == userId;
	 @ 
	 @ ensures bugTitle.equals(null) ==false  && bugDescription .equals(null) ==false  && bugPriority.equals(null) ==false 
	 @ && bugStatus.equals(null) ==false  && bugDueDate.equals(null) ==false  ;
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
     *  \fn private void solveBug(String bugID)
     *  
     *  @param [in] bugID String value holding the bug ID of the bug to be solved.
     *  
     */

	/*@
	 @ public normal_behavior
	 @ 
	 @ requires bugId > 0 && bugStatus.equals("Open") == true ;
	 @ 
	 @ ensures bugId > 0 && bugStatus.equals("Resolved") == true ;
	 @ 
	 @*/

	private void solveBug(int bugID) {
		System.out.println(bugID);

		int rs;
		try {
			connection = ConnectionFactory.getConnection();
			String sql = "Update bugs set bugStatus = 'Resolved' where  bugID = ? ";

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
	public void viewBugJframe() {

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

		JButton searchBug = new JButton("Search");
		final JComboBox bugselect = new JComboBox();

		title.setBounds(80, 91, 2000, 35);

		searchBug.setBounds(282, 300, 89, 23);

		searchBug.addActionListener(this);

		panel.setBounds(282, 141, 200, 20);
		// setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		frame1.add(title);

		frame1.add(select);
		frame1.add(searchBug);
		panel.add(bugselect);
		frame1.add(panel);

		ResultSet rs = null;
		try {
			connection = ConnectionFactory.getConnection();
			String sql = "Select bugId,bugTitle from bugs where  bugStatus != 'CLOSED' and  bugStatus != 'closed' and bugStatus != 'Closed' and bugdevID =" + this.userId;

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

		searchBug.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				String bugID = (String) bugselect.getSelectedItem();
				String[] arrOfStr = bugID.split("-", 2); 
				
				viewBug(Integer.parseInt(arrOfStr[0]));

			}
		});

		frame1.setVisible(true);

		frame1.setSize(700, 500);
	}
	public void solveBugJframe() {
		JFrame frame1 = new JFrame("Solve Bug");
		JPanel panel = new JPanel(new GridBagLayout());

		frame1.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		frame1.setLayout(new BorderLayout());
		final JPanel contentPane;
		final JComboBox bugselect;

		JLabel title = new JLabel("Select Bug assigned to you to set status 'Resolved'");

		title.setForeground(Color.red);

		title.setFont(new Font("Tahoma", Font.PLAIN, 20));

		JButton search = new JButton("Change status to 'Resolved'");

		title.setBounds(20, 150, 500, 40);

		search.setBounds(50, 280, 300, 20);

		search.addActionListener(this);

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
			String sql = "Select bugId,bugTitle from bugs where bugStatus != 'CLOSED' "
					+ "and  bugStatus != 'closed' and bugStatus != 'Closed' and "
					+ " bugStatus != 'RESOLVED' and  bugStatus != 'resolved' and bugStatus != 'Resolved' and bugdevID = "
					+ this.userId;

			PreparedStatement pstm = connection.prepareStatement(sql);

			rs = pstm.executeQuery();

			Vector v = new Vector();

			while (rs.next()) {

				String ids = rs.getString(1);
				String names = rs.getString(2);
				v.add(ids.concat("-").concat(names));

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
				
				solveBug(Integer.parseInt(arrOfStr[0]));
				success.setText("Successfully Updated " + bugID);

			}

		});

	}
	

}
