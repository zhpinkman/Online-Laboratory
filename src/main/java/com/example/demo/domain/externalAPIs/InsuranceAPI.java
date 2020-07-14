package com.example.demo.domain.externalAPIs;

import com.example.demo.domain.InsuranceCompany;

import java.util.List;

public class InsuranceAPI {

    private static List<InsuranceCompany> insuranceCompanyList;

    public static boolean verifyCode(String code) {
        if (code == null) {
            System.out.println("no code for verification");
            return false;
        }
        System.out.println("code verified");
        return true;
    }

    public static int getInsuranceCompanyRedcutionFactor(String companyName) throws Exception {
        for (InsuranceCompany insuranceCompany: insuranceCompanyList) {
            if (insuranceCompany.namesMatches(companyName)) {
                return insuranceCompany.getReductionFactor();
            }
        }
        throw new Exception("insurance company does not exists");
    }
}
