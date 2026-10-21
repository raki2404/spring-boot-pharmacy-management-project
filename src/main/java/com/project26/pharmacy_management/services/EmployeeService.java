package com.project26.pharmacy_management.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project26.pharmacy_management.models.Customer;
import com.project26.pharmacy_management.models.Employee;
import com.project26.pharmacy_management.models.MyUser;
import com.project26.pharmacy_management.models.Sales;
import com.project26.pharmacy_management.repositories.CustomerRepository;
import com.project26.pharmacy_management.repositories.EmployeeRepository;
import com.project26.pharmacy_management.repositories.MyUserRepository;
import com.project26.pharmacy_management.repositories.SalesRepository;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private SalesRepository salesRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private MyUserRepository myUserRepository;

    public List<Employee> findAllEmployee(){
        Optional<List<Employee>> employeeOptional = employeeRepository.findAllEmployees();
        if(employeeOptional.isEmpty())
            return new ArrayList<>();
        return employeeOptional.get();
    }

    public Employee findEmployee(String username){
        Optional<Employee> employee_optional=employeeRepository.findEmployee(username);
        if(employee_optional.isPresent()){
            Employee employee=employee_optional.get();
            return employee;
        }
        return new Employee();
    } 

    public Map<Sales, Customer> findPendingSales(){
        Optional<List<Sales>> salesOptional = salesRepository.getPendingSales();
        if(salesOptional.isEmpty())
            return new HashMap<>();
        List<Sales> listPendingSales = salesOptional.get();
        Map<Sales, Customer> pendingSales = new HashMap<>();
        for(Sales sale : listPendingSales){
            Optional<Customer> customerOptional = customerRepository.findCustomerbyId(sale.getCustomer_id());
            if(customerOptional.isEmpty())
                return new HashMap<>();
            pendingSales.put(sale, customerOptional.get());
        }
        return pendingSales;
    }

    public boolean finishSale(Integer sale_id, String username){
        Optional<MyUser> userOptional = myUserRepository.findUser(username);
        if(!userOptional.isEmpty() && userOptional.get().getRole().equals("EMPLOYEE")){
            return salesRepository.finishSale(sale_id, userOptional.get().getId());
        }
        return false;
    }

    public boolean updateEmployee(Employee toUpdateEmployee){
        boolean wasEmployeeUpdated = employeeRepository.updateEmployee(toUpdateEmployee);
        return wasEmployeeUpdated;
    }
}
