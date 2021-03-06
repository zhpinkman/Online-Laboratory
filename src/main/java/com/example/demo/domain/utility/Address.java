package com.example.demo.domain.utility;

public class Address {
    private double latitude;
    private double longitude;
    private String rawAddress;

    public Address(double latitude, double longitude, String rawAddress) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.rawAddress = rawAddress;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getRawAddress() {
        return rawAddress;
    }
}
