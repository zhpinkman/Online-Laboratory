package com.example.demo.controllers;


import com.example.demo.domain.user.Prescription;
import com.example.demo.domain.lab.TestDesc;
import com.example.demo.domain.handlers.MzLab;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/test")
public class MzLabController {

    @GetMapping("")
    public List<TestDesc> getTestDescList() {
        return MzLab.getInstance().getAllTests();
    }

    @GetMapping("/prescriptions")
    public List<Prescription> getPatientPrescriptions() throws Exception {
        return MzLab.getInstance().getReviewedPrescriptions();
    }

}
