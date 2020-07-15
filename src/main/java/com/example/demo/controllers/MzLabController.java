package com.example.demo.controllers;


import com.example.demo.domain.lab.Lab;
import com.example.demo.domain.user.Prescription;
import com.example.demo.domain.lab.TestDesc;
import com.example.demo.domain.handlers.MzLab;
import com.example.demo.domain.utility.Address;
import com.example.demo.domain.utility.FullTestInfo;
import com.example.demo.domain.utility.Receipt;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
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

    @PostMapping("/addresses")
    public void setPatientTestAddress(@RequestBody Address address, HttpServletResponse response) throws IOException {
        try {
            MzLab.getInstance().setPatientTestAddress(address);
        } catch (Exception e) {
            response.sendError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }

    @PostMapping("/prescription")
    public void attachPrescription(@RequestBody String prescriptionId, HttpServletResponse response) throws IOException {
        try {
            MzLab.getInstance().attachPrescriptionToTest(prescriptionId);
        } catch (Exception e) {
            response.sendError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }

    @GetMapping("/verifyAndGetLabs")
    public List<FullTestInfo> getLabsWithFullSupport(HttpServletResponse response) throws IOException {
        try {
            return MzLab.getInstance().verifyPatientTestRequest();
        } catch (Exception e) {
            response.sendError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
        return null;
    }

    @PostMapping("/setLab")
    public FullTestInfo setPatientTestLab(@RequestBody String labName, HttpServletResponse response) throws IOException {
        try {
            return MzLab.getInstance().setSelectedLabForTests(labName);
        } catch (Exception e) {
            response.sendError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
        return null;
    }

    @GetMapping("/confirmTestInfo")
    public List<Date> confirmAndGetTimes(HttpServletResponse response) throws IOException {
        try {
            return MzLab.getInstance().confirmTestInfo();
        } catch (Exception e) {
            response.sendError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
        return null;
    }

    @PostMapping("/selectTimeForTest")
    public Receipt selectTimeForTest(@RequestBody Date date, HttpServletResponse response) throws Exception {
        System.out.println(date.toString());
        return MzLab.getInstance().selectTimeForTest(date);
    }
}
