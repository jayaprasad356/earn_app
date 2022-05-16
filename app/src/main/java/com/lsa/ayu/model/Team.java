package com.lsa.ayu.model;

public class Team {
    String id,mobile,name;
    public Team(){

    }

    public Team(String id, String mobile, String name) {
        this.id = id;
        this.mobile = mobile;
        this.name = name;
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
}
