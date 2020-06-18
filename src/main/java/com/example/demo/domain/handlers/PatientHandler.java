package com.example.demo.domain.handlers;

import com.example.demo.domain.Address;
import com.example.demo.domain.Patient;
import com.example.demo.domain.Prescription;
import com.example.demo.domain.statusEnums.PrescriptionStatus;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class PatientHandler {

    private List<Patient> patientList;


    private Patient getPatient(String patientEmail) {
        for (Patient patient: patientList) {
            if (patient.emailsMatch(patientEmail)) {
                return patient;
            }
        }
        return null;
    }

    public List<Prescription> getReviewedPrescritions(String patientEmail) {
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

    public List<Address> getPatientAddresses(String patientEmail) {
        Patient patient = getPatient(patientEmail);
        return patient.getAddresses();
    }

    public void setTestRecordRequestAddress(String patientEmail, Address address) {
        Patient patient = getPatient(patientEmail);
        patient.setTestRecordRequestAddress(address);
    }
}
