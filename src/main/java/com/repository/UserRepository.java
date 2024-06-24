package com.repository;

import com.model.ProfileData;
import com.model.User;

import java.sql.*;

public class UserRepository {
    // Returns a ProfileData by Username: {Username, Email, AboutInfo}
    public static ProfileData getProfileData(String username) throws SQLException, ClassNotFoundException {
        Connection con = Connector.connect_to_users();
        PreparedStatement stmt = con.prepareStatement("SELECT uMail, uAbout FROM users WHERE uName = ?");
        stmt.setString(1, username);
        ResultSet rs = stmt.executeQuery();
        ProfileData profileData = new ProfileData(username, rs.getString("uMail"), rs.getString("uAbout"));
        rs.close();
        stmt.close();
        con.close();
        return profileData;
    }

    // Returns a next Id in table 'users' from database
    public static Integer getNextIdFromTableUsers() throws SQLException, ClassNotFoundException {
        Connection con = Connector.connect_to_users();
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM users ORDER BY uId DESC LIMIT 1");
        Integer count = 1;
        if (rs.next()) {
            count = rs.getInt("uId");
        }
        count++;
        rs.close();
        stmt.close();
        con.close();
        return count;
    }

    // True - name is unique, False - name is not unique
    public static boolean checkIsNameUnique(String name) throws SQLException, ClassNotFoundException {
        Connection con = Connector.connect_to_users();
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT uName FROM users");
        do {
            String uname = rs.getString("uName");
            if (name.equals(uname)) {
                rs.close();
                stmt.close();
                con.close();
                return false;
            }
        } while (rs.next());
        rs.close();
        stmt.close();
        con.close();
        return true;
    }

    // True - name is unique, False - name is not unique
    public static boolean checkIsEmailUnique(String email) throws SQLException, ClassNotFoundException {
        Connection con = Connector.connect_to_users();
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT uMail FROM users");
        do {
            String umail = rs.getString("uMail");
            if (email.equals(umail)) {
                rs.close();
                stmt.close();
                con.close();
                return false;
            }
        } while (rs.next());
        rs.close();
        stmt.close();
        con.close();
        return true;
    }

    // Create a statement what add user to database in table 'users'
    public static void insertUserIntoDatabase(User user) throws SQLException, ClassNotFoundException {
        Connection con = Connector.connect_to_users();
        PreparedStatement stmtInsert = con.prepareStatement("INSERT INTO users (uId, uName, uPass, uMail, uAbout)" +
                "VALUES (?, ?, ?, ?, ?)");
        stmtInsert.setInt(1, user.getId());
        stmtInsert.setString(2, user.getName());
        stmtInsert.setString(3, user.getPass());
        stmtInsert.setString(4, user.getEmail());
        stmtInsert.setString(5, user.getAbout());
        stmtInsert.executeUpdate();
        stmtInsert.close();
        con.close();
    }
}
