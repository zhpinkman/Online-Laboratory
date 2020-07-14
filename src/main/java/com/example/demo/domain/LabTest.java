package com.example.demo.domain;

import java.util.List;

public class LabTest {
    private double price;
    private TestDesc labTestDesc;

    public LabTest(double price, TestDesc labTestDesc) {
        this.price = price;
        this.labTestDesc = labTestDesc;
    }

    public boolean matches(TestDesc testDesc) {
        return labTestDesc.matches(testDesc);
    }

    public boolean isIncluded(List<TestDesc> testDescList) {
        for (TestDesc testDesc: testDescList) {
            if (testDesc.matches(labTestDesc)) {
                return true;
            }
        }
        return false;
    }

    public double getPrice() {
        return price;
    }
}
