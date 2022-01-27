package com.mercadolivre.projetointegrador.controller;

import com.mercadolivre.projetointegrador.model.InboundOrder;
import com.mercadolivre.projetointegrador.service.InboundOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inboundorder")
public class InboundOrderController {

    @Autowired
    InboundOrderService service;

    @PostMapping()
    public ResponseEntity<?> create(@RequestBody InboundOrder inboundOrder){
        InboundOrder result = service.createInboundOrder(inboundOrder);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PutMapping()
    public ResponseEntity<?> update(@RequestBody InboundOrder inboundOrder){
        InboundOrder result = service.updateInboundOrder(inboundOrder);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }
   

    

}
