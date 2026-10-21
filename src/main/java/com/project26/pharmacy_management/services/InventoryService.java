package com.project26.pharmacy_management.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project26.pharmacy_management.models.Inventory;
import com.project26.pharmacy_management.repositories.InventoryRepository;

@Service
public class InventoryService {
    @Autowired
    private InventoryRepository repository;

    public List<Inventory> findAllInventory(){
        Optional<List<Inventory>> inventoryOptional = repository.findAll();
        if(inventoryOptional.isEmpty())
            return new ArrayList<>();
        return inventoryOptional.get();
    }
}
