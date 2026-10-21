package com.project26.pharmacy_management.models;

public class User_phone {
    private int user_id;
    private String phone;

    public User_phone(int user_id, String phone) {
        this.user_id = user_id;
        this.phone = phone;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "User_phone [user_id=" + user_id + ", phone=" + phone + "]";
    }
    

}
