package com.project26.pharmacy_management.models;

public class CustomerPlan {
    
    private int med_id;
    private String name;
    private String company;
    private String time_till_expiry;
    private int customer_id;
    private int recurring;
    private String last_update_date;
    
    public CustomerPlan(int med_id, String name, String company, String time_till_expiry, int customer_id,
            int recurring, String last_update_date) {
        this.med_id = med_id;
        this.name = name;
        this.company = company;
        this.time_till_expiry = time_till_expiry;
        this.customer_id = customer_id;
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
    public int getCustomer_id() {
        return customer_id;
    }
    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
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
        return "CustomerPlan [med_id=" + med_id + ", name=" + name + ", company=" + company + ", time_till_expiry="
                + time_till_expiry + ", customer_id=" + customer_id + ", recurring=" + recurring + ", last_update_date="
                + last_update_date + "]";
    }


    
}
