package com.project26.pharmacy_management.models;

public class Sales {
    private int sale_id;
    private int customer_id;
    private int employee_id;
    private String date_of_sale;
    private int total_amount;
    
    public Sales(int sale_id, int customer_id, int employee_id, String date_of_sale, int total_amount) {
        this.sale_id = sale_id;
        this.customer_id = customer_id;
        this.employee_id = employee_id;
        this.date_of_sale = date_of_sale;
        this.total_amount = total_amount;
    }

    public int getSale_id() {
        return sale_id;
    }

    public void setSale_id(int sale_id) {
        this.sale_id = sale_id;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public int getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(int employee_id) {
        this.employee_id = employee_id;
    }

    public String getDate_of_sale() {
        return date_of_sale;
    }

    public void setDate_of_sale(String date_of_sale) {
        this.date_of_sale = date_of_sale;
    }

    public int getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(int total_amount) {
        this.total_amount = total_amount;
    }

    @Override
    public String toString() {
        return "Sales [sale_id=" + sale_id + ", customer_id=" + customer_id + ", employee_id=" + employee_id
                + ", date_of_sale=" + date_of_sale + ", total_amount=" + total_amount + "]";
    }

    
}
