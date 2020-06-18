package com.example.demo.domain.handlers;

import com.example.demo.domain.*;
import com.example.demo.domain.externalAPIs.InsuranceAPI;
import com.example.demo.domain.statusEnums.PrescriptionStatus;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class PatientHandler {

    private List<Patient> patientList;


    private Patient getPatient(String patientEmail) throws Exception {
        for (Patient patient: patientList) {
            if (patient.emailsMatch(patientEmail)) {
                return patient;
            }
        }
        throw new Exception("user not available");
    }

    public List<Prescription> getReviewedPrescritions(String patientEmail) throws Exception {
        Patient patient = getPatient(patientEmail);
        List<Prescription> patientPrescriptions = patient.getPrescriptions();
        List<Prescription> reviewedOnes = new ArrayList<>();
        for (Prescription prescription: patientPrescriptions) {
            if (prescription.getPrescriptionStatus().equals(PrescriptionStatus.REVIEWED)) {
                reviewedOnes.add(prescription);
            }
        }
        return reviewedOnes;
    }

    public List<Address> getPatientAddresses(String patientEmail) throws Exception {
        Patient patient = getPatient(patientEmail);
        return patient.getAddresses();
    }

    public void setTestRecordRequestAddress(String patientEmail, Address address) throws Exception {
        Patient patient = getPatient(patientEmail);
        patient.setTestRecordRequestAddress(address);
    }

    public void setPatientsTest(String patientEmail, List<TestDesc> testDescs) throws Exception {
        Patient patient = getPatient(patientEmail);
        patient.setTests(testDescs);
    }

    public void attachPrescriptionToTest(String patientEmail, String prescriptionId) throws Exception {
        Patient patient = getPatient(patientEmail);
        patient.attachPrescriptionToTest(prescriptionId);
    }

    public TestRequestRecord verifyPatientTestRequest(String patientEmail) throws Exception {
        Patient patient = getPatient(patientEmail);
        TestRequestRecord testRequestRecord = patient.getCurrentTestRequestRecord();
        testRequestRecord.verifyCorrectness();
        return testRequestRecord;
    }

    public void setSelectedLabForTests(String patientEmail, Lab selectedLab) throws Exception {
        Patient patient = getPatient(patientEmail);
        patient.setSelectedLabForTests(selectedLab);
    }
}
