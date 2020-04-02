import java.util.Date;
import java.sql.*;
import javax.swing.*;

public class Bug{
	String bug_description;
	String bug_title;
	String bug_projectID;
	String bug_priority;
	enum bug_status {
		OPEN, INPROGRESS, CLOSE
	}
	String bug_developerID;
	String bug_testerID;
	Date bug_due_date = new Date();
	Connection con;
	Statement stmnt;
	ResultSet rs;
	
	
	public Bug(){
		try{  
			Class.forName("com.mysql.cj.jdbc.Driver");  
			con=DriverManager.getConnection(  
			"jdbc:mysql://localhost:3306/bug_tracking_system","root","mysqlpwd");
			stmnt = con.createStatement();
		}
		catch(Exception e){ 
			JOptionPane.showMessageDialog(null, "ERROR: "+e.getMessage());
		}  
	}
	
	/*
	 * @ public normal_behaviour
	 * 
	 * @public invariant bugID >= 0;
	 * 
	 * @ requires bug_title != NULL && bug_description != NULL && bug_priority != NULL 
	 * && bug_due_date != NULL && bug_testerID != NULL && bug_developerID !=NULL
	 * && bug_projectID != NULL;
	 * 
	 * @ ensures bugID = \old (bugID) + 1;
	 * 
	 * @
	 */
	public int addBug(String bug_title, String bug_description, String bug_priority,
				bug_status bs, String bug_due_date, String bug_testerID, String bug_developerID, String bug_projectID ){
		int bugID = 0;
		try{
			stmnt.executeUpdate("insert into bugs(bugTitle, bugDescription , bugPriority, bugStatus , bugDueDate , bugtesterID , bugdevID ,bugProjectID)" + ""
					+ "values ('"+bug_title+"','"+bug_description+"','"+bug_priority+"', '"+bs+"', '"+bug_due_date+"', '"+bug_testerID+"', '"+bug_developerID+"', '"+bug_projectID+"')");
		
		    rs = stmnt.executeQuery("select last_insert_id()");
		    while(rs.next()){
		    	bugID = rs.getInt(1);
		    }
		}
		catch(Exception e){ 
			JOptionPane.showMessageDialog(null, "ERROR: "+e.getMessage());
		}
		return bugID;
		}
	
	
	/*
	 * @ public normal_behaviour
	 * 
	 * @ public invariant b_id >= 0;
	 * 
	 * @ requires bug_status == "OPEN" && (\exists int bug_ID; b_id = bug_ID);
	 * 
	 * @ ensures bug_status == "CLOSED";
	 * 
	 * @
	 */
	
	public int closeBug(int bug_ID){
		int b_id = bug_ID;
		try{
		    stmnt.executeUpdate("UPDATE bugs SET bugStatus = 'CLOSE' WHERE bugID = "+bug_ID+"");
		}
		catch(Exception e){ 
			JOptionPane.showMessageDialog(null, "ERROR: "+e.getMessage());
		}
		return b_id;
		}
	
	/*
	 * @ public normal_behaviour
	 * 
	 * @public invariant b_id >= 0;
	 * 
	 * @ requires rs != NULL ;
	 * 
	 * @ ensures bugStatus != \old (bugStatus);
	 * 
	 * @
	 */
	public int editBugStatus(int bug_ID){
		int b_id = bug_ID;
		try{
		    rs = stmnt.executeQuery("SELECT * FROM bugs WHERE bugID = "+bug_ID+" AND bugStatus != 'CLOSE'");
		    if(rs.next()){
		    	stmnt.executeUpdate("UPDATE bugs SET bugStatus = 'INPROGRESS' WHERE bugID = "+bug_ID+" ");
		    }
		    else{
		    	JOptionPane.showMessageDialog(null, "This bug is already closed!");
		    }
		}
		catch(Exception e){ 
			JOptionPane.showMessageDialog(null, "ERROR: "+e.getMessage());
		}
		return b_id;
	}
	
	/*
	 * @ public normal_behaviour
	 * 
	 * @public invariant bid >= 0;
	 * 
	 * @ requires rs != NULL && bugStatus != "CLOSED" && (\exists int bugID; bid = bugID);
	 * 
	 * @ ensures \result == bugID;
	 * 
	 * @
	 */
	
	public int searchBug(int bugID){
		int bid = bugID;
		try{
		    rs = stmnt.executeQuery("SELECT * FROM bugs WHERE bugID = "+bugID+"");
		    while(rs.next()){
		    	JOptionPane.showMessageDialog(null, "Bug Title: " +  rs.getString(2) + "\nBug Description: " + rs.getString(3) + 
		          "\nBug Priority: " + rs.getString(4) + "\nBug Status: " + rs.getString(5) + "\nBug Due Date: " +
		          rs.getString(6) + "\nBug TesterID: " + rs.getString(7)+ "\nBug Developer ID: " +
		          rs.getString(8) + "\nBug Project ID: " + rs.getString(9));
		    }
		}
		catch(Exception e){ 
			JOptionPane.showMessageDialog(null, "ERROR: "+e.getMessage());
		}
		return bid;
	}
	public static void main(String[] args){
		new Bug();
	}
}


