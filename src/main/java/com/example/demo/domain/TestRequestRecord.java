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
//    private boolean insuranceRequest; todo not sure about usage of this one
    private List<TestDesc> testDescList;
    private Prescription attachedPrescription;


    public Address getAddress() {
        return address;
    }

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

    public void attachPrescription(Prescription prescription) {
        attachedPrescription = prescription;
    }

    public void verifyCorrectness() throws Exception {
        if (address == null) {
            throw new Exception("address is null");
        }
        for (TestDesc testDesc: testDescList) {
            if (testDesc.needsInsurance()) {
                if (!attachedPrescription.prescriptionIncludes(testDesc)) {
                    throw new Exception("test needs prescription but not attached");
                }
            }
        }
    }
}
