package com.example.demo.domain;

import java.util.Date;

public class TestRequestRecord {
    private TestRequestRecordStatus testRequestRecordStatus;
    private Phlebotomist phlebotomist;
    private String testFollowUpCode;
    private Date phlebotomistReferDate;
    private TestRequestPaymentStatus testRequestPaymentStatus;
    private boolean insuranceRequest;




    private double getTotalPrice() {
        return 0;
    }
    private double getEstimatedTimeToBeDone() { return 0; }
    private String getPhlebotomistInfo() { return phlebotomist.getInfo(); }
    

}
