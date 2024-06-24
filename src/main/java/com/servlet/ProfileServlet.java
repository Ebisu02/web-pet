package com.servlet;

import com.model.ProfileData;
import com.repository.Connector;
import com.service.PostService;
import com.service.UserService;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

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
                    String name = UserService.getUsername(encodedCookie);
                    ProfileData profileData = UserService.getProfileDataByUsername(name);
                    PostService.sendProfileDataAsJSON(out, profileData.getName(), profileData.getEmail(), profileData.getAbout());
                }
            }
        } catch(Exception e) {
            PostService.postExceptionStatus(out, "failed", e);
        }
    }
}
