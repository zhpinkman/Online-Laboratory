package com.example.demo.domain.handlers;

import com.example.demo.domain.lab.Lab;
import com.example.demo.domain.lab.LabTest;
import com.example.demo.domain.lab.Phlebotomist;
import com.example.demo.domain.lab.TestDesc;
import com.example.demo.domain.user.Prescription;
import com.example.demo.domain.user.TestRequestRecord;
import com.example.demo.domain.utility.Address;
import com.example.demo.domain.utility.FullTestInfo;
import com.example.demo.domain.utility.PatientTestInfo;
import com.example.demo.domain.utility.Receipt;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MzLab {

    public static final String patientEmail = "zhivarsourati@gmail.com";
    private List<TestDesc> testDescs = new ArrayList<>();

    private PatientHandler patientHandler = new PatientHandler();
    private LabHandler labHandler = new LabHandler();


    private MzLab() {
        initTests();
    }

    private static MzLab instance;

    public static MzLab getInstance() {
        if (instance == null) {
            instance = new MzLab();
            return instance;
        }
        return instance;
    }

    private void initTests() {
        TestDesc testDesc1 = new TestDesc("test1", 1, true, true, "nothing", "nothing");
        TestDesc testDesc2 = new TestDesc("test2", 2, true, true, "nothing", "nothing");
        TestDesc testDesc3 = new TestDesc("test3", 3, false, true, "nothing", "nothing");
        TestDesc testDesc4 = new TestDesc("test4", 2, false, true, "nothing", "nothing");

        this.testDescs.add(testDesc1);
        this.testDescs.add(testDesc2);
        this.testDescs.add(testDesc3);
        this.testDescs.add(testDesc4);
    }


    public List<TestDesc> getAllTests() {
        return testDescs;
    }

    private TestDesc getTestByName(String name) throws Exception {
        for (TestDesc testDesc: testDescs) {
            if (testDesc.matches(name)) {
                return testDesc;
            }
        }
        throw new Exception("Test not found -> " + name);
    }

    private List<TestDesc> getTestsByName(List<String> names) throws Exception {
        List<TestDesc> testDescs = new ArrayList<>();
        for (String name: names) {
            testDescs.add(getTestByName(name));
        }
        return testDescs;
    }

    public List<Prescription> getReviewedPrescriptions() throws Exception {
        return patientHandler.getReviewedPrescritions(patientEmail);
    }

    public void setPatientTests(List<String> testNames) throws Exception {
        List<TestDesc> testDescs = getTestsByName(testNames);
        patientHandler.setPatientsTest(patientEmail, testDescs);
    }

    public List<Address> getPatientAddresses() throws Exception {
        return patientHandler.getPatientAddresses(patientEmail);
    }

    public void setPatientTestAddress(Address address) throws Exception {
        patientHandler.setTestRecordRequestAddress(patientEmail, address);
    }

    public void attachPrescriptionToTest(String prescriptionId) throws Exception {
        patientHandler.attachPrescriptionToTest(patientEmail, prescriptionId);
    }

    public List<FullTestInfo> verifyPatientTestRequest() throws Exception {
        TestRequestRecord testRequestRecord = patientHandler.verifyPatientTestRequest(patientEmail);
        return getLabsWithFullSupport(testRequestRecord);
    }

    public List<FullTestInfo> getLabsWithFullSupport(TestRequestRecord testRequestRecord) throws Exception {
        List<FullTestInfo> fullTestInfos = new ArrayList<>();
        List<Lab> labList = labHandler.getLabsWithFullSupport(testRequestRecord);
        for (Lab lab: labList) {
            fullTestInfos.add(getFullTestInfo(lab, labHandler.getLabTests(lab.getName(), testRequestRecord)));
        }
        return fullTestInfos;
    }

    private FullTestInfo getFullTestInfo(Lab lab, List<LabTest> labTestList) {
        FullTestInfo fullTestInfo = new FullTestInfo();
        fullTestInfo.setLabName(lab.getName());
        fullTestInfo.setLabTestList(labTestList);
        fullTestInfo.setInsuranceCompanies(lab.getSupportedInsurancesNames());
        return fullTestInfo;
    }

    public FullTestInfo setSelectedLabForTests(String labName) throws Exception {
        Lab selectedLab = labHandler.getLab(labName);
        TestRequestRecord testRequestRecord = patientHandler.setSelectedLabForTests(patientEmail, selectedLab);
        List<LabTest> labTestList = labHandler.getLabTests(labName, testRequestRecord);
        return getFullTestInfo(selectedLab, labTestList);
    }

    public List<Date> confirmTestInfo() throws Exception {
        TestRequestRecord testRequestRecord = patientHandler.confirmTestRequest(patientEmail);
        return labHandler.findRecommendedTimes(testRequestRecord);
    }


    public Receipt selectTimeForTest(Date date) throws Exception {
        TestRequestRecord testRequestRecord = patientHandler.setTimeForTest(patientEmail, date);
        labHandler.assignPhlebotomistToTest(testRequestRecord);
        return patientHandler.getTotalPrice(patientEmail);
    }

    public void confirmPaymentReceipt() throws Exception {
        patientHandler.confirmPaymentReceipt(patientEmail);
        PatientTestInfo patientInfo = patientHandler.getPatientInfo(patientEmail);
        Phlebotomist phlebotomist = patientHandler.getPatientsCurrentTestPhlebotomist(patientEmail);
        phlebotomist.addTestRecordToList(patientInfo);
        System.out.println("patient info sent to phlebotomist");
        Lab lab = patientHandler.getSelectedLab(patientEmail);
        List<TestDesc> testDescList = patientHandler.getTestDescList(patientEmail);
        labHandler.prepareKitForRequest(lab, testDescList);
    }

}
