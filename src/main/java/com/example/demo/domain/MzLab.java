package com.example.demo.domain;

import com.example.demo.domain.handlers.LabHandler;
import com.example.demo.domain.handlers.PatientHandler;

public class MzLab {
    private PatientHandler patientHandler = new PatientHandler();
    private LabHandler labHandler = new LabHandler();
}
