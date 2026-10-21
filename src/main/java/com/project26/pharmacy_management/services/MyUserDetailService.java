package com.project26.pharmacy_management.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project26.pharmacy_management.models.MyUser;
import com.project26.pharmacy_management.repositories.MyUserRepository;

@Service
public class MyUserDetailService implements  UserDetailsService {

    @Autowired
    private MyUserRepository repository;

    private PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<MyUser> user = repository.findUser(username);
        if (user.isPresent()) {
            MyUser userObj = user.get();
            return User.builder().username(userObj.getUsername()).password(userObj.getPassword()).roles(getRoles(userObj)).build();
        } else {
            throw new UsernameNotFoundException("Username not found : " + username);
        }
    }

    private String getRoles(MyUser user) {
        if(user.getRole() == null) {
            return "CUSTOMER";
        }
        return user.getRole();
    }

    public int getIdByUsername(String username){
        Optional<MyUser> user = repository.findUser(username);
        if (user.isPresent()) {
            MyUser userObj = user.get();
            return userObj.getId();
        }
        return -1;
    }

    public Optional<MyUser> SaveUser(MyUser user) {
        Optional<MyUser> temp = repository.findUser(user.getUsername());
        if(temp.isPresent()){
            return Optional.empty();
        } 
        else {
            PasswordEncoder encoder = passwordEncoder();
            user.setPassword(encoder.encode(user.getPassword()));
            repository.insertUser(user);
            return Optional.of(user);
        }
    }

    public Optional<String> getEncryptedPassword(String username) {
        return repository.getEncryptedPassword(username);
    }

    public boolean updatePassword(String username, String password) {
        PasswordEncoder encoder = passwordEncoder();
        boolean wasUpdated = repository.updatePassword(username, encoder.encode(password));
        return wasUpdated;
    }

    public List<String> getEmails(String username) {
        int userId = getIdByUsername(username);
        List<String> emails = repository.getEmailsById(userId);
        return emails;
    }

    public boolean isEmailPresent(String username, String newEmail) {
        int userId = getIdByUsername(username);
        boolean wasEmailFound = repository.isEmailPresent(userId, newEmail);
        return wasEmailFound;
    }

    public boolean addEmail(String username, String newEmail) {
        int userId = getIdByUsername(username);
        boolean wasEmailAdded = repository.addEmail(userId, newEmail);
        return wasEmailAdded;
    }

    public boolean deleteEmail(String username, String deleteEmail) {
        int userId = getIdByUsername(username);
        boolean wasEmailDeleted = repository.deleteEmail(userId, deleteEmail);
        return wasEmailDeleted;
    }

    public List<String> getPhones(String username) {
        int userId = getIdByUsername(username);
        List<String> emails = repository.getPhonesById(userId);
        return emails;
    }

    public boolean isPhonePresent(String username, String newEmail) {
        int userId = getIdByUsername(username);
        boolean wasEmailFound = repository.isPhonePresent(userId, newEmail);
        return wasEmailFound;
    }

    public boolean addPhone(String username, String newEmail) {
        int userId = getIdByUsername(username);
        boolean wasEmailAdded = repository.addPhone(userId, newEmail);
        return wasEmailAdded;
    }

    public boolean deletePhone(String username, String deletePhone) {
        int userId = getIdByUsername(username);
        boolean wasPhoneDeleted = repository.deletePhone(userId, deletePhone);
        return wasPhoneDeleted;
    }
}
