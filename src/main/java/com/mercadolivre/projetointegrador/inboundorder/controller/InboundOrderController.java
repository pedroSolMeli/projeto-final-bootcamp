package com.mercadolivre.projetointegrador.inboundorder.controller;

import com.mercadolivre.projetointegrador.inboundorder.dto.InboundOrderRequestDto;
import com.mercadolivre.projetointegrador.inboundorder.dto.InboundOrderResponseDto;
import com.mercadolivre.projetointegrador.inboundorder.model.InboundOrder;
import com.mercadolivre.projetointegrador.inboundorder.service.InboundOrderService;
import com.mercadolivre.projetointegrador.section.model.Section;
import com.mercadolivre.projetointegrador.section.service.SectionService;
import com.mercadolivre.projetointegrador.warehouse.model.Warehouse;
import com.mercadolivre.projetointegrador.warehouse.service.WarehouseService;
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
    public ResponseEntity<?> create(@RequestBody InboundOrderRequestDto inboundOrderRequestDto) {
        InboundOrder result = inboundOrderService.createInboundOrder(inboundOrderRequestDto);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> read() {
        List<InboundOrder> result = inboundOrderService.findAllInboundOrders();
        List<InboundOrderResponseDto> response = inboundOrderService.convertToDto(result);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity<?> update(@RequestBody InboundOrder inboundOrder) {
        InboundOrder result = inboundOrderService.updateInboundOrder(inboundOrder);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }


}
