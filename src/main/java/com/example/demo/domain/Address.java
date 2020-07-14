package com.example.demo.domain;

public class Address {
    private double latitude;
    private double longitude;
    private String rawAddress;

    public Address(double latitude, double longitude, String rawAddress) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.rawAddress = rawAddress;
    }
}
