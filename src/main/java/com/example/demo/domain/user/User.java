package com.example.demo.domain.user;

public class User {
    private String name;
    private String username;
    private String userEmail;
    private String password;

    public User(String name, String username, String userEmail, String password) {
        this.name = name;
        this.userEmail = userEmail;
        this.username = username;
        this.password = password;
    }

    public boolean emailsMatch(String patientEmail) {
        return userEmail.equals(patientEmail);
    }

    public String getName() {
        return name;
    }
}
