package com.example.demo.domain.lab;

import com.example.demo.domain.lab.TestDesc;

import java.util.List;

public class LabRepository {
    private String name;
    private String repositoryCode;

    public LabRepository(String name, String repositoryCode) {
        this.name = name;
        this.repositoryCode = repositoryCode;
    }

    public void prepareKit(List<TestDesc> testDescList) {
        System.out.println("kit for tests prepared");
    }
}