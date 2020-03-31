package com.carleton;


import com.carleton.util.ConnectionFactory;
import com.carleton.util.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class AuthenticationManager {
    private static AuthenticationManager instance;
    private Connection connection;

    public int verifyLogin(String username, String password, String role) {
        boolean login = false;

        try {
            connection = ConnectionFactory.getConnection();
            String sql = "SELECT * FROM bug_tracking_user WHERE username = ? AND password = ? and role = ? LIMIT 1";

            PreparedStatement pstm = connection.prepareStatement(sql);

            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                int userId = rs.getInt("id");
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
