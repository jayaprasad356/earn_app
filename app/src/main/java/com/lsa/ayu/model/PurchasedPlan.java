package com.lsa.ayu.model;

public class PurchasedPlan {
    String id,name,daily_income,price,valid,start_date,end_date,served_time,earned_amt,image;
    public PurchasedPlan(){

    }

    public PurchasedPlan(String id, String name, String daily_income, String price, String valid, String start_date, String end_date, String served_time, String earned_amt, String image) {
        this.id = id;
        this.name = name;
        this.daily_income = daily_income;
        this.price = price;
        this.valid = valid;
        this.start_date = start_date;
        this.end_date = end_date;
        this.served_time = served_time;
        this.earned_amt = earned_amt;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getServed_time() {
        return served_time;
    }

    public void setServed_time(String served_time) {
        this.served_time = served_time;
    }

    public String getEarned_amt() {
        return earned_amt;
    }

    public void setEarned_amt(String earned_amt) {
        this.earned_amt = earned_amt;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
