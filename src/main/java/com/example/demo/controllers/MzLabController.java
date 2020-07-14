package com.example.demo.controllers;


import com.example.demo.domain.user.Prescription;
import com.example.demo.domain.lab.TestDesc;
import com.example.demo.domain.handlers.MzLab;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("")
    public void selectTests(@RequestBody List<String> testNames) throws Exception {
        MzLab.getInstance().setPatientTests(testNames);
    }

}
