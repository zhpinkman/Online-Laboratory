package com.example.demo.domain;

import java.util.List;

public class Patient extends User {
    private List<Address> addresses;
    private int patientPriority;
    private String insuranceCode;
    private List<TestRequestRecord> testRequestRecordList;
    private List<Prescription> prescriptions;


    public List<Prescription> getPrescriptions() {
        return prescriptions;
    }

    public List<Address> getAddresses() {
        return addresses;
    }
}
