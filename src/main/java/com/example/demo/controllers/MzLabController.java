package com.example.demo.controllers;


import com.example.demo.domain.handlers.MzLab;
import com.example.demo.domain.lab.TestDesc;
import com.example.demo.domain.user.Prescription;
import com.example.demo.domain.utility.Address;
import com.example.demo.domain.utility.FullTestInfo;
import com.example.demo.domain.utility.Receipt;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
    public void selectTests(@RequestBody String testNamesString, HttpServletResponse response) throws IOException {
        try {
            testNamesString = testNamesString.substring(testNamesString.indexOf("=") + 1);
            List<String> testNames = new ArrayList<String>(Arrays.asList(testNamesString.split("-")));
            MzLab.getInstance().setPatientTests(testNames);
        } catch (Exception e) {
            e.printStackTrace();
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
    public void setPatientTestAddress(@RequestBody String addressIndexString, HttpServletResponse response) throws IOException {
        try {
            addressIndexString = addressIndexString.substring(addressIndexString.indexOf("=") + 1);
            Address address = MzLab.getInstance().getPatientAddresses().get(Integer.valueOf(addressIndexString));
            MzLab.getInstance().setPatientTestAddress(address);
        } catch (Exception e) {
            response.sendError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
            e.printStackTrace();
        }
    }

    @PostMapping("/prescription")
    public void attachPrescription(@RequestBody String prescriptionId, HttpServletResponse response) throws IOException {
        try {
            prescriptionId = prescriptionId.substring(prescriptionId.indexOf("=") + 1);
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
            labName = labName.substring(labName.indexOf("=") + 1);
            return MzLab.getInstance().setSelectedLabForTests(labName);
        } catch (Exception e) {
            response.sendError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
        return null;
    }

    List<Date> savedDates;

    @GetMapping("/confirmTestInfo")
    public List<Date> confirmAndGetTimes(HttpServletResponse response) throws IOException {
        try {
            savedDates = MzLab.getInstance().confirmTestInfo();
            return savedDates;
        } catch (Exception e) {
            response.sendError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
        return null;
    }

    @PostMapping("/selectTimeForTest")
    public Receipt selectTimeForTest(@RequestBody String dateIndexString, HttpServletResponse response) throws IOException {
        try {
            dateIndexString = dateIndexString.substring(dateIndexString.indexOf("=") + 1);
            Date date = savedDates.get(Integer.valueOf(dateIndexString));
            return MzLab.getInstance().selectTimeForTest(date);
        } catch (Exception e) {
            response.sendError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
        return null;
    }

    @PostMapping("/confirmPaymentReceipt")
    public void confirmPaymentReceipt(HttpServletResponse response) throws IOException {
        try {
            MzLab.getInstance().confirmPaymentReceipt();
        } catch (Exception e) {
            response.sendError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }
}
