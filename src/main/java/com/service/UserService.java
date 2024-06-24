package com.service;

import com.model.Credentials;
import com.model.ProfileData;
import com.model.User;
import com.repository.Connector;
import com.repository.UserRepository;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import javax.servlet.ServletException;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class UserService {
    // Login process
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

    // Getting profile data by username
    public static ProfileData getProfileDataByUsername(String username) {
        try {
            return UserRepository.getProfileData(username);
        } catch (Exception e) {
            return null;
        }
    }

    // Gets username from encoded cookie
    public static String getUsername(String encodedCookie) throws UnsupportedEncodingException {
        try {
            String username = new String(Base64.decode(encodedCookie), "UTF-8");
            username = new String(Base64.decode(username.replace("true:", "")), "UTF-8");
            username = username.split(":")[0];
            return username;
        }
        catch (Exception e) {
            throw e;
        }
    }

    // Returns a Credential Entity: {name, email}
    public static Credentials getCredentials(StringTokenizer stringTokenizer, String encodedCredentials) throws ServletException, UnsupportedEncodingException {
        if (!stringTokenizer.hasMoreTokens()) {
            throw new ServletException("StringTokenizer has no more tokens");
        }
        String basicHeader = stringTokenizer.nextToken();
        if (!basicHeader.equalsIgnoreCase("Basic")) {
            throw new ServletException("BasicAuthHeader isn't equal case 'Basic'");
        }
        encodedCredentials = stringTokenizer.nextToken();
        if (encodedCredentials.isEmpty()) {
            throw new ServletException("Encoded credentials is empty");
        }
        String stringCredentials = new String(Base64.decode(encodedCredentials), "UTF-8");
        int p = stringCredentials.indexOf(":");
        if (p == -1) {
            throw new ServletException("Invalid basic header: stringCred.indexOf(':')==-1");
        }
        String name = stringCredentials.substring(0, p).trim();
        String pass = stringCredentials.substring(p + 1).trim();
        if (name.isEmpty() && pass.isEmpty()) {
            throw new ServletException("Empty username or user-password strings");
        }
        Credentials credentials = new Credentials(name, pass);
        return credentials;
    }

    // Registration process
    public static User register(Integer id, StringTokenizer stringTokenizer) throws UnsupportedEncodingException, ServletException {
        if (!stringTokenizer.hasMoreTokens()) {
            throw new ServletException("StringTokenizer has no more tokens");
        }
        String basicHeader = stringTokenizer.nextToken();
        if (!basicHeader.equalsIgnoreCase("Basic")) {
            throw new ServletException("BasicAuthHeader isn't equal case 'Basic'");
        }
        String encodedCredentials = stringTokenizer.nextToken();
        if (encodedCredentials.isEmpty()) {
            throw new ServletException("Encoded credentials is empty");
        }
        String stringCredentials = new String(Base64.decode(encodedCredentials), "UTF-8");
        int p = stringCredentials.indexOf(":");
        if (p == -1) {
            throw new ServletException("Invalid registration header: stringCred.indexOf(':')==-1");
        }
        String name = stringCredentials.substring(0, p).trim();
        String pass = stringCredentials.substring(p + 1).trim();
        if (name.isEmpty() && pass.isEmpty()) {
            throw new ServletException("Empty username or user-password strings");
        }
        String other = stringTokenizer.nextToken();
        if (!other.equalsIgnoreCase("Other")) {
            throw new ServletException("There is no email & about data");
        }
        String mailAndAbout = new String(Base64.decode(stringTokenizer.nextToken()), "UTF-8");
        Integer z = mailAndAbout.indexOf(":");
        if (z == -1) {
            throw new ServletException("Invalid registration header: mailAndAbout.indexOf(':')==-1");
        }
        String email = mailAndAbout.substring(0, z).trim();
        String about = mailAndAbout.substring(z + 1).trim();
        if (!email.matches("^[\\w-\\.]+@[\\w-]+(\\.[\\w-]+)*\\.[a-z]{2,}$")) {
            throw new ServletException("nonUniqueEmail");
        }
        if (about.isEmpty()) {
            about = "-";
        }
        return new User(id, name, pass, email, about);
    }
}
