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

    public List<Prescription> getReviewedPrescriptions(Patient patient) {
        return patientHandler.getReviewedPrescritions(patient);
    }

    public List<Address> getPatientAddresses(Patient patient) {
        return patientHandler.getPatientAddresses(patient);
    }

}
