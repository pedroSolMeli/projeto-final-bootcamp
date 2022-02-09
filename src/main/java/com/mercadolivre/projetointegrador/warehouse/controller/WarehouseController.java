package com.mercadolivre.projetointegrador.warehouse.controller;

import com.mercadolivre.projetointegrador.warehouse.dto.WarehouseRequestDto;
import com.mercadolivre.projetointegrador.warehouse.dto.WarehouseResponseDto;
import com.mercadolivre.projetointegrador.warehouse.dto.WarehousesByProductResponseDto;
import com.mercadolivre.projetointegrador.warehouse.model.Warehouse;
import com.mercadolivre.projetointegrador.warehouse.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController("WarehouseController")
@RequestMapping("/warehouse")
public class WarehouseController {

    @Autowired
    WarehouseService service;

    @PostMapping()
    public ResponseEntity<?> create(@Valid @RequestBody WarehouseRequestDto warehouseRequestDto, @RequestHeader(value = "Authorization") String authHeader) {
        WarehouseResponseDto result = service.createWarehouse(warehouseRequestDto, authHeader);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<?> findAll() {
        List<Warehouse> result = service.findAllWarehouses();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<?> findByProductId(@PathVariable Long productId) {
        WarehousesByProductResponseDto result = service.getWarehousesByProducts(productId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
