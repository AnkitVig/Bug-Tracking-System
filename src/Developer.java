
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
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
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.JMenu;

	

	public class Developer extends User implements ActionListener {
		/**
		 * 
		 */
		private static final long serialVersionUID = 9104811318735213684L;

		JMenu mnExport;
		ArrayList<JPanel> panels=new ArrayList<JPanel>();
		int cPanel=0;
		JButton viewBug, solveBug;


	   	private static String[] columnNames = {"Bug ID", "Bug Title", "Bug Description", "Bug Priority", "Bug Status", "Bug Due Date"
	   			,"Select New Bug status", "Change status"};
		
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
		private String password;
		private String username;
		private String role;
		private int userId;
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
		protected void GUI()
		{	
			//setIconImage(Toolkit.getDefaultToolkit().getImage("F:\\Working Directory\\fianl project with sql\\Bill\\logo.png"));
			setTitle("Developer Panel");
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setBounds(100, 100, 840, 619);
			
			JMenuBar menuBar = new JMenuBar();
			setJMenuBar(menuBar);
			

			
			mnExport = new JMenu("Account");
			menuBar.add(mnExport);
			
			JMenuItem logout = new JMenuItem("Logout");
			mnExport.add(logout);
			logout.addActionListener(this);
			
			 viewBug = new JButton("View Bug");
			 solveBug = new JButton("Assign Bug");
			
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
	        
			System.out.println("Selected: " + e.getActionCommand()); 
			if(e.getSource() == viewBug)
			{
				viewBug();
			}
			
			else if(e.getSource() == solveBug)
			{
				solveBug();
			}
			else if(e.getActionCommand().equals("Logout"))
			{
				this.dispose();
			}
		}
		
		
		
		public void viewBug()
		{
				JFrame frame1 = new JFrame("View Bug");
			 
		       frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		        frame1.setLayout(new BorderLayout());
		        
		        
		        
		        JLabel title = new JLabel("Select Bug assigned to you from the list");

		        title.setForeground(Color.red);

		        title.setFont(new Font("Tahoma", Font.PLAIN, 25));

		        JLabel select = new JLabel("Select Bug");
		        select.setFont(new Font("Tahoma", Font.PLAIN, 20));
		        JButton search = new JButton("Search");

		 

		        title.setBounds(50, 50, 500, 40);

		        select.setBounds(50, 90, 500, 40);

		        search.setBounds(50, 500, 150, 20);

		        search.addActionListener(this);

		        //setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		        

		        frame1.add(title);

		        frame1.add(select);
		        frame1.add(search);
		        
	            final JComboBox bugselect = new JComboBox();
	            bugselect.setBounds(50, 150, 75, 20);
	            
	 

	            frame1.add(bugselect);
		        
		    //    try {

//		            Class.forName("oracle.jdbc.driver.OracleDriver");

//		            con = DriverManager.getConnection("jdbc:oracle:thin:@mcndesktop07:1521:xe", "sandeep", "welcome");

//		            st = con.createStatement();

//		            rs = st.executeQuery("select uname from emp");

		            Vector v = new Vector();
		            v.add("random1");
		            v.add("random2");

//		            while (rs.next()) {

//		                ids = rs.getString(1);
//
//		                v.add(ids);

		 //           }

		         
		            bugselect.setModel(new DefaultComboBoxModel(v));
		           // st.close();

		            //rs.close();

//		        } catch (Exception e) {

//		        }
			
		            search.addActionListener(new ActionListener() {
		    			public void actionPerformed(ActionEvent arg0) {
		    				
		    				String bugID=(String) bugselect.getSelectedItem();
		    				
		    				viewBug(bugID);
		    				
		    			}
		    		});
		  
		            frame1.setVisible(true);

			        frame1.setSize(700, 700);

		}		
		
		public static void solveBug()
		{
			 JFrame frame1 = new JFrame("Select bug ID to View Bug");

		       frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		        frame1.setLayout(new BorderLayout());
		        
		        frame1.setVisible(true);

		        frame1.setSize(400, 300);
		}
		
		public static void viewBug(String bugID)
		{
			 JFrame frame1 = new JFrame("Database Search Result");

		       frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		        frame1.setLayout(new BorderLayout());

		//TableModel tm = new TableModel();

		        DefaultTableModel model = new DefaultTableModel();

		        model.setColumnIdentifiers(columnNames);

		//DefaultTableModel model = new DefaultTableModel(tm.getData1(), tm.getColumnNames());

		//table = new JTable(model);

		        JTable table = new JTable();

		        table.setModel(model);

		        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

		        table.setFillsViewportHeight(true);

		        JScrollPane scroll = new JScrollPane(table);

		        scroll.setHorizontalScrollBarPolicy(

		                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		        scroll.setVerticalScrollBarPolicy(

		                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);


		//String textvalue = textbox.getText();

		        String uname = "";

		        String email = "";

		        String pass = "";

		        String cou = "";

		 

		        try {

		   //         pst = con.prepareStatement("select * from emp where UNAME='" + from + "'");

		    //        ResultSet rs = pst.executeQuery();

		            int i = 0;

//		            if (rs.next()) {
//
//		                uname = rs.getString("uname");
//
//		                email = rs.getString("umail");
//
//		                pass = rs.getString("upass");
//
//		                cou = rs.getString("ucountry");
//
//		                model.addRow(new Object[]{uname, email, pass, cou});
//
//		                i++;
//
//		            }

		            if (i < 1) {

		                JOptionPane.showMessageDialog(null, "No Record Found", "Error", JOptionPane.ERROR_MESSAGE);

		            }

		            if (i == 1) {

		                System.out.println(i + " Record Found");

		            } else {

		                System.out.println(i + " Records Found");

		            }

		        } catch (Exception ex) {

		            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

		        }

		        frame1.add(scroll);

		        frame1.setVisible(true);

		        frame1.setSize(400, 300);
		}
		public static void solveBug(int bugID)
		{
		
		}
	
	}
