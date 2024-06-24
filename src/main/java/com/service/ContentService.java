package com.service;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ContentService {
    // Posting image to servlet context
    public static void serveImage(ServletContext context, HttpServletResponse response, String path) throws IOException {
        File imageFile = new File("/images/" + path.substring("/images/".length()));
        if (imageFile.exists()) {
            response.setContentType(context.getMimeType(imageFile.getName()));
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

    // Posting html-page to servlet context
    public static void serveHtml(HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        try (OutputStream out = response.getOutputStream()) {
            String htmlContent = "<html><body><h1>Welcome to the Image Server</h1></body></html>";
            out.write(htmlContent.getBytes("UTF-8"));
        }
    }
}
