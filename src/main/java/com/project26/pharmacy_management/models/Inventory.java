package com.project26.pharmacy_management.models;

public class Inventory {
    private int inv_id;
    private int med_id;
    private String name;
    private String company;
    private int sprice;
    private String utility;
    private int time_till_expiry;
    private int quantity;
    private String date_of_man;

    public Inventory(){
        
    }

    public Inventory(String company, String date_of_man, int inv_id, int med_id, String name, int quantity, int sprice, int time_till_expiry, String utility) {
        this.company = company;
        this.date_of_man = date_of_man;
        this.inv_id = inv_id;
        this.med_id = med_id;
        this.name = name;
        this.quantity = quantity;
        this.sprice = sprice;
        this.time_till_expiry = time_till_expiry;
        this.utility = utility;
    }

    public int getInv_id() {
        return inv_id;
    }

    public void setInv_id(int inv_id) {
        this.inv_id = inv_id;
    }

    public int getMed_id() {
        return med_id;
    }

    public void setMed_id(int med_id) {
        this.med_id = med_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public int getSprice() {
        return sprice;
    }

    public void setSprice(int sprice) {
        this.sprice = sprice;
    }

    public String getUtility() {
        return utility;
    }

    public void setUtility(String utility) {
        this.utility = utility;
    }

    public int getTime_till_expiry() {
        return time_till_expiry;
    }

    public void setTime_till_expiry(int time_till_expiry) {
        this.time_till_expiry = time_till_expiry;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDate_of_man() {
        return date_of_man;
    }

    public void setDate_of_man(String date_of_man) {
        this.date_of_man = date_of_man;
    }

}

