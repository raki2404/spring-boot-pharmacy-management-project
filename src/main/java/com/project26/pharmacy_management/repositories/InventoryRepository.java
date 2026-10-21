package com.project26.pharmacy_management.repositories;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.project26.pharmacy_management.models.Inventory;

@Repository
public class InventoryRepository {
    
    @Autowired
    JdbcTemplate jdbcTemplate;

    public Optional<List<Inventory>> findAll(){
        String query="SELECT * FROM Inventory, Medicine WHERE Medicine.med_id = Inventory.med_id";
        List<Inventory> inventory= new ArrayList<> ();
        try {
            List<Map<String,Object>> rows=jdbcTemplate.queryForList(query);
            for(Map<String,Object> row : rows){
                int _inv_id = Integer.parseInt(row.get("inv_id").toString());
                int _med_id = Integer.parseInt(row.get("med_id").toString());
                String _name = row.get("name").toString();
                String _company = row.get("company").toString();
                int _sprice = Integer.parseInt(row.get("sprice").toString());
                String _utility = row.get("utility").toString();
                int _timeTillExpiry = Integer.parseInt(row.get("time_till_expiry").toString());
                int _quantity = Integer.parseInt(row.get("quantity").toString());
                String _date_of_man = row.get("date_of_man").toString();
                
                inventory.add(new Inventory(_company, _date_of_man, _inv_id, _med_id, _name, _quantity, _sprice, _timeTillExpiry, _utility));
            }
            return Optional.of(inventory);
        }  catch (DataAccessException | NumberFormatException e) {
            return Optional.empty();
        }
    }

    public boolean updateInventory(Map<Integer, Integer> cart){
        try {
            String query="UPDATE Inventory SET quantity=quantity-? WHERE inv_id=?;";
            for(Map.Entry<Integer, Integer> inventory : cart.entrySet()){
                jdbcTemplate.update(query, inventory.getValue(), inventory.getKey());
            }
            jdbcTemplate.execute("SET SQL_SAFE_UPDATES = 0;");

            String sql = "DELETE FROM inventory WHERE quantity = ?;";
            jdbcTemplate.update(sql, 0);

            jdbcTemplate.execute("SET SQL_SAFE_UPDATES = 1;");
            return true; 
        } catch (DataAccessException e) {
            return false;
        }
    }


    public Optional<Map<Integer, Integer>> getMedicines(Map<Integer, Integer> cart){
        String query="SELECT med_id ,quantity FROM Inventory WHERE inv_id=?;";
        Map<Integer, Integer> med_ids = new HashMap<> ();
        try {
            for(Map.Entry<Integer, Integer> inventory : cart.entrySet()){
                List<Map<String,Object>> rows = jdbcTemplate.queryForList(query, inventory.getKey());
                if(rows.isEmpty())
                    return Optional.empty();
                for(Map<String,Object> row : rows){
                    int _med_id = Integer.parseInt(row.get("med_id").toString());
                    int _quantity = Integer.parseInt(row.get("quantity").toString());
                    if(_quantity < inventory.getValue())
                        return Optional.empty();
                    med_ids.put(inventory.getKey(), _med_id);
                }
            }
            return Optional.of(med_ids);
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }
}
