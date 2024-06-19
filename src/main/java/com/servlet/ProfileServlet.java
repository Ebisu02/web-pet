package com.servlet;

import com.db.DB_Connector;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = /*req.getContextPath() +*/ "/jsp/profile/profile.jsp";
        getServletContext().getRequestDispatcher(path).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        try {
            Cookie[] cookies = req.getCookies();
            String encodedCookie = "";
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("isAuthorized")) {
                    encodedCookie = cookie.getValue();
                    String name = getUsername(encodedCookie);
                    ArrayList<String> emailAndAbout = getEmailAndAbout(name);
                    sendProfileDataAsJSON(out, name, emailAndAbout.get(0), emailAndAbout.get(1));
                }
            }
        } catch(Exception e){
            out.println(e.getMessage());
        }
    }

    private void sendProfileDataAsJSON(PrintWriter out, String name, String email, String about) {
        if (out != null) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name", name);
            jsonObject.put("mail", email);
            jsonObject.put("i", about);
            out.println(jsonObject.toString());
        }
    }

    // 0 - Email
    // 1 - About
    private ArrayList<String> getEmailAndAbout(String username) {
        try {
            Connection con = DB_Connector.connect_to_users();
            PreparedStatement stmt = con.prepareStatement("SELECT uMail, uAbout FROM users WHERE uName = ?");
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            ArrayList<String> emailAndAbout = new ArrayList<String>();
            emailAndAbout.add(rs.getString("uMail"));
            emailAndAbout.add(rs.getString("uAbout"));
            return emailAndAbout;
        } catch (Exception e) {
            return null;
        }
    }

    private String getUsername(String encodedCookie) throws UnsupportedEncodingException {
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
