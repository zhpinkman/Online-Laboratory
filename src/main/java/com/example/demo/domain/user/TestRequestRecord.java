package com.example.demo.domain.user;

import com.example.demo.domain.externalAPIs.InsuranceAPI;
import com.example.demo.domain.lab.Lab;
import com.example.demo.domain.lab.Phlebotomist;
import com.example.demo.domain.lab.PhlebotomistInfo;
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
    private PhlebotomistInfo phlebotomistInfo;
    private Date phlebotomistReferDate;
    private TestRequestPaymentStatus testRequestPaymentStatus;
    private Address address;
    private List<TestDesc> testDescList;
    private Prescription attachedPrescription;
    private String selectedLabName;

    TestRequestRecord() {
        testRequestPaymentStatus = TestRequestPaymentStatus.NOT_PAYED;
        testRequestRecordStatus = TestRequestRecordStatus.TEST_INITIATED;
    }

    public TestRequestRecordStatus getTestRequestRecordStatus() {
        return testRequestRecordStatus;
    }

    public void setSelectedLabName(String selectedLabName) throws Exception {
        if (!testRequestRecordStatus.equals(TestRequestRecordStatus.TESTS_CONFIRMD)) {
            throw new Exception("incorrect order!");
        }
        this.selectedLabName = selectedLabName;
        testRequestRecordStatus = TestRequestRecordStatus.LAB_SELECTED;
        System.out.println("test's lab have been selected");
    }

    public Address getAddress() {
        return address;
    }

    public List<TestDesc> getTestDescList() {
        return testDescList;
    }


    public Receipt getTotalPrice(boolean insuranceVerified, boolean insuranceSupport, int reductionFactor, List<Double> prices) throws Exception {
        Receipt receipt = new Receipt();
        double totalPrice = 0;
        for (int i = 0;i < testDescList.size(); i++) {
            double price = prices.get(i);
            if (insuranceVerified && insuranceSupport && testDescList.get(i).getInsuranceSupport()) {
                price *= (double) (100 - reductionFactor) /100;
            }
            totalPrice += price;
            receipt.addToReceipt(new ReceiptItem(testDescList.get(i).getTestName(), price));
        }
        receipt.setTotalAmount(totalPrice);
        return receipt;
    }


    public PhlebotomistInfo getPhlebotomistInfo() { return phlebotomistInfo; }

    public void setAddress(Address address) throws Exception {
        if (address == null) {
            throw new Exception("you have to select a address");
        }
        if (!testRequestRecordStatus.equals(TestRequestRecordStatus.TESTS_ITEMS_SELECTED)) {
            throw new Exception("incorrect order!");
        }
        this.address = address;
        testRequestRecordStatus = TestRequestRecordStatus.ADDRESS_SELECTED;
        System.out.println("test's address have been selected");
    }

    public void setTestDescList(List<TestDesc> testDescList) throws Exception {
        if (testDescList.size() == 0) {
            throw new Exception("you have to select some tests");
        }
        this.testDescList = testDescList;
        testRequestRecordStatus = TestRequestRecordStatus.TESTS_ITEMS_SELECTED;
        System.out.println("tests have been selected");
    }

    public void attachPrescription(Prescription prescription) throws Exception {
        if (prescription == null) {
            throw new Exception("you have to select a prescription");
        }
        if (!testRequestRecordStatus.equals(TestRequestRecordStatus.ADDRESS_SELECTED)) {
            throw new Exception("incorrect order");
        }
        attachedPrescription = prescription;
        testRequestRecordStatus = TestRequestRecordStatus.PRESCRIPTION_ADDED;
        System.out.println("prescription added");
    }

    public void verifyCorrectness() throws Exception {
        if (!testRequestRecordStatus.equals(TestRequestRecordStatus.ADDRESS_SELECTED) && !testRequestRecordStatus.equals(TestRequestRecordStatus.PRESCRIPTION_ADDED)) {
            throw new Exception("incorrect order!!");
        }
        for (TestDesc testDesc: testDescList) {
            if (testDesc.getNeedsPrescription()) {
                if (!attachedPrescription.prescriptionIncludes(testDesc) || attachedPrescription == null) {
                    throw new Exception("test needs prescription but have not attached");
                }
            }
        }
        testRequestRecordStatus = TestRequestRecordStatus.TESTS_CONFIRMD;
    }

    public void confirmRequest() throws Exception {
        if (!testRequestRecordStatus.equals(TestRequestRecordStatus.LAB_SELECTED)) {
            throw new Exception("incorrect order!");
        }
        testRequestRecordStatus = TestRequestRecordStatus.INFO_CONFIRMED;
        System.out.println("test's info have been confirmed");
    }

    public String getSelectedLabName() {
        return selectedLabName;
    }

    public void setPhlebotomistInfo(PhlebotomistInfo phlebotomistInfo) {
        this.phlebotomistInfo = phlebotomistInfo;
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

    public Prescription getPrescription() {
        return attachedPrescription;
    }
}
