package com.example.demo.domain;

import java.util.List;

public class Patient extends User {
    private Address address;
    private int patientPriority;
    private String insuranceCode;
    private List<TestRequestRecord> testRequestRecordList;
    
}
