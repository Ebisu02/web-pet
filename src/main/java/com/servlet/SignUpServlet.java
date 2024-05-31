package com.servlet;

import com.db.DB_Connector;
import com.model.User;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

@WebServlet("/signup")
public class SignUpServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = "/webapp/jsp/signup.jsp";
        getServletContext().getRequestDispatcher(path).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String r_uname = req.getParameter("uname");
        String r_pwd = req.getParameter("pwd");
        String r_pwdCheck = req.getParameter("pwdCheck");
        String r_email = req.getParameter("email");
        String r_about = req.getParameter("about");
        HttpSession session = req.getSession();
        resp.addHeader("Access-Control-Allow-Origin", "*");
        PrintWriter out = resp.getWriter();
        Boolean signedUp = false;
        Statement stmt = null;
        // 0. Check if pwd = pwdCheck
        // 1. Get next uId
        // 2. Check if name unique
        // 3. Create statement to add it to db table users
        // 4. Set user as signed up
        // 0
        if (!r_pwd.equals(r_pwdCheck)) {
            JSONObject jo = new JSONObject();
            jo.put("status", "pwdEqualsIssue");
            out.println(jo.toString());
            // TODO handle when pwd != pwdCheck into ReactApp
        }
        try {
            Connection con = DB_Connector.connect_to_users();
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM users");
            // 1
            ResultSet inc = stmt.executeQuery("SELECT * FROM users ORDER BY uId DESC LIMIT 1");
            Integer count = 1;
            if (inc.next()) {
                count = inc.getInt("uId");
            }
            count++;
            // 2
            while (rs.next()) {
                String uname = rs.getString("uName");
                if (r_uname.equals(uname)) {
                    JSONObject jo = new JSONObject();
                    jo.put("status", "nonUniqueName");
                    out.println(jo.toString());
                    break;
                }
            }
            // 3
            User user = new User(
               count,
               r_uname,
               r_pwd,
               r_email,
               r_about
            );
            stmt.executeQuery("INSERT users VALUES (" + count
                    + ",'" + r_uname + "','" + r_pwd + "','" + r_email + "','" + r_about + "')");
            // 4
            signedUp = true;
            // TODO authorise process
        } catch (Exception e) {
            JSONObject jo = new JSONObject();
            jo.put("status", "failed");
            jo.put("exception", e);
            out.println(jo.toString());
        }
    }
}
