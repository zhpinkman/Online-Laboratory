package com.example.demo.domain;

import com.example.demo.domain.externalAPIs.InsuranceAPI;
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
    private Lab selectedLab;

    TestRequestRecord() {
        testRequestPaymentStatus = TestRequestPaymentStatus.NOT_PAYED;
        testRequestRecordStatus = TestRequestRecordStatus.TEST_INITIATED;
    }


    public void setSelectedLab(Lab selectedLab) {
        this.selectedLab = selectedLab;
        testRequestRecordStatus = TestRequestRecordStatus.WAITING_TO_BE_CONFIRMED;
    }

    public Address getAddress() {
        return address;
    }

    public List<TestDesc> getTestDescList() {
        return testDescList;
    }

    public double getTotalPrice(boolean insuranceVerified, String insuranceCompany) throws Exception {
        double totalPrice = 0;
        for (TestDesc testDesc: testDescList) {
            double rawPrice = selectedLab.getTestPrice(testDesc);
            if (insuranceVerified && selectedLab.supportInsurance(insuranceCompany) && testDesc.getInsuranceSupport()) {
                int reductionFactor = InsuranceAPI.getInsuranceCompanyRedcutionFactor(insuranceCompany);
                totalPrice += rawPrice*reductionFactor;
            } else {
                totalPrice += rawPrice;
            }
        }
        testRequestRecordStatus = TestRequestRecordStatus.WAITING_FOR_PAYMENT;
        return totalPrice;
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

    public void confirmRequest() {
        testRequestRecordStatus = TestRequestRecordStatus.CONFIRMED;
    }

    public Lab getSelectedLab() {
        return selectedLab;
    }

    public void setPhlebotomistReferDate(Date phlebotomistReferDate) {
        this.phlebotomistReferDate = phlebotomistReferDate;
        testRequestRecordStatus = TestRequestRecordStatus.TIME_SELECTED;
    }

    public void setPaymentDone() {
        testRequestPaymentStatus = TestRequestPaymentStatus.PAYED;
        testRequestRecordStatus = TestRequestRecordStatus.PAYMENT_DONE;
    }
}
