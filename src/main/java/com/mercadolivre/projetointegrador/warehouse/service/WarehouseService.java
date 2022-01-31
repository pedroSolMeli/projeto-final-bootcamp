package com.mercadolivre.projetointegrador.warehouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mercadolivre.projetointegrador.warehouse.model.Warehouse;
import com.mercadolivre.projetointegrador.warehouse.repository.WarehouseRepository;

import java.util.List;

@Service
public class WarehouseService {

    @Autowired
    WarehouseRepository repository;

    public Warehouse createWarehouse(Warehouse warehouse) {
        return repository.saveAndFlush(warehouse);
    }

    public List<Warehouse> findAll() {
        return repository.findAll();
    }
}
