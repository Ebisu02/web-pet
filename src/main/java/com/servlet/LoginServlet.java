package com.servlet;

import com.db.DB_Connector;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import com.sun.tools.javac.util.Log;
import javax.servlet.http.Cookie;
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
import java.util.Random;
import java.util.StringTokenizer;
import java.util.TimeZone;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = /*req.getContextPath() + */ "/jsp/login/login.jsp";
        getServletContext().getRequestDispatcher(path).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String r_authHeader = req.getHeader("Authorization");
        PrintWriter out = resp.getWriter();
        if (r_authHeader != null) {
            StringTokenizer stringTokenizer = new StringTokenizer(r_authHeader);
            if (stringTokenizer.hasMoreTokens()) {
                String basic = stringTokenizer.nextToken();
                if (basic.equalsIgnoreCase("Basic")) {
                    try {
                        String encodedCredentials = stringTokenizer.nextToken();
                        String credentials = new String(Base64.decode(encodedCredentials), "UTF-8");
                        int p = credentials.indexOf(":");
                        if (p != -1) {
                            String r_uname = credentials.substring(0, p).trim();
                            String r_pwd = credentials.substring(p + 1).trim();
                            if (!(r_uname.isEmpty() && r_pwd.isEmpty())) {
                                HttpSession session = req.getSession();
                                resp.addHeader("Access-Control-Allow-Origin", "*");
                                Boolean loggedIn = false;
                                Statement stmt = null;
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
                                        String strToEncode = "true:" + encodedCredentials;
                                        out.println(jo.toString());
                                        Cookie cookie = new Cookie("isAuthorized", Base64.encode(strToEncode.getBytes()));
                                        resp.addCookie(cookie);
                                        loggedIn = true;
                                        break;
                                    }
                                }
                                if (!loggedIn) {
                                    JSONObject jo = new JSONObject();
                                    jo.put("status", "failed");
                                    out.println(jo.toString());
                                }
                            }
                        } else {
                            throw new Exception("Invalid authentication token:'" + credentials + "'");
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
    }
}
