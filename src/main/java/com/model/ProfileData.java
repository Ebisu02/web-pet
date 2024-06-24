package com.model;

public class ProfileData {
    private String email;
    private String name;
    private String about;

    public ProfileData(String name, String email, String about) {
        this.name = name;
        this.email = email;
        this.about = about;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getAbout() {
        return about;
    }
}
