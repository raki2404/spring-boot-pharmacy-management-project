package com.project26.pharmacy_management.models;

public class User_email {
    private int user_id;
    private String email;

    public User_email(int user_id, String email) {
        this.user_id = user_id;
        this.email = email;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User_email [user_id=" + user_id + ", email=" + email + "]";
    }

}
