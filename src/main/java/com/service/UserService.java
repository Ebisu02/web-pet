package com.service;

import com.model.ProfileData;
import com.repository.Connector;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class UserService {
    public static boolean login(String name, String pass) {
        try {
            Connection con = Connector.connect_to_users();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM users");
            while (rs.next()) {
                String uname = rs.getString("uName");
                String pwd = rs.getString("uPass");
                if (!(name.equals(uname) && pass.equals(pwd))) {
                    throw new Exception("Failed authentication name!=nameDB or pass!=passDB");
                }
                break;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static ProfileData getProfileDataByUsername(String username) {
        try {
            Connection con = Connector.connect_to_users();
            PreparedStatement stmt = con.prepareStatement("SELECT uMail, uAbout FROM users WHERE uName = ?");
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            ProfileData profileData = new ProfileData(username, rs.getString("uMail"), rs.getString("uAbout"));
            rs.close();
            stmt.close();
            con.close();
            return profileData;
        } catch (Exception e) {
            return null;
        }
    }

    public static String getUsername(String encodedCookie) throws UnsupportedEncodingException {
        try {
            String username = new String(Base64.decode(encodedCookie), "UTF-8");
            username = new String(Base64.decode(username.replace("true:", "")), "UTF-8");
            int indexOfTwoDots = username.indexOf(":");
            username = username.split(":")[0];
            return username;
        }
        catch (Exception e) {
            throw e;
        }
    }
}
