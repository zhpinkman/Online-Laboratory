package com.example.demo.domain;

public class TestDesc {
    private String testName;
    private int testPriority;
    private boolean withoutPrescriptionSupport;
    private boolean insuranceSupport;
    private String preTestChecks;
    private String testChecks;

    public TestDesc(String testName, int testPriority, boolean withoutPrescriptionSupport, boolean insuranceSupport, String preTestChecks, String testChecks) {
        this.testName = testName;
        this.testPriority = testPriority;
        this.withoutPrescriptionSupport = withoutPrescriptionSupport;
        this.insuranceSupport = insuranceSupport;
        this.preTestChecks = preTestChecks;
        this.testChecks = testChecks;
    }

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
