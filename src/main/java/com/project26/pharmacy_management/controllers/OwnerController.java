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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project26.pharmacy_management.models.Employee;
import com.project26.pharmacy_management.models.Medicine;
import com.project26.pharmacy_management.models.Owner;
import com.project26.pharmacy_management.models.Supplier;
import com.project26.pharmacy_management.models.SupplierPlan;
import com.project26.pharmacy_management.services.EmployeeService;
import com.project26.pharmacy_management.services.OwnerService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class OwnerController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private OwnerService ownerService;

    @GetMapping("/owner/home")
    public String getOwnerHomePage() {
        return "owner/home";
    }

    @GetMapping("/owner/suppliers")
    public String getSuppliers(Model model){
        List<Supplier> suppliers=ownerService.findSuppliers();
        model.addAttribute("suppliers", suppliers);
        return "owner/suppliers";
    }

    @GetMapping("/owner/supplier/update")
    public String showSupplierUpdateForm(@RequestParam("sup_id") int sup_id, Model model) {
        Supplier supplier = ownerService.findSupplier(sup_id);
        model.addAttribute("supplier", supplier);
        return "owner/supplier_update";
    }

    @PostMapping("/owner/supplier/update")
    public String submitSupplierUpdateForm(@ModelAttribute("supplier") Supplier supplier){
        ownerService.updateSupplier(supplier);
        return "redirect:/owner/suppliers";
    }

    @GetMapping("/owner/supplier/plan")
    public String getCustomerPlan(@RequestParam("sup_id") int sup_id, HttpServletRequest request, Model model) {

        List<SupplierPlan> supplierPlan = ownerService.getSupplierPlan(sup_id);
        List<Medicine> addToPlan = ownerService.getAddToPlan(sup_id);

        model.addAttribute("supplierPlan", supplierPlan);
        model.addAttribute("addToPlan", addToPlan);
        model.addAttribute("supId", sup_id);

        return "owner/supplier-plan";
    }

    @PostMapping("/owner/supplier/add-plan")
    @ResponseBody
    public Map<String, Object> addSupplierPlan(HttpServletRequest request, Model model) throws IOException {

        String json = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> jsonMap = objectMapper.readValue(json, new TypeReference<Map<String, String>>() {});
        
        boolean wasPlanAdded = ownerService.addSupplierPlan(Integer.parseInt(jsonMap.get("add-med-id")), Integer.parseInt(jsonMap.get("recurring")), Integer.parseInt(jsonMap.get("add-sup-id")));

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

    @PostMapping("/owner/supplier/delete-plan")
    @ResponseBody
    public Map<String, Object> deleteSupplierPlan(HttpServletRequest request, Model model) throws IOException {

        String json = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> jsonMap = objectMapper.readValue(json, new TypeReference<Map<String, String>>() {});
        
        boolean wasPlanDeleted = ownerService.deleteSupplierPlan(Integer.parseInt(jsonMap.get("delete-med-id")), Integer.parseInt(jsonMap.get("delete-sup-id")));

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

    @PostMapping("/owner/supplier/update-plan")
    @ResponseBody
    public Map<String, Object> updateSupplierPlan(HttpServletRequest request, Model model) throws IOException {

        String json = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> jsonMap = objectMapper.readValue(json, new TypeReference<Map<String, String>>() {});
        
        boolean wasPlanUpdated = ownerService.updateSupplierPlan(Integer.parseInt(jsonMap.get("update-med-id")), Integer.parseInt(jsonMap.get("update-sup-id")));

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

    @GetMapping("/owner/addsupplier")
    public String addSupplier(Model model){
        Supplier supplier=new Supplier();
        model.addAttribute("supplier", supplier);
        return "owner/addsupplier";
    }

    @PostMapping("/owner/addsupplier")
    public String addForm(@ModelAttribute("supplier") Supplier supplier){
        ownerService.addSupplier(supplier);
        return "redirect:/owner/home";
    }

    @GetMapping("/owner/addemployee")
    public String addEmployee(Model model){
        Employee employee=new Employee();
        model.addAttribute("employee", employee);
        return "owner/addemployee";
    }

    @PostMapping("/owner/addemployee")
    public String submitForm(@ModelAttribute("employee") Employee employee){
        ownerService.addEmployee(employee);
        return "redirect:/owner/home";
    }

    @GetMapping("/owner/employees")
    public String getEmployeeDetails(Model model) {
        List<Employee> employeeList = employeeService.findAllEmployee();
        model.addAttribute("employees", employeeList);
        return "owner/employees";
    }

    @GetMapping("/owner/employee/update")
    public String showUpdateForm(@RequestParam("username") String username, Model model) {
        Employee employee=employeeService.findEmployee(username);
        model.addAttribute("employee", employee);
        return "owner/emp_update";
    }

    @PostMapping("/owner/employee/update")
    public String submitUpdateForm(@ModelAttribute("employee") Employee employee){
        ownerService.updateEmployee(employee);
        return "redirect:/owner/employees";
    }
    
    @GetMapping("/owner/profile")
    public String getOwnerProfilePage(Model model, HttpServletRequest request) {
        String username = request.getUserPrincipal().getName();
        Optional<Owner> owner = ownerService.getOwnerFromUserName(username);
        if(owner.isPresent()) {
            model.addAttribute("username", username);
            model.addAttribute("owner", owner.get());
            return "owner/profile";
        } else {
            return "redirect:/";
        }
    }

    @PostMapping("/owner/profile")
    @ResponseBody
    public Map<String, Object> updateOwnerProfile(HttpServletRequest request) throws IOException {

        String json = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> jsonMap = objectMapper.readValue(json, new TypeReference<Map<String, String>>() {});
        
        Owner updatedOwner = new Owner(jsonMap.get("address"), jsonMap.get("first_name"), jsonMap.get("last_name"), Integer.parseInt(jsonMap.get("partnership_percent")), Integer.parseInt(jsonMap.get("id")), jsonMap.get("username"), jsonMap.get("password"), jsonMap.get("role"));
        boolean wasOwnerSucessfullyUpdated = ownerService.updateOwner(updatedOwner);

        if(wasOwnerSucessfullyUpdated) {
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
