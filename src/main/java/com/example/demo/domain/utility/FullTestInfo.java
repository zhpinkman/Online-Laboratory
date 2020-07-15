package com.example.demo.domain.utility;

import com.example.demo.domain.lab.LabTest;

import java.util.List;

public class FullTestInfo {
    private String labName;
    private List<String> insuranceCompanies;
    private List<LabTest> labTestList;


    public void setLabName(String labName) {
        this.labName = labName;
    }
    public void setLabTestList(List<LabTest> labTestList) {
        this.labTestList = labTestList;
    }

    public void setInsuranceCompanies(List<String> insuranceCompanies) {
        this.insuranceCompanies = insuranceCompanies;
    }

    public List<LabTest> getLabTestList() {
        return labTestList;
    }

    public String getLabName() {
        return labName;
    }

    public List<String> getInsuranceCompanies() {
        return insuranceCompanies;
    }
}
