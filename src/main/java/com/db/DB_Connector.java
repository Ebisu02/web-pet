package com.db;

import java.sql.*;

public class DB_Connector {
    public static Connection con;
    public static Statement statmt;
    public static ResultSet resSet;
    private final static String DB_USERS_PATH = "C:/Program Files/Tomcat/db/users.db";

    private static Connection connect(String path) throws ClassNotFoundException, SQLException {
        con = null;
        Class.forName("org.sqlite.JDBC");
        con = DriverManager.getConnection("jdbc:sqlite:" + path);
        //System.out.println("Connected!");
        return con;
    }

    public static Connection connect_to_users() throws ClassNotFoundException, SQLException {
        return connect(DB_USERS_PATH);
    }
}
