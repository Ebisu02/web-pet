package com.model;

public class Credentials {
    String pass;
    String name;

    public Credentials(String name, String pass) {
        this.pass = pass;
        this.name = name;
    }

    public String getPass() {
        return pass;
    }

    public String getName() {
        return name;
    }
}
