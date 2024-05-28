package com.model;

public class User {
    private final int uId;
    private String uName;
    private String uPass;
    private String uMail;
    private String uAbout;

    public User(int uId, String name, String pass, String mail) {
        this.uId = uId;
        this.uMail = mail;
        this.uName = name;
        this.uPass = pass;
        this.uAbout = "Информация не указана";
    }

    public User(int uId, String name, String pass, String mail, String about) {
        this.uId = uId;
        this.uMail = mail;
        this.uName = name;
        this.uPass = pass;
        this.uAbout = about;
    }

    public void changePass(String newPass) {
        this.uPass = newPass;
    }

    public void changeName(String newName) {
        this.uName = newName;
    }

    public void changeAbout(String newAbout) {
        this.uAbout = newAbout;
    }

    public void changeMail(String newMail) {
        this.uMail = newMail;
    }

    public String getAbout() {
        return uAbout;
    }

    public String getMail() {
        return uMail;
    }

    public String getPass() {
        return uPass;
    }

    public String getName() {
        return uName;
    }
}
