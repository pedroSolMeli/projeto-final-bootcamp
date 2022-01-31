package com.mercadolivre.projetointegrador.inboundorder.service;

import com.mercadolivre.projetointegrador.batch.model.Batch;
import com.mercadolivre.projetointegrador.inboundorder.dto.InboundOrderDto;
import com.mercadolivre.projetointegrador.inboundorder.dto.InboundOrderRequestDto;
import com.mercadolivre.projetointegrador.inboundorder.dto.InboundOrderResponseDto;
import com.mercadolivre.projetointegrador.inboundorder.model.InboundOrder;
import com.mercadolivre.projetointegrador.inboundorder.repository.InboundOrderRepository;
import com.mercadolivre.projetointegrador.section.dto.SectionDto;
import com.mercadolivre.projetointegrador.section.model.Section;
import com.mercadolivre.projetointegrador.section.service.SectionService;
import com.mercadolivre.projetointegrador.warehouse.model.Warehouse;
import com.mercadolivre.projetointegrador.warehouse.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InboundOrderService {

    @Qualifier("InboundOrderRepository")
    @Autowired
    InboundOrderRepository repository;

    @Autowired
    WarehouseService warehouseService;

    @Autowired
    SectionService sectionService;

    public InboundOrder createInboundOrder(InboundOrderRequestDto inboundOrderRequestDto) {
        Section section = sectionService.getSectionByCode(inboundOrderRequestDto.getInboundOrder().getSection().getSectionCode());
        Warehouse warehouse = warehouseService.getWarehouseByCode(inboundOrderRequestDto.getInboundOrder().getSection().getWarehouseCode());

        InboundOrder inboundOrder = ConvertToObject(inboundOrderRequestDto);

        //return repository.saveAndFlush(inboundOrder);
        return null;
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



    public static InboundOrder ConvertToObject(InboundOrderRequestDto inboundOrderRequestDto) {
        InboundOrderDto inboundOrder = inboundOrderRequestDto.getInboundOrder();
        SectionDto sectionDto = inboundOrder.getSection();
        //Warehouse warehouse = Ware
        //Section section = SectionService.ConvertToObject(inboundOrderRequestDto);

        InboundOrder object = InboundOrder.builder()
                .orderNumber(inboundOrder.getOrderNumber())
                .orderDate(inboundOrder.getOrderDate())
          //      .section(section)
                .build();
        return object;
    }


}
