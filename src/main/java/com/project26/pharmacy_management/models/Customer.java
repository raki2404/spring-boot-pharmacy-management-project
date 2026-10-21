package com.project26.pharmacy_management.models;

public class Customer extends MyUser {
    
    private String name;
    private String address;


    public Customer(String address, String name, int id, String username, String password, String role) {
        super(id, username, password, role);
        this.address = address;
        this.name = name;
    }

    public Customer() {
        super();
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

    @Override
    public String toString() {
        return "Customer [" + super.toString() + "name=" + name + ", address=" + address + "]";
    }
    

}
