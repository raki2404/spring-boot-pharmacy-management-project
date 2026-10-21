package com.project26.pharmacy_management.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.project26.pharmacy_management.models.Sales;

@Repository
public class SalesRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public Integer createSale(Sales sale){
        try {
            String query = "INSERT INTO Sales(customer_id, date_of_sale, total_amount) VALUES (?, ?, ?);";
            jdbcTemplate.update(query, sale.getCustomer_id(), sale.getDate_of_sale(), sale.getTotal_amount());
            String selectQuery = "SELECT MAX(sale_id) FROM Sales;";
            Integer saleId = jdbcTemplate.queryForObject(selectQuery, Integer.class);
            return saleId;
        } catch (DataAccessException e) {
            return -1;
        }
    }

    public boolean addSales(Map<Integer, Integer> cart, Map<Integer, Integer> medIds, Integer saleId){
        try {
            String query = "INSERT INTO sales_med VALUES (?, ?, ?);";
            for (Map.Entry<Integer, Integer> inventory : cart.entrySet()) {
                jdbcTemplate.update(query, saleId, medIds.get(inventory.getKey()), inventory.getValue());
            }
        } catch (DataAccessException e) {
            return false;
        }
        return true;
    }

    public Optional<List<Sales>> getPendingSales(){
        List<Sales> pendingSales = new ArrayList<>();
        try {
            String query = "SELECT * FROM sales WHERE employee_id is NULL";
            List<Map<String, Object>> rows= jdbcTemplate.queryForList(query);
            for(Map<String, Object> row : rows){
                int _sale_id = Integer.parseInt(row.get("sale_id").toString());
                int _customer_id = Integer.parseInt(row.get("customer_id").toString());
                int _total_amount = Integer.parseInt(row.get("total_amount").toString());
                String _date_of_sale = row.get("date_of_sale").toString();
                
                pendingSales.add(new Sales(_sale_id, _customer_id, -1, _date_of_sale, _total_amount));
            }
            return Optional.of(pendingSales);
        } catch (DataAccessException e) {
            return Optional.empty();
        }    
    }

    public boolean finishSale(Integer sale_id, Integer employee_id){
        try {
            String query = "UPDATE sales SET employee_id=? WHERE sale_id=?";
            jdbcTemplate.update(query, employee_id, sale_id);
            return true;
        } catch (DataAccessException e) {
            return false;
        }
    }
}
