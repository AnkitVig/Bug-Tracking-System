package com.carleton;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JComboBox;

import java.awt.event.ActionListener;
import java.util.Vector;
import java.awt.event.ActionEvent;
import java.awt.Color;

//import Database;
enum Permissions {
    AddProject, EditProject, DeleteProject, AddBug, EditBug, DeleteBug;
}

enum BugStatus {
    Open, Closed, Resolved;
}

public class User extends JFrame {

    /*@ public
     @ invariant this.userId > 0
     @
     @ && (\forall int userId; userId > 0 ; userId != this.userId);
     @
     @
     @*/

    private static final long serialVersionUID = 1L;


    public String password;
    public String username;
    public String role;
    public int userId;
    public String name;
    public String email;
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

    private JButton btnLogin;
    private JLabel error;

    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {

                    User frame = new User();

                    frame.setVisible(true);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public User() {
        GUI();
    }

    protected void GUI() {
        final JPanel contentPane;
        final JTextField usernameField;
        final JTextField roleField;
        final JPasswordField passwordField;
        final JLabel lblManagerLogin;
        final JComboBox c1;

        final JLabel label;
        setTitle("User");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 531, 387);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblUserName = new JLabel("User name");
        lblUserName.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblUserName.setBounds(154, 141, 91, 14);
        contentPane.add(lblUserName);

        usernameField = new JTextField();
        usernameField.setBounds(282, 140, 129, 20);
        contentPane.add(usernameField);
        usernameField.setColumns(10);

        JLabel lblPassword = new JLabel("Password\r\n");
        lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblPassword.setBounds(154, 174, 91, 14);
        contentPane.add(lblPassword);

        passwordField = new JPasswordField();
        passwordField.setBounds(282, 173, 129, 20);
        contentPane.add(passwordField);

        JLabel lblRole = new JLabel("Role\r\n");
        lblRole.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblRole.setBounds(154, 200, 91, 14);
        contentPane.add(lblRole);
        Vector v = new Vector();
        v.add("Manager");
        v.add("Developer");
        v.add("Tester");

        c1 = new JComboBox(v);
        c1.setBounds(282, 200, 129, 20);

        add(c1);

        passwordField.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                btnLogin.doClick();
            }
        });

        btnLogin = new JButton("Login");

        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                password = passwordField.getText().toString().toLowerCase();
                username = usernameField.getText().toString().toLowerCase();
                role = (String) c1.getSelectedItem();
                passwordField.setText("");
                usernameField.setText("");

                login(username, password, role.toLowerCase());

            }
        });

        btnLogin.setBounds(282, 250, 89, 23);
        contentPane.add(btnLogin);

        error = new JLabel("");
        error.setForeground(Color.RED);
        error.setBounds(104, 236, 220, 14);
        contentPane.add(error);

        lblManagerLogin = new JLabel("User Login");
        lblManagerLogin.setFont(new Font("Tahoma", Font.PLAIN, 17));
        lblManagerLogin.setBounds(204, 26, 167, 28);
        contentPane.add(lblManagerLogin);


    }

    /*@
     @ public normal_behavior
     @
     @ requires username.equals(null) == false && password.equals(null) == false && role.equals(null) == false;
     @
     @ ensures userId != 0 && email.equals(null) == false  && name.equals(null) == false;
     @
     @*/

    public void login(String username, String password, String role) {
        final String errorText = "Invalid user name or password!";
        if (password.equals("") || username.equals("") || role.equals(""))
            error.setText(errorText);
        else {
            error.setText("");
            if (role.equals("manager")) {
                userId = AuthenticationManager.verifyLogin(username, password, role);
                if (userId > 0) {
                    error.setText("");
                    Manager p = new Manager(userId);

                    p.setVisible(true);

                } else
                    error.setText(errorText);
            } else if (role.equals("tester")) {
                userId = AuthenticationManager.verifyLogin(username, password, role);
                if (userId > 0) {
                    error.setText("");
                    Tester t = new Tester(userId);
                    t.setVisible(true);

                } else
                    error.setText(errorText);
            } else if (role.equals("developer")) {
                userId = AuthenticationManager.verifyLogin(username, password, role);
                if (userId > 0) {
                    error.setText("");
                    Developer d = new Developer(userId);
                    d.setVisible(true);

                } else
                    error.setText(errorText);
            }

        }
    }

}