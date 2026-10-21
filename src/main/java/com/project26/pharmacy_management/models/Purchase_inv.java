package com.project26.pharmacy_management.models;

public class Purchase_inv {
    private int purchase_id;
    private int inv_id;
    private int quantity_bought;

    public Purchase_inv(int purchase_id, int inv_id, int quantity_bought) {
        this.purchase_id = purchase_id;
        this.inv_id = inv_id;
        this.quantity_bought = quantity_bought;
    }

    public int getPurchase_id() {
        return purchase_id;
    }

    public void setPurchase_id(int purchase_id) {
        this.purchase_id = purchase_id;
    }

    public int getInv_id() {
        return inv_id;
    }

    public void setInv_id(int inv_id) {
        this.inv_id = inv_id;
    }

    public int getQuantity_bought() {
        return quantity_bought;
    }

    public void setQuantity_bought(int quantity_bought) {
        this.quantity_bought = quantity_bought;
    }

    @Override
    public String toString() {
        return "Purchase_inv [purchase_id=" + purchase_id + ", inv_id=" + inv_id + ", quantity_bought="
                + quantity_bought + "]";
    }

}
