package com.example.demo.domain.handlers;

import com.example.demo.domain.Address;
import com.example.demo.domain.Patient;
import com.example.demo.domain.Prescription;
import com.example.demo.domain.TestDesc;
import com.example.demo.domain.handlers.LabHandler;
import com.example.demo.domain.handlers.PatientHandler;
import com.example.demo.domain.statusEnums.PrescriptionStatus;

import java.util.ArrayList;
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
                throw new Exception("test not available");
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

}
