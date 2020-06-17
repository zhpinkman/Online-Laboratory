package com.example.demo.domain.handlers;

import com.example.demo.domain.TestDesc;
import com.example.demo.domain.handlers.LabHandler;
import com.example.demo.domain.handlers.PatientHandler;

import java.util.List;

public class MzLab {

    private List<TestDesc> testDescs;

    private PatientHandler patientHandler = new PatientHandler();
    private LabHandler labHandler = new LabHandler();



    public List<TestDesc> getAllTests() {
        return testDescs;
    }



}
