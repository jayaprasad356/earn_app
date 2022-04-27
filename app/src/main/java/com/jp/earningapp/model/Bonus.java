package com.jp.earningapp.model;

public class Bonus {
    String id,user_id,referral_user_id,level,recharged_amount,level_percentage,bonus_amount,name,mobile,date_created;
    public Bonus(){

    }

    public Bonus(String id, String user_id, String referral_user_id, String level, String recharged_amount, String level_percentage, String bonus_amount, String name, String mobile, String date_created) {
        this.id = id;
        this.user_id = user_id;
        this.referral_user_id = referral_user_id;
        this.level = level;
        this.recharged_amount = recharged_amount;
        this.level_percentage = level_percentage;
        this.bonus_amount = bonus_amount;
        this.name = name;
        this.mobile = mobile;
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

    public String getReferral_user_id() {
        return referral_user_id;
    }

    public void setReferral_user_id(String referral_user_id) {
        this.referral_user_id = referral_user_id;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getRecharged_amount() {
        return recharged_amount;
    }

    public void setRecharged_amount(String recharged_amount) {
        this.recharged_amount = recharged_amount;
    }

    public String getLevel_percentage() {
        return level_percentage;
    }

    public void setLevel_percentage(String level_percentage) {
        this.level_percentage = level_percentage;
    }

    public String getBonus_amount() {
        return bonus_amount;
    }

    public void setBonus_amount(String bonus_amount) {
        this.bonus_amount = bonus_amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getDate_created() {
        return date_created;
    }

    public void setDate_created(String date_created) {
        this.date_created = date_created;
    }
}
