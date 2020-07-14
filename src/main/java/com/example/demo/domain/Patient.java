package com.example.demo.domain;

import com.example.demo.domain.statusEnums.PrescriptionStatus;

import java.util.Date;
import java.util.List;

public class Patient extends User {
    private List<Address> addresses;
    private int patientPriority;
    private String insuranceCode;
    private String insuranceCompany;
    private List<TestRequestRecord> testRequestRecordList;
    private List<Prescription> prescriptions;
    private TestRequestRecord currentTestRequestRecord;


    public String getInsuranceCode() {
        return insuranceCode;
    }

    public String getInsuranceCompany() {
        return insuranceCompany;
    }

    public List<Prescription> getPrescriptions() {
        return prescriptions;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setTestRecordRequestAddress(Address address) {
        currentTestRequestRecord.setAddress(address);
    }

    public void setTests(List<TestDesc> testDescs) {
        currentTestRequestRecord.setTestDescList(testDescs);
    }

    public void attachPrescriptionToTest(String prescriptionId) throws Exception {
        for (Prescription prescription: prescriptions) {
            if (prescription.idMatches(prescriptionId) &&
                    prescription.getPrescriptionStatus().equals(PrescriptionStatus.REVIEWED)) {
                currentTestRequestRecord.attachPrescription(prescription);
            }
        }
        throw new Exception("invalid prescription");
    }


    public TestRequestRecord getCurrentTestRequestRecord() {
        return currentTestRequestRecord;
    }

    public void setSelectedLabForTests(Lab selectedLab) {
        currentTestRequestRecord.setSelectedLab(selectedLab);
    }

    public TestRequestRecord confirmTestRequest() {
        currentTestRequestRecord.confirmRequest();
        return currentTestRequestRecord;
    }

    public TestRequestRecord setTimeForTest(Date date) {
        currentTestRequestRecord.setPhlebotomistReferDate(date);
        return currentTestRequestRecord;
    }

    public void setPaymentDone() {
        currentTestRequestRecord.setPaymentDone();
    }

    public int getPatientPriority() {
        return patientPriority;
    }
}
