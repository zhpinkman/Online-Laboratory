package com.example.demo.controllers;


import com.example.demo.domain.user.Prescription;
import com.example.demo.domain.lab.TestDesc;
import com.example.demo.domain.handlers.MzLab;
import com.example.demo.domain.utility.Address;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/test")
public class MzLabController {

    @GetMapping("")
    public List<TestDesc> getTestDescList() {
        return MzLab.getInstance().getAllTests();
    }

    @GetMapping("/prescriptions")
    public List<Prescription> getPatientPrescriptions(HttpServletResponse response) throws IOException {
        try {
            return MzLab.getInstance().getReviewedPrescriptions();
        } catch (Exception e) {
            response.sendError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
        return null;
    }

    @PostMapping("")
    public void selectTests(@RequestBody List<String> testNames, HttpServletResponse response) throws IOException {
        try {
            MzLab.getInstance().setPatientTests(testNames);
        } catch (Exception e) {
            response.sendError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }

    @GetMapping("/addresses")
    public List<Address> getPatientAddresses(HttpServletResponse response) throws IOException {
        try {
            return MzLab.getInstance().getPatientAddresses();
        } catch (Exception e) {
            response.sendError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
        return null;
    }

}
