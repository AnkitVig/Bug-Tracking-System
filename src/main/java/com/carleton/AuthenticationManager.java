package com.carleton;


import com.carleton.util.ConnectionFactory;
import com.carleton.util.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class AuthenticationManager {
	
	 /*@
    @ public invariant
    @
    @  username != null && password != null && role != null;
    @*/
	
    private static AuthenticationManager instance;
    private static Connection connection;
    public static Boolean login = null;
    public String username,password,role;
    
    
    
    /**
     *  \fn public int verifyLogin(String username, String password, String role)
     *  
     *  @param [in] username String value holding the username entered by the user. 
     *  
     *  @param [in] password String value holding the password entered by the user.
     *  
     *  @param [in] role String value holding the role of the user corresponding to the username and password.
     *  
     *  @return Integer value holding the userId of the logged in user if the users exists.
     *  
     */

    
    /*@
     @ public normal_behavior
     @
     @ requires username != null && password != null && role != null;
     @
     @ ensures login != null;
     @
     @
     @*/
    public static int verifyLogin(String username, String password, String role) {
 
        login = false;
        try {
            connection = ConnectionFactory.getConnection();
            String sql = "SELECT * FROM bug_tracking_user WHERE username = ? AND password = ? and role = ? LIMIT 1";


            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, username);
            pstm.setString(2, password);
            pstm.setString(3, role);
          
            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                int userId = rs.getInt("id");
                login = true;
                return userId;
            }

        } catch (SQLException e) {
            System.out.println("SQLException in get() method");
            e.printStackTrace();
        } finally {
            DbUtil.close(connection);
        }

        return 0;
    }

    public static AuthenticationManager getInstance() {
        if (instance == null) {
            instance = new AuthenticationManager();
        }
        return instance;
    }
}
