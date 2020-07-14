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

//        lab repository

        LabRepository labRepository1 = new LabRepository("repo", "LDfaLk");
        lab1.setLabRepositories(labRepository1);

//        insuranceCompany

        List<InsuranceCompany> insuranceCompanies = new ArrayList<InsuranceCompany>(Arrays.asList(new InsuranceCompany("bime", 80)));
        lab1.setSupportedInsurances(insuranceCompanies);

//        lab tests

        TestDesc testDesc1 = new TestDesc("test1", 1, false, true, "nothing", "nothing");
        TestDesc testDesc2 = new TestDesc("test2", 2, false, true, "nothing", "nothing");
        TestDesc testDesc3 = new TestDesc("test3", 3, false, true, "nothing", "nothing");
        TestDesc testDesc4 = new TestDesc("test4", 2, false, true, "nothing", "nothing");

        LabTest labTest1 = new LabTest(2000, testDesc1);
        LabTest labTest2 = new LabTest(3000, testDesc2);
        LabTest labTest3 = new LabTest(4000, testDesc3);
        LabTest labTest4 = new LabTest(4000, testDesc4);

        List<LabTest> labTestList = new ArrayList<LabTest>(Arrays.asList(labTest1, labTest2, labTest3, labTest4));

        lab1.setLabTests(labTestList);

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
