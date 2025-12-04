package com.example.DBActions;

import com.example.Main;
import com.example.Model.Book;
import com.example.Model.User;

import java.sql.*;
import java.util.logging.Logger;

public class UserCRUD {
    private static final Logger logger = Logger.getLogger(UserCRUD.class.getName());

    //Sign In
    public static User Signin(Connection con, String username, String password) {
        String query = "SELECT * FROM userschema.usertable WHERE username = ? AND password = ?";
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, username);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    User u = new User();
                    u.setUserid(rs.getInt("userid"));
                    u.setName(rs.getString("username"));
                    u.setEmail(rs.getString("email"));
                    u.setPassword(rs.getString("password"));
                    u.setRole(rs.getString("role"));
                    logger.info("USER : "+username+"("+rs.getInt("userid")+") SINGED IN.");
                    return u;
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    //User Management
    public static User addUser(Connection con, User user) {
        String query = "INSERT INTO userschema.usertable (username, password,role,email) VALUES (?, ?,?,?)";
        try (PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getRole());
            ps.setString(4, user.getEmail());

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        user.setUserid(rs.getInt(1));
                    }
                }
                logger.info("NEW "+(user.getRole().equals("student")?"STUDENT":"ADMIN")+" : "+user.getName()+"("+user.getUserid()+") DETECTED.");
                return user;
            }
        } catch (SQLException e) {
            if (e.getSQLState().equals("23505")) {
                System.out.println("Username already exists!");
            } else {
                e.printStackTrace();
            }
            System.out.println("-----> ADD USER SQL EXCEPTION : "+e.toString());
        }

        return null;
    }


}
