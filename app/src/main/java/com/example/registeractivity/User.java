package com.example.registeractivity;

public class User {
    String id;
    String user;
    String phone;
    String email;
    String pass;
    public User() {

    }

    public User(String id, String user, String phone, String email, String pass) {
        this.id = id;
        this.user = user;
        this.phone = phone;
        this.email = email;
        this.pass = pass;
    }

    public String getId() {
        return id;
    }

    public String getUser() {
        return user;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getPass() {
        return pass;
    }
}
