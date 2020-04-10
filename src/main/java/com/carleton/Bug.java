package com.carleton;
import java.util.Date;
import java.sql.*;
import javax.swing.*;
import com.carleton.util.ConnectionFactory;
import com.carleton.util.DbUtil;
import com.carleton.User;

public class Bug{
	public int bug_ID;
	public String bug_description;
	public String bug_title;
	public String bug_projectID;
	public String bug_priority;
	public String bugStatus;
	enum bug_status {
		OPEN, RESOLVED, CLOSED
	}
	String bug_developerID;
	String bug_testerID;
	Date bug_due_date = new Date();
	Statement stmnt;
	ResultSet rs;
	public Project p = new Project();
	private Connection con;

	public Bug(){
		con = ConnectionFactory.getConnection();
	}
	/*@
	 @ public invariant
	 @ 
	 @ this.bug_ID > 0
	 @ && (\forall int bug_ID; bug_ID > 0 ; bug_ID != this.bug_ID)
	 @ && p.projectID != null
	 @ && p.bugCount >=0 ;
	 @ 
	 @
	 @*/
	
	/**
     *  \fn public int addBug(String bug_title, String bug_description, String bug_priority,
				bug_status bs, String bug_due_date, String bug_testerID, String bug_developerID, String bug_projectID )
     *  
     *  @param [in] bug_title String value holding the bug title.
     *  
     *  @param [in] bug_description String value holding the bug description.
     *  
     *  @param [in] bug priority String value holding the bug priority.
     *  
     *  @param [in] bs Enumeration type holding the bug status.
     *  
     *  @param [in] bug_due_date String value holding the due date of bug.
     *  
     *  @param [in] bug_testerID String value holding userID of project tester.
     *  
     *  @param [in] bug_developerID String value holding userID of project developer.
     *  
     *  @param [in] bug_projectID String value holding the project ID.
     *  
     *  @return Integer value holding the newly generated bug ID. 
     *  
     */
	
	/*@
	 @ public normal_behaviour
	 @ 
	 @
	 @ requires bug_title != null && bug_description != null && bug_priority != null 
	 @ && bug_due_date != null && bug_testerID != null && bug_developerID !=null
	 @ && bug_projectID != null;
	 @ 
	 @ ensures bug_ID == \old (bug_ID) + 1;
	 @ 
	 @
	 @*/
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
	
	/**
     *  \fn public int closeBug(int bug_ID)
     *  
     *  @param [in] bug_ID Integer value holding the bug ID of the bug to be closed.
     *  
     *  @return Integer value holding bug ID of the closed bug. 
     *  
     */
	
	/*@
	 @ public normal_behaviour 
	 @
	 @ requires bugStatus.equals("OPEN") == true && (\exists int b_id; b_id == bug_ID);
	 @ 
	 @ ensures  bugStatus.equals("CLOSED") == true;
	 @ 
	 @
	 @*/
	
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
	
	/**
     *  \fn int editBugStatus(int bug_ID)
     *  
     *  @param [in] bug_ID Integer value holding the bug ID of the bug to be edited.
     *  
     *  @return Integer value holding bug ID of the edited bug. 
     *  
     */
	
	
	/*@
	 @ public normal_behaviour
	 @ 
	 @ requires bug_ID  >= 0 ;
	 @ 
	 @ ensures bugStatus != \old (bugStatus);
	 @ 
	 @
	 @*/
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
	
	/**
     *  \fn int searchBug(int bugID)
     *  
     *  @param [in] bug_ID Integer value holding the bug ID of the bug to be searched.
     *  
     *  @return Integer value holding bug ID of the searched bug. 
     *  
     */
	
	
	/*@
	 @ public normal_behaviour
	 @ 
	 @ 
	 @ requires bugStatus.equals("CLOSED") == false && (\exists int bid; bid == bugID);
	 @ 
	 @ ensures \result == bugID;
	 @ 
	 @
	 @*/
	
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
}


