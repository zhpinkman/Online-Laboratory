package com.example.demo.domain.handlers;

import com.example.demo.domain.externalAPIs.BankAPI;
import com.example.demo.domain.externalAPIs.InsuranceAPI;
import com.example.demo.domain.lab.PhlebotomistInfo;
import com.example.demo.domain.lab.TestDesc;
import com.example.demo.domain.statusEnums.PrescriptionStatus;
import com.example.demo.domain.user.Patient;
import com.example.demo.domain.user.PaymentReceipt;
import com.example.demo.domain.user.Prescription;
import com.example.demo.domain.user.TestRequestRecord;
import com.example.demo.domain.utility.Address;
import com.example.demo.domain.utility.PatientTestInfo;
import com.example.demo.domain.utility.Receipt;

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
            if (prescription.getPrescriptionStatus().equals(PrescriptionStatus.BAD_RECORD)) {
                throw new Exception("there is a bad record in your prescriptions list");
            }
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

    public void verifyPatientTestRequest(String patientEmail) throws Exception {
        Patient patient = getPatient(patientEmail);
        patient.verifyTestCorrentness();
    }

    public void setSelectedLabForTests(String patientEmail, String selectedLabNam) throws Exception {
        Patient patient = getPatient(patientEmail);
        patient.setSelectedLabForTests(selectedLabNam);
    }

    public void confirmTestRequest(String patientEmail) throws Exception {
        Patient patient = getPatient(patientEmail);
        patient.confirmTestRequest();
    }

    public void setTimeForTest(String patientEmail, Date date) throws Exception {
        Patient patient = getPatient(patientEmail);
        patient.setTimeForTest(date);
    }

    public Receipt getTotalPrice(String patientEmail, List<Double> prices) throws Exception {
        Patient patient = getPatient(patientEmail);
        boolean insuranceVerified = InsuranceAPI.verifyCode(patient.getInsuranceCode());
        int reductionFactor = InsuranceAPI.getInsuranceCompanyRedcutionFactor(patient.getInsuranceCompany());
        boolean insuranceSupport = InsuranceAPI.SupportsLab(patient.getSelectedLabName(), patient.getInsuranceCompany());
        Receipt receipt = patient.getTestTotalPrice(insuranceVerified, insuranceSupport, reductionFactor, prices);
        patient.setCurrentReceipt(receipt);
        return receipt;
    }

    public void confirmPaymentReceipt(String patientEmail) throws Exception {
        Patient patient = getPatient(patientEmail);
        double amountToPay = patient.getCurrentReceipt().getTotalAmount();
        PaymentReceipt paymentReceipt = new PaymentReceipt(amountToPay);
        BankAPI.verifyPayment(paymentReceipt);
        patient.setPaymentDone();
    }


    public List<TestDesc> getTestDescList(String patientEmail) throws Exception {
        Patient patient = getPatient(patientEmail);
        return patient.getTestDescList();
    }

    public PatientTestInfo getPatientInfo(String patientEmail) throws Exception {
        Patient patient = getPatient(patientEmail);
        return patient.getInfo();
    }

    public PhlebotomistInfo getPatientsCurrentTestPhlebotomist(String patientEmail) throws Exception {
        Patient patient = getPatient(patientEmail);
        return patient.getPhlebotomistInfo();
    }

    public void setWaitingForPayment(String patientEmail) throws Exception {
        Patient patient = getPatient(patientEmail);
        patient.setWaitingForPayment();
    }

    public String getSelectedLabName(String patientEmail) throws Exception {
        Patient patient = getPatient(patientEmail);
        return patient.getSelectedLabName();
    }

    public void setPhlebotomistInfo(String patientEmail, PhlebotomistInfo phlebotomistInfo) throws Exception {
        Patient patient = getPatient(patientEmail);
        patient.setPhlebotomistInfo(phlebotomistInfo);
    }
}
