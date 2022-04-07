package com.jp.earningapp.model;

public class Team {
    String id,mobile;
    public Team(){

    }

    public Team(String id, String mobile) {
        this.id = id;
        this.mobile = mobile;
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
}
