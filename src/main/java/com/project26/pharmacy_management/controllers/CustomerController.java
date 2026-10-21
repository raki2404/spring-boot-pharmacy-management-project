package com.project26.pharmacy_management.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project26.pharmacy_management.models.Customer;
import com.project26.pharmacy_management.models.CustomerPlan;
import com.project26.pharmacy_management.models.Inventory;
import com.project26.pharmacy_management.models.Medicine;
import com.project26.pharmacy_management.services.CustomerService;
import com.project26.pharmacy_management.services.InventoryService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class CustomerController {
    
    @Autowired
    private CustomerService customerService;

    @Autowired
    private InventoryService inventoryService;    

    @GetMapping("/customer/home")
    public String getCustomerHomePage() {
        return "customer/home";
    }
    
   @GetMapping("/customer/inventory")
    public String getInventory(Model model, HttpSession session) {
        // Get the inventory list from the service
        List<Inventory> inventoryList = inventoryService.findAllInventory();
        model.addAttribute("inventory", inventoryList);

        // Retrieve the cart from session
        @SuppressWarnings("unchecked")
        Map<Integer, Integer> cart = (Map<Integer, Integer>) session.getAttribute("cart");

        // If the cart is null, initialize it as an empty map
        // Map<Integer, Integer> cart = new HashMap<>();
        if (cart == null) {
            cart = new HashMap<>();
            session.setAttribute("cart", cart); // Store the empty cart in the session
        }

        // Add the cart to the model to be accessible in the Thymeleaf template
        model.addAttribute("cart", cart);

        return "customer/inventory"; // return the Thymeleaf template
    }

    @GetMapping("/customer/profile")
    public String getCustomerProfilePage(Model model, HttpServletRequest request) {
        String username = request.getUserPrincipal().getName();
        Optional<Customer> customer = customerService.getCustomerFromUserName(username);
        if(customer.isPresent()) {
            model.addAttribute("username", username);
            model.addAttribute("customer", customer.get());
            return "customer/profile";
        } else {
            return "redirect:/";
        }
    }

    @PostMapping("/customer/profile")
    @ResponseBody
    public Map<String, Object> updateCustomerProfile(HttpServletRequest request) throws IOException {

        String json = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> jsonMap = objectMapper.readValue(json, new TypeReference<Map<String, String>>() {});
        
        Customer updatedCustomer = new Customer(jsonMap.get("address"), jsonMap.get("name"), Integer.parseInt(jsonMap.get("id")), jsonMap.get("username"), jsonMap.get("password"), jsonMap.get("role"));
        boolean wasCustomerSucessfullyUpdated = customerService.updateCustomer(updatedCustomer);

        if(wasCustomerSucessfullyUpdated) {
            Map<String, Object> jsonResponse = new HashMap<>();
            jsonResponse.put("message", true);
            return jsonResponse;
        } else {
            Map<String, Object> jsonResponse = new HashMap<>();
            jsonResponse.put("message", false);
            return jsonResponse;
        }
    }

    @GetMapping("/customer/customer-plan")
    public String getCustomerPlan(HttpServletRequest request, Model model) {

        String username = request.getUserPrincipal().getName();
        List<CustomerPlan> customerPlan = customerService.getCustomerPlan(username);
        List<Medicine> addToPlan = customerService.getAddToPlan(username);
        model.addAttribute("customerPlan", customerPlan);
        model.addAttribute("addToPlan", addToPlan);

        return "customer/customer-plan";
    }

    @PostMapping("/customer/add-plan")
    @ResponseBody
    public Map<String, Object> addCustomerPlan(HttpServletRequest request, Model model) throws IOException {

        String json = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> jsonMap = objectMapper.readValue(json, new TypeReference<Map<String, String>>() {});
        
        String username = request.getUserPrincipal().getName();
        boolean wasPlanAdded = customerService.addCustomerPlan(Integer.parseInt(jsonMap.get("add-med-id")), Integer.parseInt(jsonMap.get("recurring")), username);

        if(wasPlanAdded) {
            Map<String, Object> jsonResponse = new HashMap<>();
            jsonResponse.put("message", true);
            return jsonResponse;
        } else {
            Map<String, Object> jsonResponse = new HashMap<>();
            jsonResponse.put("message", false);
            return jsonResponse;
        }
    }

    @PostMapping("/customer/delete-plan")
    @ResponseBody
    public Map<String, Object> deleteCustomerPlan(HttpServletRequest request, Model model) throws IOException {

        String json = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> jsonMap = objectMapper.readValue(json, new TypeReference<Map<String, String>>() {});
        
        boolean wasPlanDeleted = customerService.deleteCustomerPlan(Integer.parseInt(jsonMap.get("delete-med-id")), Integer.parseInt(jsonMap.get("delete-customer-id")));

        if(wasPlanDeleted) {
            Map<String, Object> jsonResponse = new HashMap<>();
            jsonResponse.put("message", true);
            return jsonResponse;
        } else {
            Map<String, Object> jsonResponse = new HashMap<>();
            jsonResponse.put("message", false);
            return jsonResponse;
        }
    }

    @PostMapping("/customer/update-plan")
    @ResponseBody
    public Map<String, Object> updateCustomerPlan(HttpServletRequest request, Model model) throws IOException {

        String json = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> jsonMap = objectMapper.readValue(json, new TypeReference<Map<String, String>>() {});
        
        boolean wasPlanUpdated = customerService.updateCustomerPlan(Integer.parseInt(jsonMap.get("update-med-id")), Integer.parseInt(jsonMap.get("update-customer-id")));

        if(wasPlanUpdated) {
            Map<String, Object> jsonResponse = new HashMap<>();
            jsonResponse.put("message", true);
            return jsonResponse;
        } else {
            Map<String, Object> jsonResponse = new HashMap<>();
            jsonResponse.put("message", false);
            return jsonResponse;
        }
    }
}
