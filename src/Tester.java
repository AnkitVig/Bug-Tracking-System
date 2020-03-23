
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JMenu;

	

	public class Tester extends User implements ActionListener {
		/**
		 * 
		 */
		private static final long serialVersionUID = 9104811318735213684L;

		JButton viewBug, assignBug, deleteBug;
		JMenu mnExport;

		ArrayList<JPanel> panels=new ArrayList<JPanel>();
		int cPanel=0;
	

		
		private String password;
		private String username;
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
			 JFrame frame1 = new JFrame("Select bug ID to View Bug");

		       frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		        frame1.setLayout(new BorderLayout());
		        
		        frame1.setVisible(true);

		        frame1.setSize(400, 300);
		        

		}
		public static void viewBug(int bugID)
		{
		
		}
		public static void assignBug()
		{
			 JFrame frame1 = new JFrame("Select bug ID to View Bug");

		       frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		        frame1.setLayout(new BorderLayout());
		        
		        frame1.setVisible(true);

		        frame1.setSize(400, 300);
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
