package com.lsa.ayu.model;

public class Withdrawal {
    String id,user_id,amount,status,date_created;
    public Withdrawal(){

    }

    public Withdrawal(String id, String user_id, String amount, String status, String date_created) {
        this.id = id;
        this.user_id = user_id;
        this.amount = amount;
        this.status = status;
        this.date_created = date_created;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate_created() {
        return date_created;
    }

    public void setDate_created(String date_created) {
        this.date_created = date_created;
    }
}
