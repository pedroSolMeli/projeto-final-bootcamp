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
        Boolean update = false; 
        InboundOrderResponseDto result = inboundOrderService.createInboundOrder(inboundOrderRequestDto, authHeader, update);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> read() {
        List<InboundOrderResponseDto> result = inboundOrderService.findAllInboundOrders();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity<?> update(@RequestBody InboundOrderRequestDto inboundOrder
				   @RequestHeader(value = "Authorization") String authHeader) {
    	Boolean update = true; 
        InboundOrderResponseDto result = inboundOrderService.updateInboundOrder(inboundOrder, update);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }


}
