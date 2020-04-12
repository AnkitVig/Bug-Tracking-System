package com.carleton;

import com.carleton.util.ConnectionFactory;
import com.carleton.util.DbUtil;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import java.util.Date;

public class Bug extends JFrame {
	public String bugID;
	public String bug_description;
	public String bug_title;
	public String bug_projectID;
	public String bug_priority;
	public enum bug_status {
		OPEN, RESOLVED, CLOSED
	}
	public String bug_developerID;
	public String bug_testerID;
	public Date bug_due_date = new Date();
	
	public ResultSet results, results1;
	private static Connection connection;
	
	/*@
	 @ public invariant
	 @ 
	 @ this.bugID != null
	 @ && (\forall String bugID; bugID != null ; bugID != this.bugID);
	 @
	 @*/

	public Bug(){
		connection = ConnectionFactory.getConnection();
	}
	
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
	
	/*
	 @ public normal_behaviour
	 @ requires bug_title != null && bug_description != null && bug_priority != null 
	 @ && bug_due_date != null && bug_testerID != null && bug_developerID != null
	 @ && bug_projectID != null && bug_title.length() > 0 && bug_description.length() > 0 &&
	 @ bug_priority.length() > 0 && bug_due_date.length() > 0 && bug_testerID.length() > 0 &&
	 @ bug_developerID.length() > 0 && bug_projectID.length() > 0;
	 @ ensures bugID != null;
	 */
	public int addBug(String bug_title, String bug_description, String bug_priority,
				String bs, String bug_due_date, String bug_testerID, String bug_developerID, String bug_projectID ){
		int bugID = 0;
		try{
			String sql = "INSERT INTO bugs(bugTitle, bugDescription , bugPriority, bugStatus , bugDueDate , bugtesterID , bugdevID ,bugProjectID)" + ""
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
			
			PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, bug_title);
            pstmt.setString(2, bug_description);
            pstmt.setString(3, bug_priority);
            pstmt.setString(4, bs);
            pstmt.setString(5, bug_due_date);
            pstmt.setString(6, bug_testerID);
            pstmt.setString(7, bug_developerID);
            pstmt.setString(5, bug_projectID);

            pstmt.executeUpdate();
		
            PreparedStatement pstmt1 = connection.prepareStatement(sql);
            
		    results = pstmt1.executeQuery("select last_insert_id()");
		    while(results.next()){
		    	bugID = results.getInt(1);
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
	
	/*
	 @ public normal_behaviour 
	 @ requires bug_status == "OPEN" && (\exists int bug_ID; b_id = bug_ID);
	 @ ensures bug_status == "CLOSED" && b_id != null;
	 @
	 */
	
	public int closeBug(int bug_ID){
		int b_id = bug_ID;
		try{
		    String sql = "UPDATE bugs SET bugStatus = 'CLOSE' WHERE bugID = ?";
		    
		    PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, bug_ID);
            
            pstmt.executeUpdate();
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
     */
	
	
	/*
	 @ public normal_behaviour
	 @ requires bug_ID != null ;
	 @ ensures bugStatus != \old (bugStatus);
	 */
	public void editBugStatus(int bug_ID){
		int b_id = bug_ID;
		try{
		    String sql = "SELECT * FROM bugs WHERE bugID = ? AND bugStatus != ?";
		    
		    PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, bug_ID);
            pstmt.setString(2, "CLOSED");
            
           	pstmt.executeUpdate();
		    if(results.next()){
		    	String sql1 = "UPDATE bugs SET bugStatus = 'INPROGRESS' WHERE bugID = ?";
		    	PreparedStatement pstmt1 = connection.prepareStatement(sql1);
	            pstmt.setInt(1, bug_ID);
	            
	              pstmt1.executeUpdate();
		    }
		    else{
		    	JOptionPane.showMessageDialog(null, "This bug is already closed!");
		    }
		}
		catch(Exception e){ 
			JOptionPane.showMessageDialog(null, "ERROR: "+e.getMessage());
		}
	}
	
	/**
     *  \fn int searchBug(int bugID)
     *  
     *  @param [in] bug_ID Integer value holding the bug ID of the bug to be searched.
     *  
     *  @return Integer value holding bug ID of the searched bug. 
     *  
     */
	
	
	/*
	 @ public normal_behaviour
	 @ requires results != NULL && bugStatus != "CLOSED" && (\exists int bugID; bid = bugID);
	 @ ensures \result == bugID;
	 @
	 */
	
	public int searchBug(int bugID){
		int bid = bugID;
		try{
		    String sql = "SELECT * FROM bugs WHERE bugID = ?";
		    
		    PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, bugID);
   
             pstmt.executeUpdate();
		    
		    while(results.next()){
		    	JOptionPane.showMessageDialog(null, "Bug Title: " +  results.getString(2) + "\nBug Description: " + results.getString(3) + 
		          "\nBug Priority: " + results.getString(4) + "\nBug Status: " + results.getString(5) + "\nBug Due Date: " +
		          results.getString(6) + "\nBug TesterID: " + results.getString(7)+ "\nBug Developer ID: " +
		          results.getString(8) + "\nBug Project ID: " + results.getString(9));
		    }
		}
		catch(Exception e){ 
			JOptionPane.showMessageDialog(null, "ERROR: "+e.getMessage());
		}
		return bid;
	}
}

