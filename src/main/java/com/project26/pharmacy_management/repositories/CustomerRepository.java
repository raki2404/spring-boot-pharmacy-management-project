package com.project26.pharmacy_management.repositories;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.project26.pharmacy_management.models.Customer;
import com.project26.pharmacy_management.models.CustomerPlan;
import com.project26.pharmacy_management.models.Medicine;

@Repository
public class CustomerRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public Optional<Customer> findCustomerbyId(int customer_id) {
        String query = "SELECT * FROM Customer WHERE customer_id = ?";
        try {
            List<Map<String, Object>> rows = jdbcTemplate.queryForList(query, customer_id);
            for(Map<String, Object> row : rows) {
                int _id = Integer.parseInt(row.get("customer_id").toString());
                String _name = row.get("name").toString();
                String _address = row.get("address").toString();
                return Optional.of(new Customer(_address, _name, _id, "", "", ""));
            }
        } catch (DataAccessException | NumberFormatException e) {
            return Optional.empty();
        }
        return Optional.empty();
    };

    public Optional<Customer> findCustomer(String username) {
        String query = "SELECT * FROM Customer, MyUser WHERE Customer.customer_id = MyUser.id and MyUser.username=?";
        try {
            List<Map<String, Object>> rows = jdbcTemplate.queryForList(query, username);
            for(Map<String, Object> row : rows) {
                int _id = Integer.parseInt(row.get("customer_id").toString());
                String _username = row.get("username").toString();
                String _password = row.get("password").toString();
                String _role = row.get("role").toString();
                String _name = row.get("name").toString();
                String _address = row.get("address").toString();
                return Optional.of(new Customer(_address, _name, _id, _username, _password, _role));
            }
        } catch (DataAccessException | NumberFormatException e) {
            return Optional.empty();
        }
        return Optional.empty();
    };

    public void insertCustomer(Customer customer) {
        String query = "INSERT into Customer(customer_id, name, address) values (?, ?, ?);";
        try {
            jdbcTemplate.update(query, customer.getId(), customer.getName(), customer.getAddress());
        } catch (DataAccessException e) {
            System.out.println(e);
        }
    }

    public boolean updateCutomer(Customer customer) {
        String query = "UPDATE Customer SET name=?, address=? WHERE customer_id=?;";
        try {
            jdbcTemplate.update(query, customer.getName(), customer.getAddress(), customer.getId());
            return true;
        } catch (DataAccessException e) {
            return false;
        }
    }

    public List<CustomerPlan> getCustomerPlan(int customerId) {
        String query = "SELECT customer_plan.med_id as med_id, name, company, time_till_expiry, customer_id, recurring, last_update_date FROM customer_plan, medicine WHERE customer_plan.med_id=medicine.med_id AND customer_id=?;";
        try {
            List<Map<String, Object>> rows = jdbcTemplate.queryForList(query, customerId);
            List<CustomerPlan> customerPlan = new ArrayList<CustomerPlan>();

            for(Map<String, Object> row : rows) {
                int _med_id = Integer.parseInt(row.get("med_id").toString());
                String _name = row.get("name").toString();
                String _company = row.get("company").toString();
                String _time_till_expiry = row.get("time_till_expiry").toString();
                int _customer_id = Integer.parseInt(row.get("customer_id").toString());
                int _recurring = Integer.parseInt(row.get("recurring").toString());
                String _last_update_date = row.get("last_update_date").toString();

                customerPlan.add(new CustomerPlan(_med_id, _name, _company, _time_till_expiry, _customer_id, _recurring, _last_update_date));
            }
            return customerPlan;

        } catch (DataAccessException | NumberFormatException e) {
            return new ArrayList<CustomerPlan>();
        }
    }

    public List<Medicine> addToPlan(int customerId) {
        String query = "SELECT * FROM medicine WHERE medicine.med_id NOT IN (SELECT customer_plan.med_id FROM customer_plan WHERE customer_id=?);";
        try {
            List<Map<String, Object>> rows = jdbcTemplate.queryForList(query, customerId);
            List<Medicine> addToPlan = new ArrayList<Medicine>();

            for(Map<String, Object> row : rows) {
                int _med_id = Integer.parseInt(row.get("med_id").toString());
                String _name = row.get("name").toString();
                String _company = row.get("company").toString();
                int _cprice = Integer.parseInt(row.get("cprice").toString());
                int _sprice = Integer.parseInt(row.get("sprice").toString());
                String _utility = row.get("utility").toString();
                int _time_till_expiry = Integer.parseInt(row.get("time_till_expiry").toString());

                addToPlan.add(new Medicine(_med_id, _company, _cprice, _name, _sprice, _time_till_expiry, _utility));
            }
            return addToPlan;

        } catch (DataAccessException | NumberFormatException e) {
            return new ArrayList<Medicine>();
        }
    }

    public boolean deleteCustomerPlan(int medId, int customerId) {
        String query = "DELETE FROM customer_plan WHERE med_id=? AND customer_id=?;";
        try {
            jdbcTemplate.update(query, medId, customerId);
            return true;
        } catch (DataAccessException e) {
            return false;
        }
    }

    public boolean addCustomerPlan(int medId, int recurring, int customerId) {
        String query = "INSERT INTO customer_plan(med_id, customer_id, recurring, last_update_date) VALUES (?, ?, ?, ?);";
        String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        try {
            jdbcTemplate.update(query, medId, customerId, recurring, currentDate);
            return true;
        } catch (DataAccessException e) {
            return false;
        }
    }

    public boolean updateCustomerPlan(int medId, int customerId) {
        String query = "UPDATE customer_plan SET last_update_date=? WHERE med_id=? AND customer_id=?;";
        String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        try {
            jdbcTemplate.update(query, currentDate,  medId, customerId);
            return true;
        } catch (DataAccessException e) {
            return false;
        }
    }
}