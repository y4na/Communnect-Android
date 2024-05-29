package com.example.communnect;

import java.util.ArrayList;

public class User {
    private String name;
    private String email;
    private boolean isLoggedIn;

    // Constructor, getters, setters

    public void setLoggedIn(boolean loggedIn) {
        isLoggedIn = loggedIn;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    // Update user's logged-in status in the authentication process
    public void loginUser(String email, String password) {
        // Authenticate user
        // If authentication is successful, set user's logged-in status to true
        setLoggedIn(true);
    }

    public void logoutUser() {
        // Set user's logged-in status to false
        setLoggedIn(false);
    }

    // Retrieve data based on the logged-in status
    public static User getLoggedInUser(ArrayList<User> userList) {
        for (User user : userList) {
            if (user.isLoggedIn()) {
                // This is the logged-in user
                return user;
            }
        }
        return null; // No user is logged in
    }
}
