package com.example.demo.domain.user;

import com.example.demo.domain.externalAPIs.InsuranceAPI;
import com.example.demo.domain.lab.Lab;
import com.example.demo.domain.lab.Phlebotomist;
import com.example.demo.domain.lab.TestDesc;
import com.example.demo.domain.statusEnums.TestRequestPaymentStatus;
import com.example.demo.domain.statusEnums.TestRequestRecordStatus;
import com.example.demo.domain.utility.Address;
import com.example.demo.domain.utility.Receipt;
import com.example.demo.domain.utility.ReceiptItem;

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
        System.out.println("test's lab have been selected");
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

    public Receipt getTotalPrice(boolean insuranceVerified, String insuranceCompany) throws Exception {
        Receipt receipt = new Receipt();
        double totalPrice = 0;
        for (TestDesc testDesc: testDescList) {
            double price = selectedLab.getTestPrice(testDesc);
            if (insuranceVerified && selectedLab.supportInsurance(insuranceCompany) && testDesc.getInsuranceSupport()) {
                int reductionFactor = InsuranceAPI.getInsuranceCompanyRedcutionFactor(insuranceCompany);
                price *= (double) (100 - reductionFactor) /100;
            }
            totalPrice += price;
            receipt.addToReceipt(new ReceiptItem(testDesc.getTestName(), price));
        }
        receipt.setTotalAmount(totalPrice);
        return receipt;
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
            if (testDesc.getNeedsPrescription()) {
                if (!attachedPrescription.prescriptionIncludes(testDesc)) {
                    throw new Exception("test needs prescription but have not attached");
                }
            }
        }
        testRequestRecordStatus = TestRequestRecordStatus.TESTS_CONFIRMD;
    }

    public void confirmRequest() throws Exception {
        if (!testRequestRecordStatus.equals(TestRequestRecordStatus.LAB_SELECTED)) {
            throw new Exception("incorrect order! first you should select lab for your tests");
        }
        testRequestRecordStatus = TestRequestRecordStatus.INFO_CONFIRMED;
        System.out.println("test's info have been confirmed");
    }

    public Lab getSelectedLab() {
        return selectedLab;
    }

    public void setPhlebotomist(Phlebotomist phlebotomist) {
        this.phlebotomist = phlebotomist;
        testRequestRecordStatus = TestRequestRecordStatus.PHLEBOTOMIST_ASSIGNED;
        System.out.println("phlebotomist have been assigned");
    }

    public void setPhlebotomistReferDate(Date phlebotomistReferDate) throws Exception {
        if (!testRequestRecordStatus.equals(TestRequestRecordStatus.INFO_CONFIRMED)) {
            throw new Exception("incorrect order! you ought to confirm your test info first");
        }
        this.phlebotomistReferDate = phlebotomistReferDate;
        testRequestRecordStatus = TestRequestRecordStatus.TIME_SELECTED_WAITING_FOR_PHLEBOTOMIST;
    }

    public void setPaymentDone() throws Exception {
        if (!testRequestRecordStatus.equals(TestRequestRecordStatus.WAITING_FOR_PAYMENT)) {
            throw new Exception("incorrect order!");
        }
        testRequestPaymentStatus = TestRequestPaymentStatus.PAYED;
        testRequestRecordStatus = TestRequestRecordStatus.PAYMENT_DONE;
    }

    public void setWaitingForPayment() {
        testRequestRecordStatus = TestRequestRecordStatus.WAITING_FOR_PAYMENT;
    }
}
