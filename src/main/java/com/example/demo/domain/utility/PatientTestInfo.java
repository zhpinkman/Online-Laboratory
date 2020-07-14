package com.example.demo.domain.utility;

import com.example.demo.domain.user.Prescription;
import com.example.demo.domain.user.TestRequestRecord;
import com.example.demo.domain.statusEnums.TestRequestRecordStatus;

import java.util.List;

public class PatientTestInfo {
    private String name;
    private int patientPriority;
    private List<Prescription> prescriptions;
    private TestRequestRecord testRequestRecord;

    public void setPatientGeneralInfo(String name, int patientPriority, List<Prescription> prescriptions) {
        this.name = name;
        this.patientPriority = patientPriority;
        this.prescriptions = prescriptions;
    }

    public void setTestRequestRecord(TestRequestRecord testRequestRecord) {
        this.testRequestRecord = testRequestRecord;
    }

    public int getWork() {
        if (testRequestRecord.getTestRequestRecordStatus().equals(TestRequestRecordStatus.TEST_DELIVERED))
            return 0;
        return testRequestRecord.getTestDescList().size();
    }
}
