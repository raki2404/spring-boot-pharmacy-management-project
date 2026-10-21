package com.project26.pharmacy_management.models;

public class Purchases {
    private int purchase_id;
    private int sup_id;
    private int owner_id;
    private int date_of_purchase;
    private int total_amount;

    public Purchases(int purchase_id, int sup_id, int owner_id, int date_of_purchase, int total_amount) {
        this.purchase_id = purchase_id;
        this.sup_id = sup_id;
        this.owner_id = owner_id;
        this.date_of_purchase = date_of_purchase;
        this.total_amount = total_amount;
    }

    public int getPurchase_id() {
        return purchase_id;
    }

    public void setPurchase_id(int purchase_id) {
        this.purchase_id = purchase_id;
    }

    public int getSup_id() {
        return sup_id;
    }

    public void setSup_id(int sup_id) {
        this.sup_id = sup_id;
    }

    public int getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(int owner_id) {
        this.owner_id = owner_id;
    }

    public int getDate_of_purchase() {
        return date_of_purchase;
    }

    public void setDate_of_purchase(int date_of_purchase) {
        this.date_of_purchase = date_of_purchase;
    }

    public int getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(int total_amount) {
        this.total_amount = total_amount;
    }

    @Override
    public String toString() {
        return "Purchases [purchase_id=" + purchase_id + ", sup_id=" + sup_id + ", owner_id=" + owner_id
                + ", date_of_purchase=" + date_of_purchase + ", total_amount=" + total_amount + "]";
    }

}
