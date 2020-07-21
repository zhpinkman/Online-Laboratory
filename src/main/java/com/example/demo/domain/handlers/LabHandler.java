package com.example.demo.domain.handlers;

import com.example.demo.domain.externalAPIs.InsuranceAPI;
import com.example.demo.domain.externalAPIs.InsuranceCompany;
import com.example.demo.domain.externalAPIs.LabInsuranceInfo;
import com.example.demo.domain.lab.*;
import com.example.demo.domain.utility.Address;
import com.example.demo.domain.utility.FullTestInfo;
import com.example.demo.domain.utility.PatientTestInfo;

import java.util.*;

public class LabHandler {

    private List<Lab> labList = new ArrayList<>();

    public LabHandler() {
        Lab lab1 = new Lab("lab1", new Address(100, 200, "baqalia"));
        Lab lab2 = new Lab("lab2", new Address(200, 300, "baqalia2"));

//        lab repository

        LabRepository labRepository1 = new LabRepository("repo", "LDfaLk");
        lab1.setLabRepositories(labRepository1);

        LabRepository labRepository2 = new LabRepository("repo2", "LDfaLk2");
        lab1.setLabRepositories(labRepository2);

//        insuranceCompany

        List<InsuranceCompany> insuranceCompanies = new ArrayList<InsuranceCompany>(Arrays.asList(new InsuranceCompany("bime", 80)));
        lab1.setSupportedInsurances(insuranceCompanies);
        InsuranceAPI.addLabInfo(new LabInsuranceInfo(lab1.getName(), new ArrayList<>()));
//        InsuranceAPI.addLabInfo(new LabInsuranceInfo(lab1.getName(), new ArrayList<>(Arrays.asList("bime"))));
        lab2.setSupportedInsurances(insuranceCompanies);
        InsuranceAPI.addLabInfo(new LabInsuranceInfo(lab2.getName(), new ArrayList<>()));

//        lab tests

        TestDesc testDesc1 = new TestDesc("test1", 1, false, true, "nothing", "nothing");
        TestDesc testDesc2 = new TestDesc("test2", 2, false, true, "nothing", "nothing");
        TestDesc testDesc3 = new TestDesc("test3", 3, false, true, "nothing", "nothing");
        TestDesc testDesc4 = new TestDesc("test4", 2, false, true, "nothing", "nothing");

        LabTest labTest1 = new LabTest(2000, testDesc1);
        LabTest labTest2 = new LabTest(3000, testDesc2);
        LabTest labTest3 = new LabTest(4000, testDesc3);
        LabTest labTest4 = new LabTest(4000, testDesc4);

//        List<LabTest> labTestList = new ArrayList<LabTest>(Arrays.asList(labTest1, labTest2, labTest3, labTest4));
        List<LabTest> labTestList = new ArrayList<LabTest>(Arrays.asList(labTest1, labTest2, labTest3));
        lab1.setLabTests(labTestList);
        List<LabTest> labTestList2 = new ArrayList<LabTest>(Arrays.asList(labTest2, labTest3, labTest4));
        lab2.setLabTests(labTestList2);


//        phlebotomists

        List<Phlebotomist> phlebotomistList= new ArrayList<Phlebotomist>(Arrays.asList(
                new Phlebotomist("phle", "phle", "mohsen.fayyaz77@gmail.com", "temp", "hdAni")));

        lab1.setPhlebotomistList(phlebotomistList);
        lab2.setPhlebotomistList(new ArrayList<>());

        labList.add(lab1);
        labList.add(lab2);
    }

    public Lab getLab(String labName) throws Exception {
        for (Lab lab: labList) {
            if (lab.namesMatches(labName)) {
                return lab;
            }
        }
        throw new Exception("lab not found");
    }

    public List<Lab> getLabsWithFullSupport(List<TestDesc> testDescList) throws Exception {
        if (labList.size() == 0) {
            throw new Exception("no labs available yet");
        }
        List<Lab> labsWithFullSupport = new ArrayList<>();
        for (Lab lab: labList) {
            if (lab.supportTests(testDescList)) {
                labsWithFullSupport.add(lab);
            }
        }
        if (labsWithFullSupport.size() == 0) {
            throw new Exception("there is no lab that supports all tests");
        }
        return labsWithFullSupport;
    }

    public List<LabTest> getLabTests(String labName, List<TestDesc> testDescList) throws Exception {
        Lab lab = getLab(labName);
        return lab.getLabTests(testDescList);
    }

    public List<Date> findRecommendedTimes() {
        Date today = new Date();
        Date tomorrow = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(tomorrow);
        c.add(Calendar.DATE, 1);
        tomorrow = c.getTime();
        return new ArrayList<>(Arrays.asList(today, tomorrow));
    }

    public PhlebotomistInfo assignPhlebotomistToTest(String labName) throws Exception {
        Lab lab = getLab(labName);
        return lab.assignPhlebotomistToTest();
    }

    public void prepareKitForRequest(String labName, List<TestDesc> testDescList) throws Exception {
        Lab lab = getLab(labName);
        lab.prepareKit(testDescList);
    }

    public FullTestInfo getFullTestInfo(String labName, List<LabTest> labTestList) throws Exception {
        Lab lab = getLab(labName);
        FullTestInfo fullTestInfo = new FullTestInfo();
        fullTestInfo.setLabName(lab.getName());
        fullTestInfo.setLabTestList(labTestList);
        fullTestInfo.setInsuranceCompanies(lab.getSupportedInsurancesNames());
        return fullTestInfo;
    }

    public List<FullTestInfo> getLabsWithFullSupportInfo(List<TestDesc> testDescList) throws Exception {
        List<FullTestInfo> fullTestInfos = new ArrayList<>();
        List<Lab> labList = getLabsWithFullSupport(testDescList);
        for (Lab lab: labList) {
            fullTestInfos.add(getFullTestInfo(lab.getName(), getLabTests(lab.getName(), testDescList)));
        }
        return fullTestInfos;
    }

    public void addTestToPhlebotomistSchedule(PhlebotomistInfo phlebotomistInfo, PatientTestInfo patientInfo) throws Exception {
        Phlebotomist phlebotomist = getPhlebotomist(phlebotomistInfo);
        phlebotomist.addTestRecordToList(patientInfo);
    }

    private Phlebotomist getPhlebotomist(PhlebotomistInfo phlebotomistInfo) throws Exception {
        String labName = phlebotomistInfo.getLabName();
        Lab lab = getLab(labName);
        return lab.getPhlebotomist(phlebotomistInfo.getName());
    }

    public List<Double> getTestPrices(String labName, List<TestDesc> testDescs) throws Exception {
        Lab lab = getLab(labName);
        return lab.getTestPrices(testDescs);
    }
}
