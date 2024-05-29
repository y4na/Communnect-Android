package com.example.communnect;

import java.util.ArrayList;
import java.util.List;

public class HelperClass {

    String firstname, lastname, email, password;
    public List<String> bookmarks;


    public HelperClass(String firstname, String lastname, String email, String password) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.bookmarks = new ArrayList<>();
    }

    public String getName() {
        return firstname;
    }

    public void setName(String name) {
        this.firstname = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public HelperClass() {
    }
}

