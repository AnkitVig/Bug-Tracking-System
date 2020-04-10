package com.carleton;

import com.carleton.util.ConnectionFactory;
import com.carleton.util.DbUtil;

import javax.swing.JFrame;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class Project extends JFrame {
	public String projectID;
	public String usersList;
	public int bugCount;
    private Connection connection;
    public ResultSet results;
    /*@
	 @ public invariant
	 @ 
	 @ this.projectID != null
	 @ && (\forall String projectID; projectID != null ; projectID != this.projectID)
	 @ && usersList != null && usersList.length() > 0 ;
	 @
	 @*/
	
    /**
     *  \fn public void addProject(String name, String description, int userId)
     *  
     *  @param [in] name String value holding the name of the project.
     *  
     *  @param [in] description String value holding project description.
     *  
     *  @param [in] userID Integer value holding user ID of the user assigned to the project.
     *  
     */

    /*@
     @ public normal_behavior
     @
     @ requires name != null && description != null && userId > 0 && name.length() > 0 && description.length() > 0 ;
     @ ensures projectID != null;
     @*/
    public void addProject(String name, String description, int userId) {
        try {
            connection = ConnectionFactory.getConnection();
            String sql = "INSERT INTO project(projectId, projectTitle, projectDescription, bug_count, userslist) " +
                    "VALUES (?, ?, ?, ?, ?)";

            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setString(2, name);
            pstmt.setString(3, description);
            pstmt.setInt(4, 0);
            pstmt.setString(5, "");

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("SQLException in get() method");
            e.printStackTrace();
        } finally {
            DbUtil.close(connection);
        }
    }
    
    /**
     *  \fn public void closeProject(String projectId)
     *  
     *  @param [in] projectId String value holding the project ID of the project to be closed.
     *  
     */

    /*@
     @ public normal_behavior
     @
     @ requires projectId != null && projectId.length() > 0  && (\exists String projectid; projectid == projectId);
     @ ensures projectId == null;
     @*/
    public void deleteProject(String projectId) {
        try {
            connection = ConnectionFactory.getConnection();
            String sql = "DELETE FROM project WHERE projectId = ?";

            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, projectId);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("SQLException in get() method");
            e.printStackTrace();
        } finally {
            DbUtil.close(connection);
        }
    }
    
    /**
     *  \fn public void editProject(String projectId, String description)
     *  
     *  @param [in] projectId String value holding the project ID of the project to be edited.
     *  
     *  @param [in] description String value holding new project description. 
     *  
     */

    /*@
     @ public normal_behavior
     @
     @ requires projectId != null && projectId.length() > 0 && description != null && description.length() > 0;
     @ ensures description != \old (description);
     @*/
    public void editProject(String projectId, String description) {
        try {
            connection = ConnectionFactory.getConnection();
            String sql = "UPDATE project SET projectDescription = ? WHERE projectId = ?";

            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, description);
            pstmt.setString(2, projectId);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("SQLException in get() method");
            e.printStackTrace();
        } finally {
            DbUtil.close(connection);
        }
    }
    
    /**
     *  \fn public ResultSet viewProjectList()
     *  
     *  @return a set of all projects in the database.
     *  
     */

    /*@
     @ public normal_behavior
     @
     @ requires results ==  null;
     @ ensures results != null;
     @*/
    
    public ResultSet viewProjectList() {
        ResultSet results = null;
        try {
            connection = ConnectionFactory.getConnection();
            String sql = "SELECT * FROM project";

            PreparedStatement pstmt = connection.prepareStatement(sql);

            results = pstmt.executeQuery();
        } catch (SQLException e) {
            System.out.println("SQLException in get() method");
            e.printStackTrace();
        } finally {
         //   DbUtil.close(connection);
        }

        return results;
    }
}