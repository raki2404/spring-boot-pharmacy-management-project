package com.project26.pharmacy_management.models;

public class Medicine {
    private int med_id;
    private String name;
    private String company;
    private int cprice;
    private int sprice;
    private String utility;
    private int time_till_expiry;

    public Medicine(int med_id, String company, int cprice, String name, int sprice, int time_till_expiry, String utility) {
        this.med_id = med_id;
        this.company = company;
        this.cprice = cprice;
        this.name = name;
        this.sprice = sprice;
        this.time_till_expiry = time_till_expiry;
        this.utility = utility;
    }

    public int getMed_id() {
        return med_id;
    }

    public String getName() {
        return name;
    }

    public String getCompany() {
        return company;
    }

    public int getCprice() {
        return cprice;
    }

    public int getSprice() {
        return sprice;
    }

    public String getUtility() {
        return utility;
    }

    public int getTime_till_expiry() {
        return time_till_expiry;
    }

    public void setmedid(int med_id) {
        this.med_id = med_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setCprice(int cprice) {
        this.cprice = cprice;
    }

    public void setSprice(int sprice) {
        this.sprice = sprice;
    }

    public void setUtility(String utility) {
        this.utility = utility;
    }

    public void setTime_till_expiry(int time_till_expiry) {
        this.time_till_expiry = time_till_expiry;
    }

    @Override
    public String toString() {
        return "Medicine [med_id=" + med_id + ", name=" + name + ", company=" + company + ", cprice=" + cprice
                + ", sprice=" + sprice + ", utility=" + utility + ", time_till_expiry=" + time_till_expiry + "]";
    }
    
}
