package com.mercadolivre.projetointegrador.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mercadolivre.projetointegrador.model.Warehouse;
import com.mercadolivre.projetointegrador.repository.WarehouseRepository;

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
