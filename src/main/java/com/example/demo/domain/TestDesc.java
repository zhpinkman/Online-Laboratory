package com.example.demo.domain;

public class TestDesc {
    private String testName;
    private int TestPriority;
    private boolean withoutPrescriptionSupport;
    private boolean insuranceSupport;
    private String preTestChecks;
    private String testChecks;

    public boolean matches(TestDesc testDesc1) {
        return testDesc1.testName.equals(testName);
    }

    public boolean needsInsurance() {
        return !withoutPrescriptionSupport;
    }

    public boolean getInsuranceSupport() {
        return insuranceSupport;
    }
}
