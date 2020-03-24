
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenu;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import util.*;
	

	public class Manager extends User implements ActionListener {
		/**
		 * 
		 */
		private Connection connection;
		private static final long serialVersionUID = 9104811318735213684L;
		JMenuItem itmAddProject;
		JMenu mnProjects;
		public static JButton btnSubmit;
		//JMenuItem itmUpdateProject;
		JMenuItem itmDeleteProject;
		JMenu mnTeamMember ;
		JMenuItem itmGrantPermission ;
		JMenuItem itmDeleteMember;
		JMenuItem itmAddMember;
		JMenu mnBug ;
		JMenuItem itmShowBug;
		JMenuItem itmShowBugList;
		JMenu mnExport;
		ArrayList<JPanel> panels=new ArrayList<JPanel>();
		int cPanel=0;
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
		{ final JPanel contentPane;
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		setTitle("Manager Panel");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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

		mntmPrintSale = new JMenuItem("Print Reports");
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
		if(e.getActionCommand().equals("Assign Projects"))
		{
		assignProjectJFrame();
		}
		else if(e.getActionCommand().equals("Grant Permissions"))
		{
		grantPermsiionsJFrame();
		}
		else if(e.getActionCommand().equals("View Bug"))
		{
		viewBugJFrame();
		}
		else if(e.getActionCommand().equals("View Bug List"))
		{
		viewBugListJFrame();
		}
		else if(e.getActionCommand().equals("View Team members"))
		{
		ViewTeamMembers();
		}
		else if(e.getActionCommand().equals("Print Reports"))
		{
		generateReportsJFrame();
		}
		else if(e.getActionCommand().equals("Logout"))
		{
		this.dispose();
		}
		}

		
		
		private void grantPermsiionsJFrame() {
			// TODO Auto-generated method stub
			
		}
		private void viewBugJFrame() {
			// TODO Auto-generated method stub
			
		}
		private void viewBugListJFrame() {
			// TODO Auto-generated method stub
			
		}
		private void generateReportsJFrame() {
			// TODO Auto-generated method stub
			
		}
		private void ViewTeamMembers() {
			// TODO Auto-generated method stub
			
		}
		public static void viewBug(int bugID)
		{
			//implemented in each subclass
		}
		public static void reportGenerator(int project_id) 
		 {
			 
		 }
		public static void viewBugList(int project_id) 
		 {
			 
		 }
		public  void assignProjectJFrame()
		{
			JFrame PFrame = new JFrame("Assign Project ");
			PFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

			PFrame.setLayout(new BorderLayout());

			JLabel lblAssignto = new JLabel("Assign To :");
			lblAssignto.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblAssignto.setBounds(110, 141, 100, 14);
			PFrame.add(lblAssignto);

			final JTextField AssigntoField = new JTextField();
			AssigntoField.setBounds(282, 140, 200, 20);
			PFrame.add(AssigntoField);
			AssigntoField.setColumns(10);

			JLabel lblProject = new JLabel("Projet :\r\n");
			lblProject.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblProject.setBounds(110, 174, 100, 25);
			PFrame.add(lblProject);

			final JTextField ProjectField = new JTextField();
			ProjectField.setBounds(282, 174, 200, 20);
			PFrame.add(ProjectField);
			ProjectField.setColumns(10);

			JLabel lblDesc = new JLabel("Project Description :\r\n");
			lblDesc.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblDesc.setBounds(110, 200, 200, 25);
			PFrame.add(lblDesc);

			final JTextField DesField = new JTextField();
			DesField.setBounds(282, 200, 200, 60);
			PFrame.add(DesField);
			DesField.setColumns(10);

			AssigntoField.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
			btnSubmit.doClick();
			}
			});
			JButton btnSubmit = new JButton("Submit");

			btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			String Assignto = AssigntoField.getText().toString().toLowerCase();
			String Project =ProjectField.getText().toString().toLowerCase();
			String Desc =DesField.getText().toString().toLowerCase();
			AssigntoField.setText("");
			ProjectField.setText("");
			DesField.setText("");


			}
			});

			btnSubmit.setBounds(282, 300, 89, 23);
			PFrame.add(btnSubmit);


		}
		public  void assignProject(int userId, int project_id) 
		 {
			 
		 }
		public  void grantPermission(int userID, Permissions permission) 
		 {
			 
		 }
	}
