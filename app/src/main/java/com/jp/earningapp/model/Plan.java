package com.jp.earningapp.model;

public class Plan {
    String id,daily_income,price,valid;
    public Plan(){

    }

    public Plan(String id, String daily_income, String price, String valid) {
        this.id = id;
        this.daily_income = daily_income;
        this.price = price;
        this.valid = valid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDaily_income() {
        return daily_income;
    }

    public void setDaily_income(String daily_income) {
        this.daily_income = daily_income;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getValid() {
        return valid;
    }

    public void setValid(String valid) {
        this.valid = valid;
    }
}
