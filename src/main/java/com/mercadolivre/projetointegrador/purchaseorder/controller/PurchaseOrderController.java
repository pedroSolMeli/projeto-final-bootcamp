package com.mercadolivre.projetointegrador.purchaseorder.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mercadolivre.projetointegrador.purchaseorder.dto.PurchaseOrderDto;
import com.mercadolivre.projetointegrador.purchaseorder.model.PurchaseOrder;
import com.mercadolivre.projetointegrador.purchaseorder.service.PurchaseOrderService;

@RestController("PurchaseOrderController")
@RequestMapping("/purchaseorder")
public class PurchaseOrderController {

    @Autowired
    PurchaseOrderService service;

    
    @PostMapping()
    public ResponseEntity<?> create(@RequestBody PurchaseOrderDto order) {
        PurchaseOrder result = service.createaPurchaseOrder(order);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }
    
//    @PostMapping()
//    public ResponseEntity<?> create(@RequestBody PurchaseOrderDto order) {
//        BigDecimal result = service.createaPurchaseOrder(order);
//        return new ResponseEntity<>(result, HttpStatus.CREATED);
//    }
//    
    

}
