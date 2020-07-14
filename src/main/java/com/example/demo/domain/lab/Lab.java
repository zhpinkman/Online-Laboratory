package com.example.demo.domain.lab;

import com.example.demo.domain.externalAPIs.InsuranceCompany;
import com.example.demo.domain.user.TestRequestRecord;
import com.example.demo.domain.utility.Address;

import java.util.ArrayList;
import java.util.List;

public class Lab {
    private String name;
    private Address labAddress;
    private LabRepository labRepositories;
    private List<LabTest> labTests;
    private List<InsuranceCompany> supportedInsurances;
    List<Phlebotomist> phlebotomistList;

    public void setPhlebotomistList(List<Phlebotomist> phlebotomistList) {
        this.phlebotomistList = phlebotomistList;
    }

    public Lab(String name, Address labAddress) {
        this.name = name;
        this.labAddress = labAddress;
    }

    public void setLabRepositories(LabRepository labRepositories) {
        this.labRepositories = labRepositories;
    }

    public void setLabTests(List<LabTest> labTests) {
        this.labTests = labTests;
    }

    public void setSupportedInsurances(List<InsuranceCompany> supportedInsurances) {
        this.supportedInsurances = supportedInsurances;
    }

    public boolean supportTests(List<TestDesc> testDescList) {
        for (TestDesc testDesc: testDescList) {
            if (!supportTest(testDesc)) {
                return false;
            }
        }
        return true;
    }

    private boolean supportTest(TestDesc testDesc) {
        for (LabTest labTest: labTests) {
            if (labTest.matches(testDesc)) {
                return true;
            }
        }
        return false;
    }

    public boolean namesMatches(String labName) {
        return name.equals(labName);
    }

    public String getName() {
        return name;
    }

    public Address getLabAddress() {
        return labAddress;
    }



    public List<LabTest> getLabTests(List<TestDesc> testDescList) {
        List<LabTest> labTestList = new ArrayList<>();
        for (LabTest labTest: labTests) {
            if (labTest.isIncluded(testDescList)) {
                labTestList.add(labTest);
            }
        }
        return labTestList;
    }

    public boolean supportInsurance(String insuranceCompanyName) {
        for (InsuranceCompany insuranceCompany: supportedInsurances) {
            if (insuranceCompany.namesMatches(insuranceCompanyName)) {
                return true;
            }
        }
        return false;
    }

    public double getTestPrice(TestDesc testDesc) throws Exception {
        for (LabTest labTest: labTests) {
            if (labTest.matches(testDesc)) {
                return labTest.getPrice();
            }
        }
        throw new Exception("lab test not found");
    }

    public void prepareKit(List<TestDesc> testDescList) {
        labRepositories.prepareKit(testDescList);
    }

    public void assignPhlebotomistToTest(TestRequestRecord testRequestRecord) {
        int minimumWork = Integer.MAX_VALUE;
        Phlebotomist lessOccupiedPhlebotomist = null;
        for (Phlebotomist phlebotomist: phlebotomistList) {
            if (phlebotomist.getAmountOfWork() < minimumWork) {
                minimumWork = phlebotomist.getAmountOfWork();
                lessOccupiedPhlebotomist = phlebotomist;
            }
        }
        testRequestRecord.setPhlebotomist(lessOccupiedPhlebotomist);
    }
}
