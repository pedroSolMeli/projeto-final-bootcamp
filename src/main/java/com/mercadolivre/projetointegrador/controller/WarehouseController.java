package com.mercadolivre.projetointegrador.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mercadolivre.projetointegrador.model.InboundOrder;
import com.mercadolivre.projetointegrador.model.Warehouse;
import com.mercadolivre.projetointegrador.repository.WarehouseRepository;
import com.mercadolivre.projetointegrador.service.WarehouseService;

@RestController
@RequestMapping("/warehouse")
public class WarehouseController {
	
	@Autowired
	WarehouseService service;
	
	
	 @PostMapping()
	    public ResponseEntity<?> create(@RequestBody Warehouse warehouse){
	        Warehouse result =service.createwarehouse(warehouse);
	        return new ResponseEntity<>(result, HttpStatus.CREATED);
	    }

}
