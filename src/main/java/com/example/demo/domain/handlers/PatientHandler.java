package com.example.demo.domain.handlers;

import com.example.demo.domain.externalAPIs.BankAPI;
import com.example.demo.domain.externalAPIs.InsuranceAPI;
import com.example.demo.domain.lab.Lab;
import com.example.demo.domain.lab.Phlebotomist;
import com.example.demo.domain.lab.TestDesc;
import com.example.demo.domain.statusEnums.PrescriptionStatus;
import com.example.demo.domain.user.Patient;
import com.example.demo.domain.user.PaymentReceipt;
import com.example.demo.domain.user.Prescription;
import com.example.demo.domain.user.TestRequestRecord;
import com.example.demo.domain.utility.Address;
import com.example.demo.domain.utility.PatientTestInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class PatientHandler {

    private List<Patient> patientList = new ArrayList<>();

    public PatientHandler() {
        patientList.add(new Patient("zhivar", "zh_pinkman", "zhivarsourati@gmail.com", "haha",
                new ArrayList<Address>(Arrays.asList(new Address(20, 40, "temp address"))),
                10, "youCannotFindIt", "bime"));
    }


    private Patient getPatient(String patientEmail) throws Exception {
        for (Patient patient: patientList) {
            if (patient.emailsMatch(patientEmail)) {
                return patient;
            }
        }
        throw new Exception("user not available");
    }

    public List<Prescription> getReviewedPrescritions(String patientEmail) throws Exception {
        Patient patient = getPatient(patientEmail);
        List<Prescription> patientPrescriptions = patient.getPrescriptions();
        List<Prescription> reviewedOnes = new ArrayList<>();
        for (Prescription prescription: patientPrescriptions) {
            if (prescription.getPrescriptionStatus().equals(PrescriptionStatus.REVIEWED)) {
                reviewedOnes.add(prescription);
            }
        }
        return reviewedOnes;
    }

    public List<Address> getPatientAddresses(String patientEmail) throws Exception {
        Patient patient = getPatient(patientEmail);
        return patient.getAddresses();
    }

    public void setTestRecordRequestAddress(String patientEmail, Address address) throws Exception {
        Patient patient = getPatient(patientEmail);
        patient.setTestRecordRequestAddress(address);
    }

    public void setPatientsTest(String patientEmail, List<TestDesc> testDescs) throws Exception {
        Patient patient = getPatient(patientEmail);
        patient.setTests(testDescs);
    }

    public void attachPrescriptionToTest(String patientEmail, String prescriptionId) throws Exception {
        Patient patient = getPatient(patientEmail);
        patient.attachPrescriptionToTest(prescriptionId);
    }

    public TestRequestRecord verifyPatientTestRequest(String patientEmail) throws Exception {
        Patient patient = getPatient(patientEmail);
        TestRequestRecord testRequestRecord = patient.getCurrentTestRequestRecord();
        testRequestRecord.verifyCorrectness();
        return testRequestRecord;
    }

    public TestRequestRecord setSelectedLabForTests(String patientEmail, Lab selectedLab) throws Exception {
        Patient patient = getPatient(patientEmail);
        patient.setSelectedLabForTests(selectedLab);
        return patient.getCurrentTestRequestRecord();
    }

    public TestRequestRecord confirmTestRequest(String patientEmail) throws Exception {
        Patient patient = getPatient(patientEmail);
        return patient.confirmTestRequest();
    }

    public TestRequestRecord setTimeForTest(String patientEmail, Date date) throws Exception {
        Patient patient = getPatient(patientEmail);
        return patient.setTimeForTest(date);
    }

    public double getTotalPrice(String patientEmail) throws Exception {
        Patient patient = getPatient(patientEmail);
        boolean insuranceVerified = InsuranceAPI.verifyCode(patient.getInsuranceCode());
        TestRequestRecord testRequestRecord = patient.getCurrentTestRequestRecord();
        return testRequestRecord.getTotalPrice(insuranceVerified, patient.getInsuranceCompany());
    }

    public void confirmPaymentReceipt(String patientEmail) throws Exception {
        Patient patient = getPatient(patientEmail);
        double amountToPay = getTotalPrice(patientEmail);
        PaymentReceipt paymentReceipt = new PaymentReceipt(amountToPay);
        BankAPI.verifyPayment(paymentReceipt);
        patient.setPaymentDone();
    }

    public Lab getSelectedLab(String patientEmail) throws Exception {
        Patient patient = getPatient(patientEmail);
        TestRequestRecord testRequestRecord = patient.getCurrentTestRequestRecord();
        return testRequestRecord.getSelectedLab();
    }

    public List<TestDesc> getTestDescList(String patientEmail) throws Exception {
        Patient patient = getPatient(patientEmail);
        TestRequestRecord testRequestRecord = patient.getCurrentTestRequestRecord();
        return testRequestRecord.getTestDescList();
    }

    public PatientTestInfo getPatientInfo(String patientEmail) throws Exception {
        PatientTestInfo patientInfo = new PatientTestInfo();
        Patient patient = getPatient(patientEmail);
        patientInfo.setPatientGeneralInfo(patient.getName(), patient.getPatientPriority(), patient.getPrescriptions());
        TestRequestRecord testRequestRecord = patient.getCurrentTestRequestRecord();
        patientInfo.setTestRequestRecord(testRequestRecord);
        return patientInfo;
    }

    public Phlebotomist getPatientsCurrentTestPhlebotomist(String patientEmail) throws Exception {
        Patient patient = getPatient(patientEmail);
        TestRequestRecord testRequestRecord = patient.getCurrentTestRequestRecord();
        return testRequestRecord.getPhlebotomist();
    }
}
