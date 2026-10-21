package com.project26.pharmacy_management.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project26.pharmacy_management.models.Employee;
import com.project26.pharmacy_management.models.Medicine;
import com.project26.pharmacy_management.models.MyUser;
import com.project26.pharmacy_management.models.Owner;
import com.project26.pharmacy_management.models.Supplier;
import com.project26.pharmacy_management.models.SupplierPlan;
import com.project26.pharmacy_management.repositories.EmployeeRepository;
import com.project26.pharmacy_management.repositories.OwnerRepository;
import com.project26.pharmacy_management.repositories.SupplierRepository;

@Service
public class OwnerService {

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private SupplierRepository supplierrepository;

    @Autowired
    private EmployeeRepository employeerepository;

    @Autowired
    private MyUserDetailService userService;

    public List<Supplier> findSuppliers(){
        Optional<List<Supplier>> sup_optional=supplierrepository.findAllSuppliers();
        if(sup_optional.isEmpty())
            return new ArrayList<>();
        return sup_optional.get();
    }

    public boolean addSupplier(Supplier supplier){
        try {
            return supplierrepository.createSupplier(supplier);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean addEmployee(Employee employee){
        try {
            employee.setRole("EMPLOYEE");
            Optional<MyUser> user = userService.SaveUser((MyUser) employee);
            if(user.isPresent()){
                int id=userService.getIdByUsername(employee.getUsername());
                employee.setId(id);
                employeerepository.insertEmployee(employee);
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean updateEmployee(Employee employee){
        try {
            return employeerepository.updateEmployee(employee);
        } catch (Exception e) {
            return false;
        }
    }

    public Optional<Owner> getOwnerFromUserName(String username){
        Optional<Owner> owner = ownerRepository.findOwner(username);
        if(owner.isPresent()){
            return owner;
        }
        else{
            return Optional.empty();
        }
    }

    public boolean updateOwner(Owner toUpdateOwner){
        boolean wasOwnerUpdated = ownerRepository.updateOwner(toUpdateOwner);
        return wasOwnerUpdated;
    }

    public Supplier findSupplier(int sup_id){
        Optional<Supplier> supplier_optional = supplierrepository.findSupplier(sup_id);
        if(supplier_optional.isPresent()){
            Supplier supplier = supplier_optional.get();
            return supplier;
        }
        return new Supplier();
    }

    public boolean updateSupplier(Supplier toUpdateSupplier){
        boolean wasSupplierUpdated = supplierrepository.updateSupplier(toUpdateSupplier);
        return wasSupplierUpdated;
    }

    public List<SupplierPlan> getSupplierPlan(int sup_id) {
        return supplierrepository.getSupplierPlan(sup_id);
    }

    public List<Medicine> getAddToPlan(int sup_id) {
        return supplierrepository.addToPlan(sup_id);
    }

    public boolean deleteSupplierPlan(int medId, int supId) {
        return supplierrepository.deleteSupplierPlan(medId, supId);
    }

    public boolean addSupplierPlan(int medId, int recurring, int supId) {
        return supplierrepository.addSupplierPlan(medId, recurring, supId);
    }

    public boolean updateSupplierPlan(int medId, int supId) {
        return supplierrepository.updateSupplierPlan(medId, supId);
    }
}
