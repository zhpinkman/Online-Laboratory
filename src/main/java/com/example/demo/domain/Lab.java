package com.example.demo.domain;

import java.util.List;

public class Lab {
    private String name;
    private Address labAddress;
    private List<LabRepository> labRepositories;
    private List<LabTest> labTests;
    private List<InsuranceCompany> supportedInsurances;

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
}
