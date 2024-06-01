package com.servlet;

import com.db.DB_Connector;
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

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = /*req.getContextPath() + */ "/jsp/login/login.jsp";
        getServletContext().getRequestDispatcher(path).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String r_uname = req.getParameter("uname");
        String r_pwd = req.getParameter("pwd");
        if (!(r_uname.isEmpty() && r_pwd.isEmpty())) {
            HttpSession session = req.getSession();
            resp.addHeader("Access-Control-Allow-Origin", "*");
            PrintWriter out = resp.getWriter();
            Boolean loggedIn = false;
            Statement stmt = null;
            try {
                Connection con = DB_Connector.connect_to_users();
                stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM users");
                while (rs.next()) {
                    String uname = rs.getString("uName");
                    String pwd = rs.getString("uPass");
                    if (r_uname.equals(uname) && r_pwd.equals(pwd)) {
                        session.setAttribute("uname", uname);
                        JSONObject jo = new JSONObject();
                        jo.put("status", "success");
                        jo.put("uname", uname);
                        out.println(jo.toString());
                        loggedIn = true;
                        break;
                    }
                }
                if (!loggedIn) {
                    JSONObject jo = new JSONObject();
                    jo.put("status", "failed");
                    out.println(jo.toString());
                }
            } catch (Exception e) {
                JSONObject jo = new JSONObject();
                jo.put("status", "failed");
                jo.put("exception", e);
                out.println(jo.toString());
            }
        }
    }
}
