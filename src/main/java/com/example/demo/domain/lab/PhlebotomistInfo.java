package com.example.demo.domain.lab;

public class PhlebotomistInfo {
    private String name;
    private String info;
    private String labName;

    public PhlebotomistInfo(String name, String info, String labName) {
        this.name = name;
        this.info = info;
        this.labName = labName;
    }

    public String getName() {
        return name;
    }

    public String getInfo() {
        return info;
    }

    public String getLabName() {
        return labName;
    }
}
