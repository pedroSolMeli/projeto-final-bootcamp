package com.mercadolivre.projetointegrador.inboundorder.controller;

import com.mercadolivre.projetointegrador.inboundorder.dto.InboundOrderRequestDto;
import com.mercadolivre.projetointegrador.inboundorder.dto.InboundOrderResponseDto;
import com.mercadolivre.projetointegrador.inboundorder.model.InboundOrder;
import com.mercadolivre.projetointegrador.inboundorder.service.InboundOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("InboundOrderController")
@RequestMapping("/inboundorder")
public class InboundOrderController {

    @Autowired
    InboundOrderService inboundOrderService;

    @PostMapping()
    public ResponseEntity<?> create(@RequestBody InboundOrderRequestDto inboundOrderRequestDto,
                                    @RequestHeader(value = "Authorization") String authHeader) {
        InboundOrderResponseDto result = inboundOrderService.createInboundOrder(inboundOrderRequestDto, authHeader);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> read() {
        List<InboundOrderResponseDto> result = inboundOrderService.findAllInboundOrders();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity<?> update(@RequestBody InboundOrder inboundOrder) {
        InboundOrder result = inboundOrderService.updateInboundOrder(inboundOrder);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }


}
