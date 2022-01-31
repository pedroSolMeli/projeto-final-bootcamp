package com.mercadolivre.projetointegrador.inboundorder.service;

import com.mercadolivre.projetointegrador.batch.dto.BatchRequestDto;
import com.mercadolivre.projetointegrador.batch.model.Batch;
import com.mercadolivre.projetointegrador.batch.service.BatchService;
import com.mercadolivre.projetointegrador.inboundorder.dto.InboundOrderDto;
import com.mercadolivre.projetointegrador.inboundorder.dto.InboundOrderRequestDto;
import com.mercadolivre.projetointegrador.inboundorder.dto.InboundOrderResponseDto;
import com.mercadolivre.projetointegrador.inboundorder.model.InboundOrder;
import com.mercadolivre.projetointegrador.inboundorder.repository.InboundOrderRepository;
import com.mercadolivre.projetointegrador.section.dto.SectionDto;
import com.mercadolivre.projetointegrador.section.model.Section;
import com.mercadolivre.projetointegrador.section.service.SectionService;
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

    @Autowired
    BatchService batchService;

    public InboundOrder createInboundOrder(InboundOrderRequestDto inboundOrderRequestDto) {
        SectionDto sectionDto = inboundOrderRequestDto.getInboundOrder().getSection();
        Section section = sectionService.getSectionBySectionCodeAndWarehouseCode(sectionDto.getSectionCode(), sectionDto.getWarehouseCode());

        List<BatchRequestDto> batchStock = inboundOrderRequestDto.getInboundOrder().getBatchStock();
        List<Batch> batchList = BatchService.ConvertToObjectList(batchStock);
        List<Batch> batchListWithProducts = batchService.populateBatchWithProduct(batchStock, batchList);

        InboundOrder inboundOrder = ConvertToObject(inboundOrderRequestDto, section, batchListWithProducts);
        InboundOrder result = repository.saveAndFlush(inboundOrder);
        return result;
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

    public static InboundOrder ConvertToObject(InboundOrderRequestDto inboundOrderRequestDto, Section section, List<Batch> batchList) {
        InboundOrderDto inboundOrder = inboundOrderRequestDto.getInboundOrder();
        InboundOrder object = InboundOrder.builder()
                .orderNumber(inboundOrder.getOrderNumber())
                .orderDate(inboundOrder.getOrderDate())
                .section(section)
                .batchStock(batchList)
                .build();
        return object;
    }

}
