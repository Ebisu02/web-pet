package com.servlet;

import com.model.Credentials;
import com.service.PostService;
import com.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private String encodedCredentials;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = /*req.getContextPath() + */ "/jsp/login/login.jsp";
        getServletContext().getRequestDispatcher(path).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.addHeader("Access-Control-Allow-Origin", "*");
        String authHeader = req.getHeader("Authorization");
        PrintWriter out = resp.getWriter();
        if (authHeader == null) {
            throw new ServletException("AuthHeader is null");
        }
        try {
            StringTokenizer stringTokenizer = new StringTokenizer(authHeader);
            Credentials credentials = UserService.getCredentials(stringTokenizer);
            HttpSession session = req.getSession();
            Boolean loggedIn = UserService.login(credentials.getName(), credentials.getPass());
            if (!loggedIn) {
                PostService.postStatus(out, "failed");
            }
            session.setAttribute("uname", credentials.getName());
            PostService.postStatus(out, "success");
            PostService.setAuthorizationTokenInCookie(UserService.getEncodedCredentials(), resp);
        } catch (Exception e) {
            PostService.postExceptionStatus(out, "failed", e);
        }
    }
}
