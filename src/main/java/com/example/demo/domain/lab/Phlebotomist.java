package com.example.demo.domain.lab;

import com.example.demo.domain.user.User;
import com.example.demo.domain.utility.PatientTestInfo;

import java.util.ArrayList;
import java.util.List;

public class Phlebotomist extends User {
    private String phlebotomistCode;
    private String info;
    private List<PatientTestInfo> patientTestInfoList;

    public Phlebotomist(String name, String username, String userEmail, String password, String phlebotomistCode) {
        super(name, username, userEmail, password);
        this.phlebotomistCode = phlebotomistCode;
        patientTestInfoList = new ArrayList<>();
    }

    public String getInfo() {
        return info;
    }


    public void addTestRecordToList(PatientTestInfo patientTestInfo) {
        patientTestInfoList.add(patientTestInfo);
    }

    public int getAmountOfWork() {
        int result = 0;
        for (PatientTestInfo patientTestInfo: patientTestInfoList) {
            result += patientTestInfo.getWork();
        }
        return result;
    }
}
