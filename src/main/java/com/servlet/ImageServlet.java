package com.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ImageServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getPathInfo();
        if (path != null && path.startsWith("/images/")) {
            serveImage(response, path);
        } else {
            serveHtml(response);
        }
    }

    private void serveImage(HttpServletResponse response, String path) throws IOException {
        File imageFile = new File("/images/" + path.substring("/images/".length()));
        if (imageFile.exists()) {
            response.setContentType(getServletContext().getMimeType(imageFile.getName()));
            response.setContentLength((int) imageFile.length());
            try (FileInputStream in = new FileInputStream(imageFile); OutputStream out = response.getOutputStream()) {
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = in.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesRead);
                }
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404
        }
    }

    private void serveHtml(HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        try (OutputStream out = response.getOutputStream()) {
            String htmlContent = "<html><body><h1>Welcome to the Image Server</h1></body></html>";
            out.write(htmlContent.getBytes("UTF-8"));
        }
    }
}
