package com.mercadolivre.projetointegrador.purchaseorder.controller;

import com.mercadolivre.projetointegrador.inboundorder.dto.InboundOrderRequestDto;
import com.mercadolivre.projetointegrador.inboundorder.model.InboundOrder;
import com.mercadolivre.projetointegrador.purchaseorder.model.PurchaseOrder;
import com.mercadolivre.projetointegrador.purchaseorder.service.PurchaseOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("PurchaseOrderController")
@RequestMapping("/purchaseorder")
public class PurchaseOrderController {

    @Autowired
    PurchaseOrderService service;

//    @GetMapping
//    public ResponseEntity<?>
    
    @PostMapping()
    public ResponseEntity<?> create(@RequestBody PurchaseOrder order) {
        PurchaseOrder result = service.createaPurchaseOrder(order);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }
    
    

}
