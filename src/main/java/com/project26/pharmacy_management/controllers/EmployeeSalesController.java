package com.project26.pharmacy_management.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project26.pharmacy_management.services.EmployeeService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class EmployeeSalesController {
    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/employee/sales")
    @ResponseBody
    public Map<String, Object> finishOrder(HttpServletRequest request) throws IOException {
        String json = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> jsonMap = objectMapper.readValue(json, new TypeReference<Map<String, String>>() {});

        Map<String, Object> jsonResponse = new HashMap<>();
        
        try {
            if(jsonMap.get("sale_id") == null){
                jsonResponse.put("message", "Invalid sale");
                return jsonResponse;
            }
            Integer saleId = Integer.valueOf(jsonMap.get("sale_id"));
            if(employeeService.finishSale(saleId, request.getUserPrincipal().getName()))
                jsonResponse.put("message", "Finished the sale!!");
            else
                jsonResponse.put("message", "Some error occured during the sale");
        } catch (NumberFormatException e) {
            jsonResponse.put("message", "Some error occured during the sale");
        }
        return jsonResponse;
    }
}
