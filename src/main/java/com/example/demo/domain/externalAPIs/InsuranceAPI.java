package com.example.demo.domain.externalAPIs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InsuranceAPI {

    private static List<InsuranceCompany> insuranceCompanyList = new ArrayList<>(
            Arrays.asList(new InsuranceCompany("bime", 80)));


    public static boolean verifyCode(String code) {
        if (code == null) {
            System.out.println("no code for verification");
            return false;
        }
        System.out.println("code verified");
        return true;
    }

    public static int getInsuranceCompanyRedcutionFactor(String companyName){
        for (InsuranceCompany insuranceCompany: insuranceCompanyList) {
            if (insuranceCompany.namesMatches(companyName)) {
                return insuranceCompany.getReductionFactor();
            }
        }
        return 0;
    }
}
