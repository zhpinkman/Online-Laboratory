package com.example.demo.domain.utility;

import com.example.demo.domain.lab.TestDesc;
import com.example.demo.domain.user.Prescription;
import com.example.demo.domain.statusEnums.TestRequestRecordStatus;

import java.util.List;

public class PatientTestInfo {
    private String name;
    private int patientPriority;
    private Prescription prescriptions;
    private List<TestDesc> testDescList;
    private TestRequestRecordStatus testRequestRecordStatus;

    public void setPatientGeneralInfo(String name, int patientPriority, Prescription prescriptions) {
        this.name = name;
        this.patientPriority = patientPriority;
        this.prescriptions = prescriptions;
    }

    public void setTestRequestRecordStatus(TestRequestRecordStatus testRequestRecordStatus) {
        this.testRequestRecordStatus = testRequestRecordStatus;
    }

    public void setTestDescList(List<TestDesc> testDescList) {
        this.testDescList = testDescList;
    }

    public int getWork() {
        if (testRequestRecordStatus.equals(TestRequestRecordStatus.TEST_DELIVERED))
            return 0;
        return testDescList.size();
    }
}
