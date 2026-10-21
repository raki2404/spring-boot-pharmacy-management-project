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

import com.project26.pharmacy_management.models.Medicine;
import com.project26.pharmacy_management.models.Supplier;
import com.project26.pharmacy_management.models.SupplierPlan;

@Repository
public class SupplierRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public Optional<List<Supplier>> findAllSuppliers(){
        String query="SELECT * FROM SUPPLIER";
        try {
            List<Supplier> suppliers=new ArrayList<>();
            List<Map<String,Object>> rows=jdbcTemplate.queryForList(query);
            for(Map<String,Object> row: rows){
                int _sup_id=Integer.parseInt(row.get("sup_id").toString());
                String _name=row.get("name").toString();
                String _address=row.get("address").toString();
                String _email=row.get("email").toString();
                String _phone=row.get("phone").toString();
                suppliers.add(new Supplier(_sup_id, _name, _address, _email, _phone));
            }
            return Optional.of(suppliers);
        } catch (NumberFormatException | DataAccessException e) {
            return Optional.empty();
        }
    }

    public Optional<Supplier> findSupplier(int sup_id){
        String query="SELECT * FROM supplier where sup_id=?";
        try {
            List<Map<String,Object>> rows=jdbcTemplate.queryForList(query, sup_id);
            for(Map<String,Object> row: rows){
                int _sup_id=Integer.parseInt(row.get("sup_id").toString());
                String _name=row.get("name").toString();
                String _address=row.get("address").toString();
                String _email=row.get("email").toString();
                String _phone=row.get("phone").toString();
                return Optional.of(new Supplier(_sup_id, _name, _address, _email, _phone));
            }
        } catch (NumberFormatException | DataAccessException e) {
            return Optional.empty();
        }
        return Optional.empty();
    }

    public boolean updateSupplier(Supplier supplier){
        String query="UPDATE supplier SET name=?, address=?, email=?, phone=? WHERE sup_id=?;";
        try {
            jdbcTemplate.update(query, supplier.getName(), supplier.getAddress(), supplier.getEmail(), supplier.getPhone(), supplier.getSup_id());
            return true;
        } catch (DataAccessException e) {
            return false;
        }
    }

    public boolean createSupplier(Supplier supplier){
        try {
            String query="INSERT INTO SUPPLIER(name,address,phone,email) VALUES(?,?,?,?)";
            jdbcTemplate.update(query,supplier.getName(),supplier.getAddress(),supplier.getPhone(),supplier.getEmail());
            return true;
        } catch (DataAccessException e) {
            return false;
        }
    }

    public List<SupplierPlan> getSupplierPlan(int sup_id) {
        String query = "SELECT supplier_plan.med_id as med_id, name, company, time_till_expiry, sup_id, recurring, last_update_date FROM supplier_plan, medicine WHERE supplier_plan.med_id=medicine.med_id AND sup_id=?;";
        try {
            List<Map<String, Object>> rows = jdbcTemplate.queryForList(query, sup_id);
            List<SupplierPlan> supplierPlan = new ArrayList<SupplierPlan>();

            for(Map<String, Object> row : rows) {
                int _med_id = Integer.parseInt(row.get("med_id").toString());
                String _name = row.get("name").toString();
                String _company = row.get("company").toString();
                String _time_till_expiry = row.get("time_till_expiry").toString();
                int _sup_id = Integer.parseInt(row.get("sup_id").toString());
                int _recurring = Integer.parseInt(row.get("recurring").toString());
                String _last_update_date = row.get("last_update_date").toString();

                supplierPlan.add(new SupplierPlan(_med_id, _name, _company, _time_till_expiry, _sup_id, _recurring, _last_update_date));
            }
            return supplierPlan;

        } catch (DataAccessException | NumberFormatException e) {
            return new ArrayList<SupplierPlan>();
        }
    }

    public List<Medicine> addToPlan(int sup_id) {
        String query = "SELECT * FROM medicine WHERE medicine.med_id NOT IN (SELECT supplier_plan.med_id FROM supplier_plan WHERE sup_id=?);";
        try {
            List<Map<String, Object>> rows = jdbcTemplate.queryForList(query, sup_id);
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

    public boolean deleteSupplierPlan(int medId, int supId) {
        String query = "DELETE FROM supplier_plan WHERE med_id=? AND sup_id=?;";
        try {
            jdbcTemplate.update(query, medId, supId);
            return true;
        } catch (DataAccessException e) {
            return false;
        }
    }

    public boolean addSupplierPlan(int medId, int recurring, int supId) {
        String query = "INSERT INTO supplier_plan(med_id, sup_id, recurring, last_update_date) VALUES (?, ?, ?, ?);";
        String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        try {
            jdbcTemplate.update(query, medId, supId, recurring, currentDate);
            return true;
        } catch (DataAccessException e) {
            return false;
        }
    }

    public boolean updateSupplierPlan(int medId, int supId) {
        String query = "UPDATE supplier_plan SET last_update_date=? WHERE med_id=? AND sup_id=?;";
        String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        try {
            jdbcTemplate.update(query, currentDate,  medId, supId);
            System.out.println("BBBBBBBBBBBBBBB");
            System.out.println(true);
            return true;
        } catch (DataAccessException e) {
            System.out.println("AAAAAAAAAAAAAAA");
            System.out.println(e);
            return false;
        }
    }

}
