package com.example.demo.domain;

import java.util.List;

public class PatientTestInfo {
    private String name;
    private int patientPriority;
    private List<Prescription> prescriptions;
    private TestRequestRecord testRequestRecord;

    public void setPatientGeneralInfo(String name, int patientPriority, List<Prescription> prescriptions) {
        this.name = name;
        this.patientPriority = patientPriority;
        this.prescriptions = prescriptions;
    }

    public void setTestRequestRecord(TestRequestRecord testRequestRecord) {
        this.testRequestRecord = testRequestRecord;
    }
}
