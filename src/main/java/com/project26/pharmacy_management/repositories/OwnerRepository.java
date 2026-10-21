package com.project26.pharmacy_management.repositories;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.project26.pharmacy_management.models.Owner;

@Repository
public class OwnerRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public Optional<Owner> findOwner(String username) {
        String query = "SELECT * FROM Owner, MyUser WHERE Owner.owner_id = MyUser.id and MyUser.username=?";
        try {
            List<Map<String, Object>> rows = jdbcTemplate.queryForList(query, username);
            for(Map<String, Object> row : rows) {
                int _id = Integer.parseInt(row.get("owner_id").toString());
                String _username = row.get("username").toString();
                String _password = row.get("password").toString();
                String _role = row.get("role").toString();
                String _fname = row.get("first_name").toString();
                String _lname = row.get("last_name").toString();
                String _address = row.get("address").toString();
                int _partnership_percent = Integer.parseInt(row.get("partnership_percent").toString());
                return Optional.of(new Owner(_address, _fname, _lname, _partnership_percent, _id, _username, _password, _role));
            }
        } catch (DataAccessException | NumberFormatException e) {
            return Optional.empty();
        }
        return Optional.empty();
    };

    public boolean updateOwner(Owner owner){
        try {
            String query="UPDATE OWNER SET first_name=?,last_name=?,address=? WHERE owner_id=?";
            jdbcTemplate.update(query,owner.getFirst_name(),owner.getLast_name(),owner.getAddress(),owner.getId());
            return true;
        } catch (DataAccessException e) {
            return false;
        }
    };
}