package com.mercadolivre.projetointegrador.stock.service;

import com.mercadolivre.projetointegrador.stock.dto.InboundOrderRequestDto;
import com.mercadolivre.projetointegrador.stock.dto.InboundOrderResponseDto;
import com.mercadolivre.projetointegrador.stock.model.Batch;
import com.mercadolivre.projetointegrador.stock.model.InboundOrder;
import com.mercadolivre.projetointegrador.stock.repository.InboundOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InboundOrderService {

    @Autowired
    InboundOrderRepository repository;

    public InboundOrder createInboundOrder(InboundOrderRequestDto inboundOrderRequestDto) {
        InboundOrder inboundOrder = convertToObject(inboundOrderRequestDto);
        return repository.saveAndFlush(inboundOrder);
    }

    public List<InboundOrder> findAllInboundOrders() {
        return repository.findAll();
    }

    public InboundOrder updateInboundOrder(InboundOrder inboundOrder) {
        return repository.saveAndFlush(inboundOrder);
    }

    public List<InboundOrderResponseDto> convertToDto(List<InboundOrder> inboundOrderList) {
        ArrayList<InboundOrderResponseDto> result = inboundOrderList.stream().map(InboundOrderResponseDto::new).collect(Collectors.toCollection(ArrayList::new));
        return result;
    }

    public InboundOrderResponseDto convertToDto(InboundOrder inboundOrder) {
        List<Batch> batchStock = inboundOrder.getBatchStock();
        InboundOrderResponseDto dto = InboundOrderResponseDto.builder().batchStock(batchStock).build();
        return dto;
    }

    public InboundOrder convertToObject(InboundOrderRequestDto inboundOrderRequestDto) {

        InboundOrder inboundOrder = inboundOrderRequestDto.getInboundOrder();
        InboundOrder object = InboundOrder.builder()
                .orderNumber(inboundOrder.getOrderNumber())
                .orderDate(inboundOrder.getOrderDate())
                //.section(inboundOrder.getSection())
                .batchStock(inboundOrder.getBatchStock())
                .build();
        return object;

    }


}
