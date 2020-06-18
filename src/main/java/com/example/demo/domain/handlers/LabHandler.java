package com.example.demo.domain.handlers;

import com.example.demo.domain.Lab;
import com.example.demo.domain.TestRequestRecord;

import java.util.ArrayList;
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
}
