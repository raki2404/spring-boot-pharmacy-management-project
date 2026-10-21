package com.project26.pharmacy_management.controllers;

import java.util.Map;
import java.util.HashMap;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project26.pharmacy_management.services.MyUserDetailService;

import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.security.Principal;

@RestController
public class MyUserController {

    @Autowired
    private MyUserDetailService myUserDetailService;

    private PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @PostMapping("/add-email")
    @ResponseBody
    public Map<String, Object> addEmail(HttpServletRequest request, Principal principal, Model model) throws IOException {

        String json = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> jsonMap = objectMapper.readValue(json, new TypeReference<Map<String, String>>() {});

        try {
            String newEmail = jsonMap.get("new-email");

            boolean alreadyPresent = myUserDetailService.isEmailPresent(principal.getName(), newEmail);
            if(alreadyPresent) {
                Map<String, Object> jsonResponse = new HashMap<>();
                jsonResponse.put("message", "Email already present");
                return jsonResponse;
            } else {
                myUserDetailService.addEmail(principal.getName(), newEmail);
                Map<String, Object> jsonResponse = new HashMap<>();
                jsonResponse.put("message", "Added new email");
                return jsonResponse;
            }
        } catch(Exception e) {
            Map<String, Object> jsonResponse = new HashMap<>();
            jsonResponse.put("message", "Email could not be added");
            return jsonResponse;
        }
    }

    @PostMapping("/delete-email")
    @ResponseBody
    public Map<String, Object> deleteEmail(HttpServletRequest request, Principal principal, Model model) throws IOException {

        String json = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> jsonMap = objectMapper.readValue(json, new TypeReference<Map<String, String>>() {});

        try {
            String deleteEmail = jsonMap.get("delete-email");

            myUserDetailService.deleteEmail(principal.getName(), deleteEmail);
            Map<String, Object> jsonResponse = new HashMap<>();
            jsonResponse.put("message", "Deleted email");
            return jsonResponse;
        } catch(Exception e) {
            Map<String, Object> jsonResponse = new HashMap<>();
            jsonResponse.put("message", "Email could not be deletd");
            return jsonResponse;
        }
    }

    @PostMapping("/add-phone")
    @ResponseBody
    public Map<String, Object> addPhone(HttpServletRequest request, Principal principal, Model model) throws IOException {

        String json = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> jsonMap = objectMapper.readValue(json, new TypeReference<Map<String, String>>() {});

        try {
            String newPhone = jsonMap.get("new-phone");

            boolean alreadyPresent = myUserDetailService.isPhonePresent(principal.getName(), newPhone);
            if(alreadyPresent) {
                Map<String, Object> jsonResponse = new HashMap<>();
                jsonResponse.put("message", "Phone already present");
                return jsonResponse;
            } else {
                myUserDetailService.addPhone(principal.getName(), newPhone);
                Map<String, Object> jsonResponse = new HashMap<>();
                jsonResponse.put("message", "Added new phone");
                return jsonResponse;
            }
        } catch(Exception e) {
            Map<String, Object> jsonResponse = new HashMap<>();
            jsonResponse.put("message", "Phone could not be added");
            return jsonResponse;
        }
    }

    @PostMapping("/delete-phone")
    @ResponseBody
    public Map<String, Object> deletePhone(HttpServletRequest request, Principal principal, Model model) throws IOException {

        String json = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> jsonMap = objectMapper.readValue(json, new TypeReference<Map<String, String>>() {});

        try {
            String deletePhone = jsonMap.get("delete-phone");

            myUserDetailService.deletePhone(principal.getName(), deletePhone);
            Map<String, Object> jsonResponse = new HashMap<>();
            jsonResponse.put("message", "Deleted phone");
            return jsonResponse;
        } catch(Exception e) {
            Map<String, Object> jsonResponse = new HashMap<>();
            jsonResponse.put("message", "Phone could not be deletd");
            return jsonResponse;
        }
    }

    @PostMapping("/update-password")
    @ResponseBody
    public Map<String, Object> updatePassword(HttpServletRequest request, Principal principal, Model model) throws IOException {

        String json = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> jsonMap = objectMapper.readValue(json, new TypeReference<Map<String, String>>() {});
        
        try {
            if (!jsonMap.get("new-password").equals(jsonMap.get("confirm-password"))) {
                Map<String, Object> jsonResponse = new HashMap<>();
                jsonResponse.put("message", "New passwords do not match");
                return jsonResponse;
            } else {
                PasswordEncoder passwordEncoder = passwordEncoder();
                Optional<String> oldEncryptedPassword = myUserDetailService.getEncryptedPassword(principal.getName());

                if(!oldEncryptedPassword.isPresent()) {
                    Map<String, Object> jsonResponse = new HashMap<>();
                    jsonResponse.put("message", "Old Password could not be found");
                    return jsonResponse;
                } else if (!passwordEncoder.matches(jsonMap.get("old-password"), oldEncryptedPassword.get())) {
                    Map<String, Object> jsonResponse = new HashMap<>();
                    jsonResponse.put("message", "Old password is not correct");
                    return jsonResponse;
                } else {
                    myUserDetailService.updatePassword(principal.getName(), jsonMap.get("new-password"));
                    Map<String, Object> jsonResponse = new HashMap<>();
                    jsonResponse.put("message", "Password updated successfully");
                    return jsonResponse;
                }
            }
        } catch (Exception e) {
            Map<String, Object> jsonResponse = new HashMap<>();
            jsonResponse.put("message", e.getMessage());
            return jsonResponse;
        }
    }
}
