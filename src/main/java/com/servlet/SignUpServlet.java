package com.servlet;

import com.db.DB_Connector;
import com.model.User;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
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
import java.util.StringTokenizer;

@WebServlet("/signup")
public class SignUpServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = /*req.getContextPath() +*/ "/jsp/signup/signup.jsp";
        getServletContext().getRequestDispatcher(path).forward(req, resp);
    }

    // 0. Get data from Registration header
    // 1. Get next uId
    // 2. Check if name&email unique
    // 3. Create statement to add it to db table users
    // 4. Set user as signed up (send status==success)
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String r_regHeader = req.getHeader("Registration");
        PrintWriter out = resp.getWriter();
        if (r_regHeader != null) {
            StringTokenizer stringTokenizer = new StringTokenizer(r_regHeader);
            if (stringTokenizer.hasMoreTokens()) {
                String basic = stringTokenizer.nextToken();
                if (basic.equalsIgnoreCase("Basic")) {
                    try {
                        String credentials = new String(Base64.decode(stringTokenizer.nextToken()));
                        String other = stringTokenizer.nextToken();
                        if (other.equalsIgnoreCase("Other")) {
                            String mailAndAbout = new String(Base64.decode(stringTokenizer.nextToken()));
                            int p = credentials.indexOf(":"), z = mailAndAbout.indexOf(":");
                            if (p != -1 && z != -1) {
                                String r_uname = credentials.substring(0, p).trim();
                                String r_pwd = credentials.substring(p + 1).trim();
                                String r_email = mailAndAbout.substring(0, z).trim();
                                String r_about = mailAndAbout.substring(z + 1).trim();
                                if (!(r_uname.isEmpty() && r_pwd.isEmpty() && r_email.isEmpty())) {
                                    if (r_about.isEmpty()) {
                                        r_about = "-";
                                    }
                                    HttpSession session = req.getSession();
                                    resp.addHeader("Access-Control-Allow-Origin", "*");
                                    Boolean signedUp = false;
                                    Statement stmt = null;
                                    Connection con = DB_Connector.connect_to_users();
                                    stmt = con.createStatement();
                                    // Get next uId
                                    ResultSet inc = stmt.executeQuery("SELECT * FROM users ORDER BY uId DESC LIMIT 1");
                                    Integer count = 1;
                                    if (inc.next()) {
                                        count = inc.getInt("uId");
                                    }
                                    count++;
                                    // Check if name & email are unique
                                    ResultSet rs = stmt.executeQuery("SELECT uName FROM users");
                                    do {
                                        String uname = rs.getString("uName");
                                        if (r_uname.equals(uname)) {
                                            con.close();
                                            throw new Exception("nonUniqueName");
                                        }
                                    } while(rs.next());
                                    rs = stmt.executeQuery("SELECT uMail FROM users");
                                    do {
                                        String umail = rs.getString("uMail");
                                        if (r_email.equals(umail)) {
                                            con.close();
                                            throw new Exception("nonUniqueEmail");
                                        }
                                    } while (rs.next());
                                    // Create statement to add it to db table users
                                    stmt.executeUpdate("INSERT INTO users VALUES (" + count
                                            + ",'" + r_uname + "','" + r_pwd + "','" + r_email + "','" + r_about + "')");
                                    // Set user as signed up (send status==success)
                                    signedUp = true;
                                    JSONObject jo = new JSONObject();
                                    jo.put("status", "success");
                                    jo.put("name", r_uname);
                                    jo.put("pwd", r_pwd);
                                    jo.put("name", r_email);
                                    jo.put("name", r_about);
                                    out.println(jo.toString());
                                    con.close();
                                }
                            }
                        }
                    } catch(Exception e) {
                        String msg = e.getMessage();
                        JSONObject jo = new JSONObject();
                        switch (msg) {
                            case "nonUniqueName":
                                jo.put("status", "nonUniqueName");
                                out.println(jo.toString());
                                break;
                            case "nonUniqueEmail":
                                jo.put("status", "nonUniqueEmail");
                                out.println(jo.toString());
                                break;
                            default:
                                jo.put("status", "failed");
                                jo.put("exception", e);
                                out.println(jo.toString());
                                break;
                        }
                    }
                }
            }
        }
    }
}
