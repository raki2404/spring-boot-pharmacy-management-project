package com.project26.pharmacy_management.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.project26.pharmacy_management.models.MyUser;

@Repository
public class MyUserRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public Optional<MyUser> findUser(String username) {
        String query = "SELECT * FROM MyUser WHERE MyUser.username=?;";
        try {
            List<Map<String, Object>> rows = jdbcTemplate.queryForList(query, username);
            for(Map<String, Object> row : rows){
                int _id = Integer.parseInt(row.get("id").toString());
                String _username = row.get("username").toString();
                String _password = row.get("password").toString();
                String _role = row.get("role").toString();
                return Optional.of(new MyUser(_id, _username, _password, _role));
            }
        } catch (DataAccessException | NumberFormatException e) {
            return Optional.empty();
        }
        return Optional.empty();
    };

    public boolean updatePassword(String username, String password) {
        String query = "UPDATE MyUser SET password=? WHERE username=?;";
        try {
            jdbcTemplate.update(query, password, username);
            return true;
        } catch (DataAccessException e) {
            return false;
        }
    }

    public void insertUser(MyUser user) {
        String query = "INSERT into MyUser(username, password, role) values (?, ?, ?);";
        try {
            jdbcTemplate.update(query, user.getUsername(), user.getPassword(), user.getRole());
        } catch (DataAccessException e) {
            System.out.println(e);
        }
    }

    public Optional<String> getEncryptedPassword(String username) {
        String query = "SELECT password FROM myuser WHERE username=?;";
        try {
            String oldEncryptedPassword = jdbcTemplate.queryForObject(query, String.class, username);
            return Optional.of(oldEncryptedPassword);
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    public boolean isEmailPresent(int userId, String email) {
        String query = "SELECT * FROM user_email WHERE user_id=? AND email=?;";
        try {
            List<Map<String, Object>> resultList = jdbcTemplate.queryForList(query, userId, email);
            if(resultList==null || resultList.isEmpty()) {
                return false;
            } 
        } catch (DataAccessException e) {
            return false;
        }
        return true;
    }

    public boolean addEmail(int userId, String email) {
        String query = "INSERT into user_email(user_id, email) VALUES (?, ?);";
        try {
            jdbcTemplate.update(query, userId, email);
            return true;
        } catch (DataAccessException e) {
            return false;
        }
    }

    public boolean deleteEmail(int userId, String email) {
        String query = "DELETE FROM user_email WHERE user_id=? AND email=?;";
        try {
            jdbcTemplate.update(query, userId, email);
            return true;
        } catch (DataAccessException e) {
            return false;
        }
    }

    public List<String> getEmailsById(int userId) {
        String query = "SELECT email FROM user_email WHERE user_id=?;";
        try {
            List<Map<String, Object>> rows= jdbcTemplate.queryForList(query, userId);
            List<String> emails = new ArrayList<>();
            for(Map<String, Object> row: rows) {
                emails.add(row.get("email").toString());
            }
            return emails;
        } catch (DataAccessException e) {
            return new ArrayList<>();
        }
    }

    public boolean isPhonePresent(int userId, String phone) {
        String query = "SELECT * FROM user_phone WHERE user_id=? AND phone=?;";
        try {
            List<Map<String, Object>> resultList = jdbcTemplate.queryForList(query, userId, phone);
            if(resultList==null || resultList.isEmpty()) {
                return false;
            }
        } catch (DataAccessException e) {
            return false;
        }
        return true;
    }

    public boolean addPhone(int userId, String phone) {
        String query = "INSERT into user_phone(user_id, phone) VALUES (?, ?);";
        try {
            jdbcTemplate.update(query, userId, phone);
            return true;
        } catch (DataAccessException e) {
            return false;
        }
    }

    public List<String> getPhonesById(int userId) {
        String query = "SELECT phone FROM user_phone WHERE user_id=?;";
        try {
            List<Map<String, Object>> rows= jdbcTemplate.queryForList(query, userId);
            List<String> phones = new ArrayList<>();
            for(Map<String, Object> row: rows) {
                phones.add(row.get("phone").toString());
            }
            return phones;
        } catch (DataAccessException e) {
            return new ArrayList<>();
        }
    }

    public boolean deletePhone(int userId, String phone) {
        String query = "DELETE FROM user_phone WHERE user_id=? AND phone=?;";
        try {
            jdbcTemplate.update(query, userId, phone);
            return true;
        } catch (DataAccessException e) {
            return false;
        }
    }
    
}
