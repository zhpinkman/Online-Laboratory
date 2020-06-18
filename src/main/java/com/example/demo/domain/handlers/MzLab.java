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

    public List<Prescription> getReviewedPrescriptions(String patientEmail) {
        return patientHandler.getReviewedPrescritions(patientEmail);
    }

    public void setPatientTests(String patientEmail, List<TestDesc> testDescs) {
        patientHandler.setPatientsTest(patientEmail, testDescs);
    }

    public List<Address> getPatientAddresses(String patientEmail) {
        return patientHandler.getPatientAddresses(patientEmail);
    }

    public void setTestRecordRequestAddress(String patientEmail, Address address) {
        patientHandler.setTestRecordRequestAddress(patientEmail, address);
    }

}
