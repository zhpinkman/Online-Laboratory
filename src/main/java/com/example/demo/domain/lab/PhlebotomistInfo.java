package com.example.demo.domain.lab;

public class PhlebotomistInfo {
    private String name;
    private String info;

    public PhlebotomistInfo(String name, String info) {
        this.name = name;
        this.info = info;
    }

    public String getName() {
        return name;
    }

    public String getInfo() {
        return info;
    }
}
