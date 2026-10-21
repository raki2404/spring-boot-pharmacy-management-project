package com.project26.pharmacy_management.models;

public class SupplierPlan {

    private int med_id;
    private String name;
    private String company;
    private String time_till_expiry;
    private int sup_id;
    private int recurring;
    private String last_update_date;
    
    public SupplierPlan(int med_id, String name, String company, String time_till_expiry, int sup_id, int recurring,
            String last_update_date) {
        this.med_id = med_id;
        this.name = name;
        this.company = company;
        this.time_till_expiry = time_till_expiry;
        this.sup_id = sup_id;
        this.recurring = recurring;
        this.last_update_date = last_update_date;
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
    public String getTime_till_expiry() {
        return time_till_expiry;
    }
    public void setTime_till_expiry(String time_till_expiry) {
        this.time_till_expiry = time_till_expiry;
    }
    public int getSup_id() {
        return sup_id;
    }
    public void setSup_id(int sup_id) {
        this.sup_id = sup_id;
    }
    public int getRecurring() {
        return recurring;
    }
    public void setRecurring(int recurring) {
        this.recurring = recurring;
    }
    public String getLast_update_date() {
        return last_update_date;
    }
    public void setLast_update_date(String last_update_date) {
        this.last_update_date = last_update_date;
    }
    @Override
    public String toString() {
        return "SupplierPlan [med_id=" + med_id + ", name=" + name + ", company=" + company + ", time_till_expiry="
                + time_till_expiry + ", sup_id=" + sup_id + ", recurring=" + recurring + ", last_update_date="
                + last_update_date + "]";
    }

}
