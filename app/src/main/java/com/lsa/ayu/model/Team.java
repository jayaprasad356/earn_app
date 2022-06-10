package com.lsa.ayu.model;

public class Team {
    String id,mobile,name,contribution;
    public Team(){

    }

    public Team(String id, String mobile, String name, String contribution) {
        this.id = id;
        this.mobile = mobile;
        this.name = name;
        this.contribution = contribution;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContribution() {
        return contribution;
    }

    public void setContribution(String contribution) {
        this.contribution = contribution;
    }
}
