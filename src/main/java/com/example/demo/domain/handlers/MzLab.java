package com.example.demo.domain.handlers;

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
        List<Prescription> patientPrescriptions = patient.getPrescriptions();
        List<Prescription> reviewedOnes = new ArrayList<>();
        for (Prescription prescription: patientPrescriptions) {
            if (prescription.getPrescriptionStatus().equals(PrescriptionStatus.REVIEWED)) {
                reviewedOnes.add(prescription);
            }
        }
        return reviewedOnes;
    }

}
