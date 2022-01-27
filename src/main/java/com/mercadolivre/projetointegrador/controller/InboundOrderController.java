package com.mercadolivre.projetointegrador.controller;

import com.mercadolivre.projetointegrador.dto.InboundOrderRequestDto;
import com.mercadolivre.projetointegrador.dto.InboundOrderResponseDto;
import com.mercadolivre.projetointegrador.model.InboundOrder;
import com.mercadolivre.projetointegrador.service.InboundOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inboundorder")
public class InboundOrderController {

    @Autowired
    InboundOrderService service;

    @PostMapping()
    public ResponseEntity<?> create(@RequestBody InboundOrderRequestDto inboundOrderRequestDto) {
        InboundOrder result = service.createInboundOrder(inboundOrderRequestDto);
        InboundOrderResponseDto response = service.convertToDto(result);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> read() {
        List<InboundOrder> result = service.findAllInboundOrders();
        List<InboundOrderResponseDto> response = service.convertToDto(result);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity<?> update(@RequestBody InboundOrder inboundOrder) {
        InboundOrder result = service.updateInboundOrder(inboundOrder);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }


}
