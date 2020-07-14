package com.example.demo.domain;

import java.util.List;

public class LabRepository {
    private String name;
    private String repositoryCode;

    public void prepareKit(List<TestDesc> testDescList) {
        System.out.println("kit for tests prepared");
    }
}