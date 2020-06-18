package com.example.demo.domain;

public class User {
    private String name;
    private String username;
    private String userEmail;
    private String password;


    public boolean emailsMatch(String patientEmail) {
        return userEmail.equals(patientEmail);
    }
}
