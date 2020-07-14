package com.example.demo.domain.lab;

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

    public boolean matches(String testName) {
        return this.testName.equals(testName);
    }

    public boolean getNeedsInsurance() {
        return !withoutPrescriptionSupport;
    }

    public boolean getInsuranceSupport() {
        return insuranceSupport;
    }

    public String getTestName() {
        return testName;
    }

    public int getTestPriority() {
        return testPriority;
    }

    public String getPreTestChecks()
    {
        return preTestChecks;
    }

    public String getTestChecks()
    {
        return testChecks;
    }

}
