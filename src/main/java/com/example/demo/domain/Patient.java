package com.example.demo.domain;

import java.util.List;

public class Patient extends User {
    private List<Address> addresses;
    private int patientPriority;
    private String insuranceCode;
    private List<TestRequestRecord> testRequestRecordList;
    private List<Prescription> prescriptions;
    private TestRequestRecord currentTestRequestRecord;


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
            if (prescription.idMatches(prescriptionId)) {
                currentTestRequestRecord.attachPrescription(prescription);
            }
        }
        throw new Exception("invalid prescription");
    }
}
