package com.mercadolivre.projetointegrador.purchaseorder.controller;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mercadolivre.projetointegrador.inboundorder.model.InboundOrder;
import com.mercadolivre.projetointegrador.purchaseorder.dto.PurchaseOrderDto;
import com.mercadolivre.projetointegrador.purchaseorder.model.PurchaseOrder;
import com.mercadolivre.projetointegrador.purchaseorder.repository.PurchaseOrderRepository;
import com.mercadolivre.projetointegrador.purchaseorder.service.PurchaseOrderService;

@RestController("PurchaseOrderController")
@RequestMapping("/purchaseorder")
public class PurchaseOrderController {

    @Autowired
    PurchaseOrderService service;

    
//    @PostMapping()
//    public ResponseEntity<?> create(@RequestBody PurchaseOrderDto order) {
//        PurchaseOrder result = service.createaPurchaseOrder(order);
//        return new ResponseEntity<>(result, HttpStatus.CREATED);
//    }
    
    @PostMapping()
    public ResponseEntity<?> create(@RequestBody PurchaseOrderDto order) {
        BigDecimal result = service.createaPurchaseOrder(order);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }
    
    @GetMapping()
    public ResponseEntity<?> findAll() {
        List<PurchaseOrder> result = service.findAll();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    
    
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
    	PurchaseOrder result = service.findPurchaseOrder(id);
    	return new ResponseEntity<>(result, HttpStatus.OK);
    }
    
    @PutMapping()
    public ResponseEntity<?> update(@RequestBody PurchaseOrder order) {
        PurchaseOrder result = service.updatePurchaseOrder(order);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

   
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        service.deleteById(id);
        return new ResponseEntity<>("Deleted PurchaseOrder id: " + id, HttpStatus.OK);
    }
    
    

}
