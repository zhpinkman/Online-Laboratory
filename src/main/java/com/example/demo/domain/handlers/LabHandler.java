package com.example.demo.domain.handlers;

import com.example.demo.domain.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class LabHandler {

    private List<Lab> labList;

    public LabHandler() {
        Lab lab1 = new Lab("lab", new Address(100, 200, "baqalia"));
        LabRepository labRepository1 = new LabRepository("repo", "LDfaLk");
        lab1.setLabRepositories(labRepository1);
        List<InsuranceCompany> insuranceCompanies = new ArrayList<InsuranceCompany>(Arrays.asList(new InsuranceCompany("bime", 80)));
        lab1.setSupportedInsurances(insuranceCompanies);
        List<LabTest> labTestList = new ArrayList<>();

    }

    public Lab getLab(String labName) throws Exception {
        for (Lab lab: labList) {
            if (lab.namesMatches(labName)) {
                return lab;
            }
        }
        throw new Exception("lab not found");
    }

    public List<Lab> getLabsWithFullSupport(TestRequestRecord testRequestRecord) {
        List<Lab> labsWithFullSupport = new ArrayList<>();
        for (Lab lab: labList) {
            if (lab.supportTests(testRequestRecord.getTestDescList())) {
                labsWithFullSupport.add(lab);
            }
        }
        return labsWithFullSupport;
    }

    public List<LabTest> getLabTests(String labName, TestRequestRecord testRequestRecord) throws Exception {
        Lab lab = getLab(labName);
        return lab.getLabTests(testRequestRecord.getTestDescList());
    }

    public List<Date> findRecommendedTimes(TestRequestRecord testRequestRecord) {
        return null; // todo
    }

    public void assignPhlebotomistToTest(TestRequestRecord testRequestRecord) {
        Lab lab = testRequestRecord.getSelectedLab();
        lab.assignPhlebotomistToTest(testRequestRecord);
    }

    public void prepareKitForRequest(Lab lab, List<TestDesc> testDescList) {
        lab.prepareKit(testDescList);
    }

}
