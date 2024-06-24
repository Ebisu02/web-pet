package com.service;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.json.JSONObject;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class PostService {
    public static void postStatus(PrintWriter out, String status) {
        if (out.equals(null)) {
            return;
        }
        JSONObject jo = new JSONObject();
        jo.put("status", status);
        out.println(jo.toString());
    }

    public static void postExceptionStatus(PrintWriter out, String status, Exception e) {
        JSONObject jo = new JSONObject();
        jo.put("status", "failed");
        jo.put("exception", e);
        out.println(jo.toString());
    }

    public static void setAuthorizationTokenInCookie(String encodedCredentials, HttpServletResponse resp) {
        String strToEncode = "true:" + encodedCredentials;
        Cookie cookie = new Cookie("isAuthorized", Base64.encode(strToEncode.getBytes()));
        resp.addCookie(cookie);
    }

    public static void sendProfileDataAsJSON(PrintWriter out, String name, String email, String about) {
        if (out.equals(null)) {
            return;
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", name);
        jsonObject.put("mail", email);
        jsonObject.put("i", about);
        out.println(jsonObject.toString());
    }
}
