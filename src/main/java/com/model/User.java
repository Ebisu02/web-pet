package com.model;

public class User {
    private final Integer id;
    private final String name;
    private final String pass;
    private final String email;
    private final String about;

    public User(Integer id, String name, String pass, String email, String about) {
        this.id = id;
        this.name = name;
        this.pass = pass;
        this.email = email;
        this.about = about;
    }

    public String getAbout() {
        return about;
    }

    public String getEmail() {
        return email;
    }

    public String getPass() {
        return pass;
    }

    public String getName() {
        return name;
    }

    public Integer getId() {
        return id;
    }
}
