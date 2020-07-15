package com.example.demo.domain.externalAPIs;

public class InsuranceCompany {
    private String companyName;
    private int reductionFactor;

    public InsuranceCompany(String companyName, int reductionFactor) {
        this.companyName = companyName;
        this.reductionFactor = reductionFactor;
    }

    public boolean namesMatches(String insuranceCompanyName) {
        return companyName.equals(insuranceCompanyName);
    }

    public String getCompanyName() {
        return companyName;
    }

    public int getReductionFactor() {
        return reductionFactor;
    }
}
