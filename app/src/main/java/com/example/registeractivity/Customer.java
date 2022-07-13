package com.example.registeractivity;

public class Customer {
    String id;
    String name;
    String phone;
    String amt;


    public Customer() {

        //this is required

    }

    public Customer(String id, String name, String phone, String amt) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.amt = amt;
    }

    public Customer(String id, String name, String amt) {
        this.id = id;
        this.name = name;
        this.amt = amt;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getAmt() {
        return amt;
    }
}

