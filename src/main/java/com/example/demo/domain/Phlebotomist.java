package com.example.demo.domain;

import java.util.ArrayList;
import java.util.List;

public class Phlebotomist extends User{
    private String phlebotomistCode;
    private String info;
    private List<PatientTestInfo> patientTestInfoList;

    public Phlebotomist() {
        patientTestInfoList = new ArrayList<>();
    }

    public String getInfo() {
        return info;
    }


    public void addTestRecordToList(PatientTestInfo patientTestInfo) {
        patientTestInfoList.add(patientTestInfo);
    }

}
