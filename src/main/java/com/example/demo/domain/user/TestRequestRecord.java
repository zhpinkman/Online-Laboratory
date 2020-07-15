package com.example.demo.domain.user;

import com.example.demo.domain.externalAPIs.InsuranceAPI;
import com.example.demo.domain.lab.Lab;
import com.example.demo.domain.lab.Phlebotomist;
import com.example.demo.domain.lab.TestDesc;
import com.example.demo.domain.statusEnums.TestRequestPaymentStatus;
import com.example.demo.domain.statusEnums.TestRequestRecordStatus;
import com.example.demo.domain.utility.Address;

import java.util.Date;
import java.util.List;

public class TestRequestRecord {
    private TestRequestRecordStatus testRequestRecordStatus;
    private Phlebotomist phlebotomist;
    private String testFollowUpCode;
    private Date phlebotomistReferDate;
    private TestRequestPaymentStatus testRequestPaymentStatus;
    private Address address;
    private List<TestDesc> testDescList;
    private Prescription attachedPrescription;
    private Lab selectedLab;

    TestRequestRecord() {
        testRequestPaymentStatus = TestRequestPaymentStatus.NOT_PAYED;
        testRequestRecordStatus = TestRequestRecordStatus.TEST_INITIATED;
    }

    public TestRequestRecordStatus getTestRequestRecordStatus() {
        return testRequestRecordStatus;
    }

    public void setSelectedLab(Lab selectedLab) throws Exception {
        if (!testRequestRecordStatus.equals(TestRequestRecordStatus.TESTS_CONFIRMD)) {
            throw new Exception("incorrect order! first you have to confirm tests");
        }
        this.selectedLab = selectedLab;
        testRequestRecordStatus = TestRequestRecordStatus.LAB_SELECTED;
    }

    public Address getAddress() {
        return address;
    }

    public List<TestDesc> getTestDescList() {
        return testDescList;
    }

    public Phlebotomist getPhlebotomist() {
        return phlebotomist;
    }

    public double getTotalPrice(boolean insuranceVerified, String insuranceCompany) throws Exception {
        double totalPrice = 0;
        for (TestDesc testDesc: testDescList) {
            double rawPrice = selectedLab.getTestPrice(testDesc);
            if (insuranceVerified && selectedLab.supportInsurance(insuranceCompany) && testDesc.getInsuranceSupport()) {
                int reductionFactor = InsuranceAPI.getInsuranceCompanyRedcutionFactor(insuranceCompany);
                totalPrice += rawPrice*(100 - reductionFactor);
            } else {
                totalPrice += rawPrice;
            }
        }
        testRequestRecordStatus = TestRequestRecordStatus.WAITING_FOR_PAYMENT;
        return totalPrice;
    }


    public double getEstimatedTimeToBeDone() { return 0; }
    public String getPhlebotomistInfo() { return phlebotomist.getInfo(); }

    public void setAddress(Address address) throws Exception {
        if (!testRequestRecordStatus.equals(TestRequestRecordStatus.TESTS_ITEMS_SELECTED)) {
            throw new Exception("incorrect order!");
        }
        this.address = address;
        testRequestRecordStatus = TestRequestRecordStatus.ADDRESS_SELECTED;
        System.out.println("test's address have been selected");
    }

    public void setTestDescList(List<TestDesc> testDescList) {
        this.testDescList = testDescList;
        testRequestRecordStatus = TestRequestRecordStatus.TESTS_ITEMS_SELECTED;
        System.out.println("tests have been selected");
    }

    public void attachPrescription(Prescription prescription) throws Exception {
        if (!testRequestRecordStatus.equals(TestRequestRecordStatus.ADDRESS_SELECTED)) {
            throw new Exception("incorrect order");
        }
        attachedPrescription = prescription;
        System.out.println("prescription added");
    }

    public void verifyCorrectness() throws Exception {
        if (address == null) {
            throw new Exception("address is null");
        }
        for (TestDesc testDesc: testDescList) {
            if (testDesc.getNeedsInsurance()) {
                if (!attachedPrescription.prescriptionIncludes(testDesc)) {
                    throw new Exception("test needs prescription but have not attached");
                }
            }
        }
        testRequestRecordStatus = TestRequestRecordStatus.TESTS_CONFIRMD;
    }

    public void confirmRequest() {
        testRequestRecordStatus = TestRequestRecordStatus.CONFIRMED;
    }

    public Lab getSelectedLab() {
        return selectedLab;
    }

    public void setPhlebotomist(Phlebotomist phlebotomist) {
        this.phlebotomist = phlebotomist;
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
