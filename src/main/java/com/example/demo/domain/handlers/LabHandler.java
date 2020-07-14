package com.example.demo.domain.handlers;

import com.example.demo.domain.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LabHandler {

    private List<Lab> labList;

    public Lab getLab(String labName) throws Exception {
        for (Lab lab: labList) {
            if (lab.namesMatches(labName)) {
                return lab;
            }
        }
        throw new Exception("lab not found");
    }

    public List<Lab> getLabsWithFullSupport(TestRequestRecord testRequestRecord) {
        List<Lab> labsWithFullSupport = new ArrayList<>();
        for (Lab lab: labList) {
            if (lab.supportTests(testRequestRecord.getTestDescList())) {
                labsWithFullSupport.add(lab);
            }
        }
        return labsWithFullSupport;
    }

    public List<LabTest> getLabTests(String labName, TestRequestRecord testRequestRecord) throws Exception {
        Lab lab = getLab(labName);
        return lab.getLabTests(testRequestRecord.getTestDescList());
    }

    public List<Date> findRecommendedTimes(TestRequestRecord testRequestRecord) {
        return null; // todo
    }

    public void assignPhlebotomistToTest(TestRequestRecord testRequestRecord) {
        return; //  todo
    }

    public void prepareKitForRequest(Lab lab, List<TestDesc> testDescList) {
        lab.prepareKit(testDescList);
    }

    public void sendPatientInfoToPhlebotomist(Phlebotomist phlebotomist, PatientInfo patientInfo) {
//        todo
    }
}
