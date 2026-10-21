package com.project26.pharmacy_management.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.project26.pharmacy_management.models.Employee;

@Repository
public class EmployeeRepository {
    
    @Autowired
    JdbcTemplate jdbcTemplate;

    public Optional<Employee> findEmployee(String username) {
        String query = "SELECT * FROM Employee, MyUser WHERE Employee.employee_id = MyUser.id and MyUser.username=?";
        try {
            List<Map<String, Object>> rows = jdbcTemplate.queryForList(query, username);
            for(Map<String, Object> row : rows) {
                int _id = Integer.parseInt(row.get("employee_id").toString());
                String _username = row.get("username").toString();
                String _password = row.get("password").toString();
                String _role = row.get("role").toString();
                String _fname = row.get("first_name").toString();
                String _lname = row.get("last_name").toString();
                String _address = row.get("address").toString();
                String _date = row.get("date_of_joining").toString();
                String _emprole = row.get("emp_role").toString();
                int _salary = Integer.parseInt(row.get("salary").toString());
                return Optional.of(new Employee(_address, _date, _emprole, _fname, _lname, _salary, _id, _username, _password, _role));
            }
        } catch (DataAccessException | NumberFormatException e) {
            return Optional.empty();
        }
        return Optional.empty();
    };

    public Optional<List<Employee>> findAllEmployees() {
        String query = "SELECT * FROM Employee, MyUser WHERE Employee.employee_id = MyUser.id;";
        try {
            List<Map<String, Object>> rows = jdbcTemplate.queryForList(query);
            List<Employee> employee_list = new ArrayList<> ();
            for(Map<String, Object> row : rows){
                int _id = Integer.parseInt(row.get("employee_id").toString());
                String _username = row.get("username").toString();
                String _password = row.get("password").toString();
                String _role = row.get("role").toString();
                String _fname = row.get("first_name").toString();
                String _lname = row.get("last_name").toString();
                String _address = row.get("address").toString();
                String _date = row.get("date_of_joining").toString();
                String _emprole = row.get("emp_role").toString();
                int _salary = Integer.parseInt(row.get("salary").toString());
                employee_list.add(new Employee(_address, _date, _emprole, _fname, _lname, _salary, _id, _username, _password, _role));
            }
            return Optional.of(employee_list);
        } catch (DataAccessException | NumberFormatException e) {
            return Optional.empty();
        }
    }

    public boolean insertEmployee(Employee employee){
        try {
            String query="INSERT INTO EMPLOYEE VALUES(?,?,?,?,?,?,?)";
            jdbcTemplate.update(query,employee.getId(),employee.getFirst_name(),employee.getLast_name(),employee.getAddress(),
            employee.getDate_of_joining(),employee.getEmp_role(),employee.getSalary());
            return true;
        } catch (DataAccessException e) {
            return false;
        }
    }

    public boolean updateEmployee(Employee employee){
        try {
            String query="UPDATE EMPLOYEE SET first_name=?,last_name=?,address=?,emp_role=?,salary=? WHERE employee_id=?";
            jdbcTemplate.update(query,employee.getFirst_name(),employee.getLast_name(),employee.getAddress(),
            employee.getEmp_role(),employee.getSalary(),employee.getId());
            return true;
        } catch (DataAccessException e) {
            return false;
        }
    }
}
