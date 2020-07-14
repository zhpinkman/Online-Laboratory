package com.example.demo.domain.handlers;

import com.example.demo.domain.lab.Lab;
import com.example.demo.domain.lab.LabTest;
import com.example.demo.domain.lab.Phlebotomist;
import com.example.demo.domain.lab.TestDesc;
import com.example.demo.domain.user.Prescription;
import com.example.demo.domain.user.TestRequestRecord;
import com.example.demo.domain.utility.Address;
import com.example.demo.domain.utility.FullTestInfo;
import com.example.demo.domain.utility.PatientTestInfo;

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


    public List<TestDesc> getAllTests() {
        return testDescs;
    }

    private boolean testExists(TestDesc testDesc) {
        for (TestDesc testDesc1: testDescs) {
            if (testDesc.matches(testDesc1)) {
                return true;
            }
        }
        return false;
    }

    public List<Prescription> getReviewedPrescriptions() throws Exception {
        return patientHandler.getReviewedPrescritions(patientEmail);
    }

    public void setPatientTests(List<TestDesc> testDescs) throws Exception {
        for (TestDesc testDesc: testDescs) {
            if (!testExists(testDesc)) {
                throw new Exception("Test not available");
            }
        }
        patientHandler.setPatientsTest(patientEmail, testDescs);
    }

    public List<Address> getPatientAddresses() throws Exception {
        return patientHandler.getPatientAddresses(patientEmail);
    }

    public void setTestRequestRecordAddress(Address address) throws Exception {
        patientHandler.setTestRecordRequestAddress(patientEmail, address);
    }

    public void attachPrescriptionToTest(String prescriptionId) throws Exception {
        patientHandler.attachPrescriptionToTest(patientEmail, prescriptionId);
    }

    public List<Lab> verifyPatientTestRequest() throws Exception {
        TestRequestRecord testRequestRecord = patientHandler.verifyPatientTestRequest(patientEmail);
        return getLabsWithFullSupport(testRequestRecord);
    }

    public List<Lab> getLabsWithFullSupport(TestRequestRecord testRequestRecord) {
        return labHandler.getLabsWithFullSupport(testRequestRecord);
    }

    private FullTestInfo getFullTestInfo(String labName, List<LabTest> labTestList) {
        FullTestInfo fullTestInfo = new FullTestInfo();
        fullTestInfo.setLabName(labName);
        fullTestInfo.setLabTestList(labTestList);
        return fullTestInfo;
    }

    public FullTestInfo setSelectedLabForTests(String labName) throws Exception {
        Lab selectedLab = labHandler.getLab(labName);
        TestRequestRecord testRequestRecord = patientHandler.setSelectedLabForTests(patientEmail, selectedLab);
        List<LabTest> labTestList = labHandler.getLabTests(labName, testRequestRecord);
        return getFullTestInfo(labName, labTestList);
    }

    public List<Date> confirmTestInfo() throws Exception {
        TestRequestRecord testRequestRecord = patientHandler.confirmTestRequest(patientEmail);
        return labHandler.findRecommendedTimes(testRequestRecord);
    }


    public double selectTimeForTest(Date date) throws Exception { // it should return the amount patient should pay
        TestRequestRecord testRequestRecord = patientHandler.setTimeForTest(patientEmail, date);
        labHandler.assignPhlebotomistToTest(testRequestRecord);
        return patientHandler.getTotalPrice(patientEmail);
    }

    public void confirmPaymentReceipt() throws Exception {
        patientHandler.confirmPaymentReceipt(patientEmail);
        PatientTestInfo patientInfo = patientHandler.getPatientInfo(patientEmail);
        Phlebotomist phlebotomist = patientHandler.getPatientsCurrentTestPhlebotomist(patientEmail);
        phlebotomist.addTestRecordToList(patientInfo);
        System.out.println("patient info sent to phlebotomist");
        Lab lab = patientHandler.getSelectedLab(patientEmail);
        List<TestDesc> testDescList = patientHandler.getTestDescList(patientEmail);
        labHandler.prepareKitForRequest(lab, testDescList);
    }

}
