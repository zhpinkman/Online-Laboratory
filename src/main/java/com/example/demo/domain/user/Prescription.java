package com.example.demo.domain.user;

import com.example.demo.domain.lab.TestDesc;
import com.example.demo.domain.statusEnums.PrescriptionStatus;

import java.util.List;

public class Prescription {
    private String id;
    private String rawData;
    private List<TestDesc> testDescs;
    private PrescriptionStatus prescriptionStatus;

    public Prescription(String id, String rawData, List<TestDesc> testDescs, PrescriptionStatus prescriptionStatus) {
        this.id = id;
        this.rawData = rawData;
        this.testDescs = testDescs;
        this.prescriptionStatus = prescriptionStatus;
    }


    public PrescriptionStatus getPrescriptionStatus() {
        return prescriptionStatus;
    }

    public String getId() {
        return id;
    }

    public List<TestDesc> getTestDescs() {
        return testDescs;
    }

    public String getRawData() {
        return rawData;
    }

    public boolean idMatches(String prescriptionId) {
        return prescriptionId.equals(id);
    }

    public boolean prescriptionIncludes(TestDesc testDesc) {
        for (TestDesc testDesc1: testDescs) {
            if (testDesc.matches(testDesc1)) {
                return true;
            }
        }
        return false;
    }
}
