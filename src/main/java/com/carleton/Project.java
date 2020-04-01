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

    private Connection connection;

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

    public void closeProject(String projectId) {
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

    public ResultSet viewProjectList() {
        ResultSet results = null;
        try {
            connection = ConnectionFactory.getConnection();
            String sql = "SELECT * FROM project";
            ;

            PreparedStatement pstmt = connection.prepareStatement(sql);

            results = pstmt.executeQuery();
        } catch (SQLException e) {
            System.out.println("SQLException in get() method");
            e.printStackTrace();
        } finally {
            DbUtil.close(connection);
        }

        return results;
    }
}