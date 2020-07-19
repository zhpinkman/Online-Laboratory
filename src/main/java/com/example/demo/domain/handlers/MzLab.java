package com.example.demo.domain.handlers;

import com.example.demo.domain.lab.*;
import com.example.demo.domain.user.Prescription;
import com.example.demo.domain.utility.Address;
import com.example.demo.domain.utility.FullTestInfo;
import com.example.demo.domain.utility.PatientTestInfo;
import com.example.demo.domain.utility.Receipt;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MzLab {

    public static final String patientEmail = "zhivarsourati@gmail.com";
    private List<TestDesc> testDescs = new ArrayList<>();

    private PatientHandler patientHandler = new PatientHandler();
    private LabHandler labHandler = new LabHandler();


    private MzLab() {
        initTests();
    }

    private static MzLab instance;

    public static MzLab getInstance() {
        if (instance == null) {
            instance = new MzLab();
            return instance;
        }
        return instance;
    }

    private void initTests() {
        TestDesc testDesc1 = new TestDesc("test1", 1, true, true, "nothing", "nothing");
        TestDesc testDesc2 = new TestDesc("test2", 2, true, true, "nothing", "nothing");
        TestDesc testDesc3 = new TestDesc("test3", 3, false, true, "nothing", "nothing");
        TestDesc testDesc4 = new TestDesc("test4", 2, false, true, "nothing", "nothing");

        this.testDescs.add(testDesc1);
        this.testDescs.add(testDesc2);
        this.testDescs.add(testDesc3);
        this.testDescs.add(testDesc4);
    }


    public List<TestDesc> getAllTests() throws Exception {
        if (testDescs.size() <= 0) {
            throw new Exception("there are no tests to be shown");
        }
        return testDescs;
    }

    private TestDesc getTestByName(String name) throws Exception {
        for (TestDesc testDesc: testDescs) {
            if (testDesc.matches(name)) {
                return testDesc;
            }
        }
        throw new Exception("Test not found -> " + name);
    }

    private List<TestDesc> getTestsByName(List<String> names) throws Exception {
        List<TestDesc> testDescs = new ArrayList<>();
        for (String name: names) {
            testDescs.add(getTestByName(name));
        }
        return testDescs;
    }

    public List<Prescription> getReviewedPrescriptions() throws Exception {
        return patientHandler.getReviewedPrescritions(patientEmail);
    }

    public void setPatientTests(List<String> testNames) throws Exception {
        List<TestDesc> testDescs = getTestsByName(testNames);
        patientHandler.setPatientsTest(patientEmail, testDescs);
    }

    public List<Address> getPatientAddresses() throws Exception {
        return patientHandler.getPatientAddresses(patientEmail);
    }

    public void setPatientTestAddress(Address address) throws Exception {
        patientHandler.setTestRecordRequestAddress(patientEmail, address);
    }

    public void attachPrescriptionToTest(String prescriptionId) throws Exception {
        patientHandler.attachPrescriptionToTest(patientEmail, prescriptionId);
    }

    public List<FullTestInfo> verifyPatientTestRequest() throws Exception {
        patientHandler.verifyPatientTestRequest(patientEmail);
        return getLabsWithFullSupport(patientHandler.getTestDescList(patientEmail));
    }

    public List<FullTestInfo> getLabsWithFullSupport(List<TestDesc> testDescList) throws Exception {
        return labHandler.getLabsWithFullSupportInfo(testDescList);
    }


    public FullTestInfo setSelectedLabForTests(String labName) throws Exception {
        patientHandler.setSelectedLabForTests(patientEmail, labName);
        List<LabTest> labTestList = labHandler.getLabTests(labName, patientHandler.getTestDescList(patientEmail));
        return labHandler.getFullTestInfo(labName, labTestList);
    }

    public List<Date> confirmTestInfo() throws Exception {
        patientHandler.confirmTestRequest(patientEmail);
        return labHandler.findRecommendedTimes();
    }


    public PhlebotomistInfo selectTimeForTest(Date date) throws Exception {
        patientHandler.setTimeForTest(patientEmail, date);
        String labName = patientHandler.getSelectedLabName(patientEmail);
        PhlebotomistInfo phlebotomistInfo = labHandler.assignPhlebotomistToTest(labName);
        patientHandler.setPhlebotomistInfo(patientEmail, phlebotomistInfo);
        return  phlebotomistInfo;
    }

    public Receipt getReceipt() throws Exception {
        List<TestDesc> testDescs = patientHandler.getTestDescList(patientEmail);
        String labName = patientHandler.getSelectedLabName(patientEmail);
        List<Double> prices = labHandler.getTestPrices(labName, testDescs);
        Receipt receipt = patientHandler.getTotalPrice(patientEmail, prices);
        patientHandler.setWaitingForPayment(patientEmail);
        return receipt;
    }

    public void confirmPaymentReceipt() throws Exception {
        patientHandler.confirmPaymentReceipt(patientEmail);
        PatientTestInfo patientInfo = patientHandler.getPatientInfo(patientEmail);
        PhlebotomistInfo phlebotomistInfo = patientHandler.getPatientsCurrentTestPhlebotomist(patientEmail);
        labHandler.addTestToPhlebotomistSchedule(phlebotomistInfo, patientInfo);
        System.out.println("patient info sent to phlebotomist");
        String labName = patientHandler.getSelectedLabName(patientEmail);
        List<TestDesc> testDescList = patientHandler.getTestDescList(patientEmail);
        labHandler.prepareKitForRequest(labName, testDescList);
    }

}
