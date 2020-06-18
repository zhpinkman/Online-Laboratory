package com.example.demo.domain;

public class LabTest {
    private double price;
    private TestDesc labTestDesc;

    public boolean matches(TestDesc testDesc) {
        return labTestDesc.matches(testDesc);
    }
}
