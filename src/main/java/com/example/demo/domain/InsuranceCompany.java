package com.example.demo.domain;

public class InsuranceCompany {
    private String companyName;
    private int reductionFactor;

    public boolean namesMatches(String insuranceCompanyName) {
        return companyName.equals(insuranceCompanyName);
    }


    public int getReductionFactor() {
        return reductionFactor;
    }
}
