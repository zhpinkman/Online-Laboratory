package com.example.demo.domain.user;

import com.example.demo.domain.lab.Lab;
import com.example.demo.domain.lab.TestDesc;
import com.example.demo.domain.statusEnums.PrescriptionStatus;
import com.example.demo.domain.utility.Address;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Patient extends User {
    private List<Address> addresses;
    private int patientPriority;
    private String insuranceCode;
    private String insuranceCompany;
    private List<TestRequestRecord> testRequestRecordList;
    private List<Prescription> prescriptions = new ArrayList<>();
    private TestRequestRecord currentTestRequestRecord;

    public Patient(String name, String username, String userEmail, String password, List<Address> addresses, int patientPriority, String insuranceCode, String insuranceCompany) {
        super(name, username, userEmail, password);
        this.addresses = addresses;
        this.patientPriority = patientPriority;
        this.insuranceCode = insuranceCode;
        this.insuranceCompany = insuranceCompany;

        TestDesc testDesc3 = new TestDesc("test3", 3, false, true, "nothing", "nothing");
        TestDesc testDesc4 = new TestDesc("test4", 2, false, true, "nothing", "nothing");

        List<TestDesc> testDescList = new ArrayList<TestDesc>(Arrays.asList(testDesc3, testDesc4));

        prescriptions.add(new Prescription("kshLkBS", "prescription", testDescList, PrescriptionStatus.REVIEWED));
    }


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

    public void setTestRecordRequestAddress(Address address) throws Exception {
        if(currentTestRequestRecord == null){
            throw new Exception("Please start the use case from the beginning");
        }
        currentTestRequestRecord.setAddress(address);
    }

    public void setTests(List<TestDesc> testDescs) throws Exception {
        if (currentTestRequestRecord != null) {
            throw new Exception("another test is completing its workflow");
        }
        currentTestRequestRecord = new TestRequestRecord();
        currentTestRequestRecord.setTestDescList(testDescs);
    }

    public void attachPrescriptionToTest(String prescriptionId) throws Exception {
        for (Prescription prescription: prescriptions) {
            if (prescription.idMatches(prescriptionId) &&
                    prescription.getPrescriptionStatus().equals(PrescriptionStatus.REVIEWED)) {
                currentTestRequestRecord.attachPrescription(prescription);
                return;
            }
        }
        throw new Exception("invalid prescription");
    }


    public TestRequestRecord getCurrentTestRequestRecord() {
        return currentTestRequestRecord;
    }

    public void setSelectedLabForTests(Lab selectedLab) throws Exception {
        currentTestRequestRecord.setSelectedLab(selectedLab);
    }

    public TestRequestRecord confirmTestRequest() throws Exception {
        currentTestRequestRecord.confirmRequest();
        return currentTestRequestRecord;
    }

    public TestRequestRecord setTimeForTest(Date date) throws Exception {
        currentTestRequestRecord.setPhlebotomistReferDate(date);
        return currentTestRequestRecord;
    }

    public void setPaymentDone() throws Exception {
        currentTestRequestRecord.setPaymentDone();
    }

    public int getPatientPriority() {
        return patientPriority;
    }
}
