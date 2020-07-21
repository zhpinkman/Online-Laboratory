package com.example.demo.domain.externalAPIs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InsuranceAPI {

    private static List<InsuranceCompany> insuranceCompanyList = new ArrayList<>(
            Arrays.asList(new InsuranceCompany("bime", 80)));

    private static List<LabInsuranceInfo> labInsuranceInfoList = new ArrayList<>();

    public static void addLabInfo(LabInsuranceInfo labInsuranceInfo) {
        labInsuranceInfoList.add(labInsuranceInfo);
    }


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

    private static LabInsuranceInfo getLabInfo(String labName) throws Exception {
        for (LabInsuranceInfo labInsuranceInfo: labInsuranceInfoList) {
            if (labInsuranceInfo.getLabName().equals(labName)) {
                return labInsuranceInfo;
            }
        }
        throw new Exception("no such lab registered!!");
    }

    public static boolean SupportsLab(String selectedLabName, String insuranceCompany) throws Exception {
        LabInsuranceInfo labInsuranceInfo = getLabInfo(selectedLabName);
        for (String insuranceName: labInsuranceInfo.getSupportedInsurances()) {
            if (insuranceName.equals(insuranceCompany)) {
                return true;
            }
        }
        return false;
    }
}
