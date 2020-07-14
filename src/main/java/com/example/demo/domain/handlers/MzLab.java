package com.example.demo.domain.handlers;

import com.example.demo.domain.*;
import com.example.demo.domain.handlers.LabHandler;
import com.example.demo.domain.handlers.PatientHandler;
import com.example.demo.domain.statusEnums.PrescriptionStatus;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MzLab {

    private List<TestDesc> testDescs;

    private PatientHandler patientHandler = new PatientHandler();
    private LabHandler labHandler = new LabHandler();

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

    public List<Prescription> getReviewedPrescriptions(String patientEmail) throws Exception {
        return patientHandler.getReviewedPrescritions(patientEmail);
    }

    public void setPatientTests(String patientEmail, List<TestDesc> testDescs) throws Exception {
        for (TestDesc testDesc: testDescs) {
            if (!testExists(testDesc)) {
                throw new Exception("Test not available");
            }
        }
        patientHandler.setPatientsTest(patientEmail, testDescs);
    }

    public List<Address> getPatientAddresses(String patientEmail) throws Exception {
        return patientHandler.getPatientAddresses(patientEmail);
    }

    public void setTestRequestRecordAddress(String patientEmail, Address address) throws Exception {
        patientHandler.setTestRecordRequestAddress(patientEmail, address);
    }

    public void attachPrescriptionToTest(String patientEmail, String prescriptionId) throws Exception {
        patientHandler.attachPrescriptionToTest(patientEmail, prescriptionId);
    }

    public List<Lab> verifyPatientTestRequest(String patientEmail) throws Exception {
        TestRequestRecord testRequestRecord = patientHandler.verifyPatientTestRequest(patientEmail);
        return getLabsWithFullSupport(testRequestRecord);
    }

    public List<Lab> getLabsWithFullSupport(TestRequestRecord testRequestRecord) {
        return labHandler.getLabsWithFullSupport(testRequestRecord);
    }

    public FullTestInfo setSelectedLabForTests(String patientEmail, String labName) throws Exception {
        FullTestInfo fullTestInfo = new FullTestInfo();
        Lab selectedLab = labHandler.getLab(labName);
        fullTestInfo.setLabName(selectedLab.getName());
        TestRequestRecord testRequestRecord = patientHandler.setSelectedLabForTests(patientEmail, selectedLab);
        List<LabTest> labTestList = labHandler.getLabTests(labName, testRequestRecord);
        fullTestInfo.setLabTestList(labTestList);
        return fullTestInfo;
    }

    public List<Date> confirmTestInfo(String patientEmail) throws Exception {
        TestRequestRecord testRequestRecord = patientHandler.confirmTestRequest(patientEmail);
        return labHandler.findRecommendedTimes(testRequestRecord);
    }


    public double selectTimeForTest(String patientEmail, Date date) throws Exception { // it should return the amount patient should pay
        TestRequestRecord testRequestRecord = patientHandler.setTimeForTest(patientEmail, date);
        labHandler.assignPhlebotomistToTest(testRequestRecord);
        return patientHandler.getTotalPrice(patientEmail);
    }

    public void confirmPaymentReceipt(String patientEmail) throws Exception {
        patientHandler.confirmPaymentReceipt(patientEmail);
    }

}
