package com.project26.pharmacy_management.models;

public class Employee extends MyUser {

    private String first_name;
    private String last_name;
    private String address;
    private String date_of_joining;
    private String emp_role;
    private int salary;

    public Employee(){
        
    }

    public Employee(String address, String date_of_joining, String emp_role, String first_name, String last_name, int salary, int id, String username, String password, String role) {
        super(id, username, password, role);
        this.address = address;
        this.date_of_joining = date_of_joining;
        this.emp_role = emp_role;
        this.first_name = first_name;
        this.last_name = last_name;
        this.salary = salary;
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

    public String getDate_of_joining() {
        return date_of_joining;
    }

    public void setDate_of_joining(String date_of_joining) {
        this.date_of_joining = date_of_joining;
    }

    public String getEmp_role() {
        return emp_role;
    }

    public void setEmp_role(String emp_role) {
        this.emp_role = emp_role;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Employee [" + super.toString() + "first_name=" + first_name + ", last_name=" + last_name + ", address=" + address
                + ", date_of_joining=" + date_of_joining + ", emp_role=" + emp_role + ", salary="
                + salary + "]";
    }

    

}