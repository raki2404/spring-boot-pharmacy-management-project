package com.project26.pharmacy_management.services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project26.pharmacy_management.models.Customer;
import com.project26.pharmacy_management.models.CustomerPlan;
import com.project26.pharmacy_management.models.Medicine;
import com.project26.pharmacy_management.models.MyUser;
import com.project26.pharmacy_management.models.Sales;
import com.project26.pharmacy_management.repositories.CustomerRepository;
import com.project26.pharmacy_management.repositories.InventoryRepository;
import com.project26.pharmacy_management.repositories.MedicineRepository;
import com.project26.pharmacy_management.repositories.MyUserRepository;
import com.project26.pharmacy_management.repositories.SalesRepository;


@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private MyUserRepository myUserRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private MedicineRepository medicineRepository;

    @Autowired
    private SalesRepository salesRepository;

    @Autowired
    private MyUserDetailService userService;
    
    public Optional<Customer> SaveCustomer(Customer customer){
        customer.setRole("CUSTOMER");
        Optional<MyUser> user = userService.SaveUser((MyUser) customer);
        if(user.isPresent()){
            int id = userService.getIdByUsername(customer.getUsername());
            customer.setId(id);
            customerRepository.insertCustomer(customer);
            return Optional.of(customer);
        }
        else{
            return Optional.empty();
        }
    }

    public Optional<Customer> getCustomerFromUserName(String username){
        Optional<Customer> customer = customerRepository.findCustomer(username);
        if(customer.isPresent()){
            return customer;
        }
        else{
            return Optional.empty();
        }
    }

    public boolean updateCustomer(Customer toUpdateCustomer){
        boolean wasCustomerUpdated = customerRepository.updateCutomer(toUpdateCustomer);
        return wasCustomerUpdated;
    }

    public boolean CreateCartSale(Map<Integer, Integer> Cart, String username){
        Optional<MyUser> userOptional = myUserRepository.findUser(username);
        if(!userOptional.isPresent()){
            return false;
        }
        MyUser user = userOptional.get();
        Optional<Map<Integer, Integer>> medOptional = inventoryRepository.getMedicines(Cart); 
        if(!medOptional.isPresent()){
            return false;
        }
        Map<Integer, Integer> medIds = medOptional.get();
        if(!inventoryRepository.updateInventory(Cart)){
            return false;
        }
        Optional<Map<Integer, Integer>> priceOptional = medicineRepository.getPrice(new ArrayList<>(medIds.values()));
        if(!priceOptional.isPresent()){
            return false;
        }
        Map<Integer, Integer> price = priceOptional.get();
        Integer totalAmount = 0;
        for(Map.Entry<Integer, Integer> inventory : Cart.entrySet()){
            totalAmount += price.get(medIds.get(inventory.getKey()))*inventory.getValue();
        }
        LocalDate today = LocalDate.now();
        DateTimeFormatter mysqlFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String mysqlDateString = today.format(mysqlFormatter);

        Sales sale = new Sales(-1, user.getId(), -1, mysqlDateString, totalAmount);
        Integer saleId = salesRepository.createSale(sale);

        if(saleId == -1)
            return false;

        return salesRepository.addSales(Cart, medIds, saleId);
    }

    public List<CustomerPlan> getCustomerPlan(String username) {
        int customerId = userService.getIdByUsername(username);
        return customerRepository.getCustomerPlan(customerId);
    }

    public List<Medicine> getAddToPlan(String username) {
        int customerId = userService.getIdByUsername(username);
        return customerRepository.addToPlan(customerId);
    }

    public boolean deleteCustomerPlan(int medId, int customerId) {
        return customerRepository.deleteCustomerPlan(medId, customerId);
    }

    public boolean addCustomerPlan(int medId, int recurring, String username) {
        int customerId = userService.getIdByUsername(username);
        return customerRepository.addCustomerPlan(medId, recurring, customerId);
    }

    public boolean updateCustomerPlan(int medId, int customerId) {
        return customerRepository.updateCustomerPlan(medId, customerId);
    }
}
