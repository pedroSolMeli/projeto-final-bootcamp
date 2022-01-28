package com.mercadolivre.projetointegrador.warehouse.controller;

import com.mercadolivre.projetointegrador.warehouse.model.Warehouse;
import com.mercadolivre.projetointegrador.warehouse.service.WarehouseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("WarehouseController")
@RequestMapping("/warehouse")
public class WarehouseController {

    @Autowired
    WarehouseService service;

    @PostMapping()
    public ResponseEntity<?> create(@RequestBody Warehouse warehouse) {
        Warehouse result = service.createWarehouse(warehouse);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<?> findAll() {
        List<Warehouse> result = service.findAll();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
