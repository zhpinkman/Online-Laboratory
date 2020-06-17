package com.example.demo.domain.handlers;

import com.example.demo.domain.Address;
import com.example.demo.domain.Patient;
import com.example.demo.domain.Prescription;
import com.example.demo.domain.statusEnums.PrescriptionStatus;

import java.util.ArrayList;
import java.util.List;

public class PatientHandler {
    public List<Prescription> getReviewedPrescritions(Patient patient) {
        List<Prescription> patientPrescriptions = patient.getPrescriptions();
        List<Prescription> reviewedOnes = new ArrayList<>();
        for (Prescription prescription: patientPrescriptions) {
            if (prescription.getPrescriptionStatus().equals(PrescriptionStatus.REVIEWED)) {
                reviewedOnes.add(prescription);
            }
        }
        return reviewedOnes;
    }

    public List<Address> getPatientAddresses(Patient patient) {
        return patient.getAddresses();
    }
}
