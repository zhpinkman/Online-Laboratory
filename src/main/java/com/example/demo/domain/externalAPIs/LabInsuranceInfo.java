package com.example.demo.domain.externalAPIs;

import java.util.List;

public class LabInsuranceInfo {
    private String labName;
    private List<String> supportedInsurances;


    public LabInsuranceInfo(String labName, List<String> supportedInsurances) {
        this.labName = labName;
        this.supportedInsurances = supportedInsurances;
    }

    public String getLabName() {
        return labName;
    }

    public List<String> getSupportedInsurances() {
        return supportedInsurances;
    }
}
