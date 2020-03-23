
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenu;

	

	public class Manager extends User implements ActionListener {
		/**
		 * 
		 */
		private static final long serialVersionUID = 9104811318735213684L;
		JMenuItem itmAddProject;
		JMenu mnProjects;
		//JMenuItem itmUpdateProject;
		JMenuItem itmDeleteProject;
		JMenu mnTeamMember ;
		JMenuItem itmDeleteMember;
		JMenuItem itmAddMember;
		JMenu mnBug ;
		JMenuItem itmShowBug;
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
		{	final JPanel contentPane;
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
			
			itmAddProject = new JMenuItem("Add Projects");
			mnProjects.add(itmAddProject);
			itmAddProject.addActionListener(this);
			
			itmDeleteProject = new JMenuItem("Delete Projects");
			mnProjects.add(itmDeleteProject);
			itmDeleteProject.addActionListener(this);
			
			
			mnTeamMember = new JMenu("Team Member");
			menuBar.add(mnTeamMember);
			
			itmAddMember = new JMenuItem("Add Team Member");
			mnTeamMember.add(itmAddMember);
			itmAddMember.addActionListener(this);
			
			itmDeleteMember = new JMenuItem("Delete Team Member");
			mnTeamMember.add(itmDeleteMember);
			itmDeleteMember.addActionListener(this);
			
			mnBug = new JMenu("Bugs");
			menuBar.add(mnBug);
			
			itmShowBug = new JMenuItem("Show Bugs");
			mnBug.add(itmShowBug);
			itmShowBug.addActionListener(this);
			
			mnSearch = new JMenu("Search");
			menuBar.add(mnSearch);
			
			mntmSearchProject = new JMenuItem("Search Project");
			mnSearch.add(mntmSearchProject);
			mntmSearchProject.addActionListener(this);
			
			mntmSearchMember = new JMenuItem("Search Team Member");
			mnSearch.add(mntmSearchMember);
			
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
			mntmSearchMember.addActionListener(this);
			
			getContentPane().setLayout(new BorderLayout(0, 0));
			
		/*	panels.add(new addProject());
			panels.add(new updateProduct());
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
			if(e.getActionCommand().equals("Add Product"))
			{
				System.out.println(panels.get(cPanel));
				this.remove(panels.get(cPanel));
				this.revalidate();
				this.repaint();
				getContentPane().add(panels.get(0));
				this.setVisible(true);
				cPanel=0;
				this.setTitle("Add Product");
			}
			else if(e.getActionCommand().equals("Update Product"))
			{
				this.remove(panels.get(cPanel));
				this.revalidate();
				this.repaint();
				getContentPane().add(panels.get(1));
				this.setVisible(true);
				cPanel=1;
				this.setTitle("Update Product");
			}
			else if(e.getActionCommand().equals("Delete Product"))
			{
				this.remove(panels.get(cPanel));
				this.revalidate();
				this.repaint();
				getContentPane().add(panels.get(2));
				this.setVisible(true);
				cPanel=2;
				this.setTitle("Delete Product");
			}
			else if(e.getActionCommand().equals("Add Cashier"))
			{
				this.remove(panels.get(cPanel));
				this.revalidate();
				this.repaint();
				getContentPane().add(panels.get(3));
				this.setVisible(true);
				cPanel=3;
				this.setTitle("Add Cashier");
			}
			else if(e.getActionCommand().equals("Delete Cashier"))
			{
				this.remove(panels.get(cPanel));
				this.revalidate();
				this.repaint();
				getContentPane().add(panels.get(4));
				this.setVisible(true);
				cPanel=4;
				this.setTitle("Delete Cashier");
			}
			else if(e.getActionCommand().equals("Show Stock"))
			{
				this.remove(panels.get(cPanel));
				getContentPane().add(panels.get(5));
				this.revalidate();
				this.repaint();
				this.setVisible(true);
				cPanel=5;
				this.setTitle("Show Stock");
			}
			else if(e.getActionCommand().equals("Search Product"))
			{
				this.remove(panels.get(cPanel));
				getContentPane().add(panels.get(6));
				this.revalidate();
				this.repaint();
				this.setVisible(true);
				cPanel=6;
				this.setTitle("Search Product");
			}
			else if(e.getActionCommand().equals("Search Cashier"))
			{
				this.remove(panels.get(cPanel));
				getContentPane().add(panels.get(7));
				this.revalidate();
				this.repaint();
				this.setVisible(true);
				cPanel=7;
				this.setTitle("Search Cashier");
			}
			else if(e.getActionCommand().equals("Print Sale"))
			{
				this.remove(panels.get(cPanel));
				getContentPane().add(panels.get(8));
				this.revalidate();
				this.repaint();
				this.setVisible(true);
				cPanel=8;
				this.setTitle("Print Sale");
			}
			else if(e.getActionCommand().equals("Logout"))
			{
				this.dispose();
			}
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
		public static void assignProject(int userId, int project_id) 
		 {
			 
		 }
		public static void grantPermission(int userID, Permissions permission) 
		 {
			 
		 }
	}
