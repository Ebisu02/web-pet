package com.service;

import com.google.gson.Gson;
import com.model.Recipe;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.json.JSONObject;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.ArrayList;

public class PostService {
    public static final String STATUS_SUCCESS = "success";
    public static final String STATUS_FAILED = "failed";

    // Post status in PrintWriter like {status:"your_status"}
    public static void postStatus(PrintWriter out, String status) {
        if (out.equals(null)) {
            return;
        }
        JSONObject jo = new JSONObject();
        jo.put("status", status);
        out.println(jo.toString());
    }

    // Post status in PrintWriter like {status:"your_status", exception:"exception.msg"}
    public static void postExceptionStatus(PrintWriter out, String status, Exception e) {
        JSONObject jo = new JSONObject();
        jo.put("status", status);
        jo.put("exception", e);
        out.println(jo.toString());
    }

    // Sets authorization token in cookie
    public static void setAuthorizationTokenInCookie(String encodedCredentials, HttpServletResponse resp) {
        String strToEncode = "true:" + encodedCredentials;
        Cookie cookie = new Cookie("isAuthorized", Base64.encode(strToEncode.getBytes()));
        resp.addCookie(cookie);
    }

    // Sending profile data as json: {name:"name",mail:"mail",about:"i"}
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

    // Posting ArrayList<Recipe> as json object using Gson
    public static void postRecipeList(PrintWriter out, ArrayList<Recipe> recipes) {
        if (out.equals(null)) {
            return;
        }
        if (recipes.equals(null) || recipes.isEmpty()) {
            return;
        }
        Gson gson = new Gson();
        String json = gson.toJson(recipes);
        out.println(json);
    }
}
