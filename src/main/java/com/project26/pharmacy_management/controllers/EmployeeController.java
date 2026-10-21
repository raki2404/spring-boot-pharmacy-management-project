package com.project26.pharmacy_management.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
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
import com.project26.pharmacy_management.models.Employee;
import com.project26.pharmacy_management.models.Sales;
import com.project26.pharmacy_management.services.EmployeeService;

import jakarta.servlet.http.HttpServletRequest;



@Controller
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/employee/home")
    public String getEmployeeHomePage() {
        return "employee/home";
    }

    @GetMapping("/employee/sales")
    public String getSalesPage(Model model){
        Map<Sales, Customer> pendingSales = employeeService.findPendingSales();
        model.addAttribute("pendingSales", pendingSales);
        return "employee/sales";
    }

    @GetMapping("/employee/profile")
    public String getEmployeeProfilePage(Model model, HttpServletRequest request) {
        String username = request.getUserPrincipal().getName();
        Employee employee = employeeService.findEmployee(username);
        if(employee.getId() != 0) {
            model.addAttribute("username", username);
            model.addAttribute("employee", employee);
            return "employee/profile";
        } else {
            return "redirect:/";
        }
    }

    @PostMapping("/employee/profile")
    @ResponseBody
    public Map<String, Object> updateEmployeeProfile(HttpServletRequest request) throws IOException {

        String json = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> jsonMap = objectMapper.readValue(json, new TypeReference<Map<String, String>>() {});
        
        System.out.println(jsonMap);

        Employee updatedEmployee = new Employee(jsonMap.get("address"), jsonMap.get("date_of_joining"), jsonMap.get("emp_role"), jsonMap.get("first_name"), jsonMap.get("last_name"), Integer.parseInt(jsonMap.get("salary")), Integer.parseInt(jsonMap.get("id")), jsonMap.get("username"), jsonMap.get("password"), jsonMap.get("role"));
        boolean wasEmployeeSucessfullyUpdated = employeeService.updateEmployee(updatedEmployee);

        if(wasEmployeeSucessfullyUpdated) {
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
