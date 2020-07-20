package com.example.demo.domain.user;

import com.example.demo.domain.lab.Lab;
import com.example.demo.domain.lab.PhlebotomistInfo;
import com.example.demo.domain.lab.TestDesc;
import com.example.demo.domain.statusEnums.PrescriptionStatus;
import com.example.demo.domain.utility.Address;
import com.example.demo.domain.utility.PatientTestInfo;
import com.example.demo.domain.utility.Receipt;

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
    private Receipt currentReceipt;

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

    public List<Prescription> getPrescriptions() throws Exception {
        if (prescriptions.size() <= 0) {
            throw new Exception("no prescription available!");
        }
        return prescriptions;
    }

    public List<Address> getAddresses() throws Exception {
        if (addresses.size() == 0) {
            throw new Exception("there are no addresses in address book");
        }
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

    public void setCurrentReceipt(Receipt currentReceipt) {
        this.currentReceipt = currentReceipt;
    }

    public Receipt getCurrentReceipt() {
        return currentReceipt;
    }

    public TestRequestRecord getCurrentTestRequestRecord() throws Exception {
        if (currentTestRequestRecord == null) {
            throw new Exception("there are no tests undergoing");
        }
        return currentTestRequestRecord;
    }

    public void setSelectedLabForTests(String labName) throws Exception {
        currentTestRequestRecord.setSelectedLabName(labName);
    }

    public void confirmTestRequest() throws Exception {
        currentTestRequestRecord.confirmRequest();
    }

    public void setTimeForTest(Date date) throws Exception {
        currentTestRequestRecord.setPhlebotomistReferDate(date);
    }

    public void setPaymentDone() throws Exception {
        currentTestRequestRecord.setPaymentDone();
    }

    public int getPatientPriority() {
        return patientPriority;
    }

    public List<TestDesc> getTestDescList() {
        return currentTestRequestRecord.getTestDescList();
    }

    public String getSelectedLabName() {
        return currentTestRequestRecord.getSelectedLabName();
    }

    public PhlebotomistInfo getPhlebotomistInfo() {
        return currentTestRequestRecord.getPhlebotomistInfo();
    }

    public void verifyTestCorrentness() throws Exception {
        currentTestRequestRecord.verifyCorrectness();
    }

    public void setWaitingForPayment() {
        currentTestRequestRecord.setWaitingForPayment();
    }

    public PatientTestInfo getInfo() throws Exception {
        PatientTestInfo patientInfo = new PatientTestInfo();
        patientInfo.setPatientGeneralInfo(getName(), getPatientPriority(), getCurrentTestPrescription());
        patientInfo.setTestRequestRecordStatus(currentTestRequestRecord.getTestRequestRecordStatus());
        patientInfo.setTestDescList(currentTestRequestRecord.getTestDescList());
        return patientInfo;
    }

    private Prescription getCurrentTestPrescription() {
        return currentTestRequestRecord.getPrescription();
    }

    public void setPhlebotomistInfo(PhlebotomistInfo phlebotomistInfo) {
        currentTestRequestRecord.setPhlebotomistInfo(phlebotomistInfo);
    }

    public Receipt getTestTotalPrice(boolean insuranceVerified, boolean insuranceSupport, int reductionFactor, List<Double> prices) throws Exception {
        return currentTestRequestRecord.getTotalPrice(insuranceVerified, insuranceSupport, reductionFactor, prices);
    }
}
