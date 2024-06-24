package com.repository;

import java.sql.*;

public class Connector {
    public static Connection con;
    private final static String DB_USERS_PATH = "C:/Program Files/Tomcat/db/users.db";
    private final static String DB_RECIPES_PATH = "C:/Program Files/Tomcat/db/recipes.db";

    private static Connection connect(String path) throws ClassNotFoundException, SQLException {
        con = null;
        Class.forName("org.sqlite.JDBC");
        con = DriverManager.getConnection("jdbc:sqlite:" + path);
        //System.out.println("Connected!");
        return con;
    }

    // Returns a connection to table users
    public static Connection connect_to_users() throws ClassNotFoundException, SQLException {
        return connect(DB_USERS_PATH);
    }

    // Return a connection to table recipes
    public static Connection connect_to_recipes() throws ClassNotFoundException, SQLException {
        return connect(DB_RECIPES_PATH);
    }
}
