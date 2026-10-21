package com.project26.pharmacy_management.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.project26.pharmacy_management.services.CustomerService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/update")
    @ResponseBody
    public Map<String, Object> updateCart(@RequestBody Map<Integer, Integer> cart, HttpSession session) {
        session.setAttribute("cart", cart);
        Map<String, Object> jsonResponse = new HashMap<>();
        jsonResponse.put("message", "Session Cart Updated Sucessfully !");
        return jsonResponse;
    }

    @PostMapping("/checkout")
    @ResponseBody
    public Map<String, Object> checkout(HttpSession session, HttpServletRequest request) {
        @SuppressWarnings("unchecked")
        Map<Integer, Integer> cart = (Map<Integer, Integer>) session.getAttribute("cart");
        
        if (cart == null || cart.isEmpty()) {
            Map<String, Object> jsonResponse = new HashMap<>();
            jsonResponse.put("message", false);
            return jsonResponse;
        }
        else if(!customerService.CreateCartSale(cart, request.getUserPrincipal().getName())){
            Map<String, Object> jsonResponse = new HashMap<>();
            jsonResponse.put("message", false);
            return jsonResponse;
        } else {
            session.removeAttribute("cart");
            Map<String, Object> jsonResponse = new HashMap<>();
            jsonResponse.put("message", true);
            return jsonResponse;
        }
    }
}
