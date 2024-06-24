package com.servlet;

import com.service.ContentService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ImageServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getPathInfo();
        if (path != null && path.startsWith("/images/")) {
            ContentService.serveImage(getServletContext(), response, path);
        } else {
            ContentService.serveHtml(response);
        }
    }
}
