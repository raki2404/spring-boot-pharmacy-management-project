package com.project26.pharmacy_management.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.project26.pharmacy_management.models.Customer;
import com.project26.pharmacy_management.models.Medicine;
import com.project26.pharmacy_management.repositories.MedicineRepository;
import com.project26.pharmacy_management.services.CustomerService;
import com.project26.pharmacy_management.services.MyUserDetailService;

import jakarta.servlet.http.HttpServletRequest;



@Controller
public class ContentController {
    
    @Autowired
    private MedicineRepository med_repo;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private MyUserDetailService myUserDetailService;

    @GetMapping("/")
    public String getIndexPage(HttpServletRequest request) {
        if (request.isUserInRole("OWNER"))
            return "redirect:/owner/home";
        else if (request.isUserInRole("EMPLOYEE"))
            return "redirect:/employee/home";
        else if (request.isUserInRole("CUSTOMER"))
            return "redirect:/customer/home";
        else
            return "index";
    }

    @GetMapping("/login")
    public String getLoginPage(HttpServletRequest request) {
        if (request.isUserInRole("OWNER"))
            return "redirect:/owner/home";
        else if (request.isUserInRole("EMPLOYEE"))
            return "redirect:/employee/home";
        else if (request.isUserInRole("CUSTOMER"))
            return "redirect:/customer/home";
        else
            return "login";
    }

    @GetMapping("/logout")
    public String getLogoutPage() {
        return "logout";
    }

    @GetMapping("/medicines")
    public String getMedicines(Model model){
        Optional<List<Medicine>> optionalmedicines= med_repo.findAll();
        List<Medicine> medicines = optionalmedicines.get();
        model.addAttribute("medicines", medicines);
        return "medicines";
    }

    @GetMapping("/signup")
    public String getSignupPage(Model model, HttpServletRequest request) {
        if (request.isUserInRole("OWNER"))
            return "redirect:/owner/home";
        else if (request.isUserInRole("EMPLOYEE"))
            return "redirect:/employee/home";
        else if (request.isUserInRole("CUSTOMER"))
            return "redirect:/customer/home";
        else {
            Customer customer = new Customer();
            model.addAttribute("customer", customer);
            return "signup";
        }
    }

    @PostMapping("/signup")
    public String submitForm(@ModelAttribute("customer") Customer customer, HttpServletRequest request) {
        if (request.isUserInRole("OWNER"))
            return "redirect:/owner/home";
        else if (request.isUserInRole("EMPLOYEE"))
            return "redirect:/employee/home";
        else if (request.isUserInRole("CUSTOMER"))
            return "redirect:/customer/home";
        else {
            customerService.SaveCustomer(customer);
            return "redirect:/customer/home";
        }
    }

    @GetMapping("/welcome")
    public String getDefaultPage(HttpServletRequest request) {
        if (request.isUserInRole("OWNER"))
            return "redirect:/owner/home";
        else if (request.isUserInRole("EMPLOYEE"))
            return "redirect:/employee/home";
        else if (request.isUserInRole("CUSTOMER"))
            return "redirect:/customer/home";
        else
            return "redirect:/";
    }

    @GetMapping("/update-password")
    public String getUpdatePassword(HttpServletRequest request) {
        return "update-password";
    }

    @GetMapping("/registered-emails")
    public String getEmails(HttpServletRequest request, Model model) {
        List<String> emails = myUserDetailService.getEmails(request.getUserPrincipal().getName());
        model.addAttribute("emails", emails);
        return "registered-emails";
    }

    @GetMapping("/registered-phones")
    public String getPhones(HttpServletRequest request, Model model) {
        List<String> phones = myUserDetailService.getPhones(request.getUserPrincipal().getName());
        model.addAttribute("phones", phones);
        return "registered-phones";
    }

}
