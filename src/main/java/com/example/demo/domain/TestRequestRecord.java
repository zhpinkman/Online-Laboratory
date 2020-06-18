package com.example.demo.domain;

import com.example.demo.domain.statusEnums.TestRequestPaymentStatus;
import com.example.demo.domain.statusEnums.TestRequestRecordStatus;

import java.util.Date;
import java.util.List;

public class TestRequestRecord {
    private TestRequestRecordStatus testRequestRecordStatus;
    private Phlebotomist phlebotomist;
    private String testFollowUpCode;
    private Date phlebotomistReferDate;
    private TestRequestPaymentStatus testRequestPaymentStatus;
    private Address address;
    private boolean insuranceRequest;
    private List<TestDesc> testDescList;




    public double getTotalPrice() {
        return 0;
    }
    public double getEstimatedTimeToBeDone() { return 0; }
    public String getPhlebotomistInfo() { return phlebotomist.getInfo(); }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setTestDescList(List<TestDesc> testDescList) {
        this.testDescList = testDescList;
    }
}
