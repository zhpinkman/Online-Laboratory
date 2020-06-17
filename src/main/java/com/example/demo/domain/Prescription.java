package com.example.demo.domain;

import com.example.demo.domain.statusEnums.PrescriptionStatus;

import java.util.List;

public class Prescription {
    private String rawData;
    private List<TestDesc> testDescs;
    private PrescriptionStatus prescriptionStatus;


    public PrescriptionStatus getPrescriptionStatus() {
        return prescriptionStatus;
    }
}
