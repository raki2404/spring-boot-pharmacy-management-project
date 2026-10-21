package com.project26.pharmacy_management.models;

public class Owner extends MyUser {
    
    private String first_name;
    private String last_name;
    private String address;
    private int partnership_percent;

    public Owner(String address, String first_name, String last_name, int partnership_percent, int id, String username, String password, String role) {
        super(id, username, password, role);
        this.address = address;
        this.first_name = first_name;
        this.last_name = last_name;
        this.partnership_percent = partnership_percent;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPartnership_percent() {
        return partnership_percent;
    }

    public void setPartnership_percent(int partnership_percent) {
        this.partnership_percent = partnership_percent;
    }

    @Override
    public String toString() {
        return "Owner [" + super.toString() + "first_name=" + first_name + ", last_name=" + last_name + ", address=" + address
                + ", partnership_percent=" + partnership_percent + "]";
    }

}
