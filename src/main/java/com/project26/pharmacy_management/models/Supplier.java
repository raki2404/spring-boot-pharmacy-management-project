package com.project26.pharmacy_management.models;

public class Supplier {
    private int sup_id;
    private String name;
    private String address;
    private String email;
    private String phone;

    public Supplier(int sup_id, String name, String address, String email, String phone) {
        this.sup_id = sup_id;
        this.name = name;
        this.address = address;
        this.email = email;
        this.phone = phone;
    }

    public Supplier() {
    }

    public int getSup_id() {
        return sup_id;
    }

    public void setSup_id(int sup_id) {
        this.sup_id = sup_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Supplier [sup_id=" + sup_id + ", name=" + name + ", address=" + address + ", email=" + email
                + ", phone=" + phone + "]";
    }

    
}
