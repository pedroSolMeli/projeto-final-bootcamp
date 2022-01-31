package com.mercadolivre.projetointegrador.inboundorder.service;

import com.mercadolivre.projetointegrador.batch.dto.BatchResponseDto;
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

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InboundOrderService {

    @Qualifier("InboundOrderRepository")
    @Autowired
    InboundOrderRepository inboundOrderRepository;

    @Autowired
    WarehouseService warehouseService;

    @Autowired
    SectionService sectionService;

    @Autowired
    BatchService batchService;

    public InboundOrderResponseDto createInboundOrder(InboundOrderRequestDto inboundOrderRequestDto) {
        SectionDto sectionDto = inboundOrderRequestDto.getInboundOrder().getSection();
        Section section = sectionService.getSectionBySectionCodeAndWarehouseCode(sectionDto.getSectionCode(), sectionDto.getWarehouseCode());

        InboundOrder inboundOrder = ConvertToObject(inboundOrderRequestDto, section);
        InboundOrder inboundOrderPopulated = inboundOrderRepository.save(inboundOrder);
        List<Batch> batchListWithInboundOrder = batchService.populateBatchListWithInboundOrder(inboundOrderRequestDto, inboundOrderPopulated);
        inboundOrderPopulated.setBatchStock(batchListWithInboundOrder);
        InboundOrder result = inboundOrderRepository.saveAndFlush(inboundOrderPopulated);
        InboundOrderResponseDto response = ConvertToDto(result);

        return response;
    }

    public List<InboundOrderResponseDto> findAllInboundOrders() {
        List<InboundOrder> result = inboundOrderRepository.findAll();
        List<InboundOrderResponseDto> response = ConvertToDto(result);
        return response;
    }

    public InboundOrder updateInboundOrder(InboundOrder inboundOrder) {
        return inboundOrderRepository.saveAndFlush(inboundOrder);
    }

    public static List<InboundOrderResponseDto> ConvertToDto(List<InboundOrder> inboundOrderList) {
        List<InboundOrderResponseDto> result = inboundOrderList.stream().map(i -> ConvertToDto(i)).collect(Collectors.toList());
        return result;
    }

    public static InboundOrderResponseDto ConvertToDto(InboundOrder inboundOrder) {
        List<Batch> batchStock = inboundOrder.getBatchStock();
        List<BatchResponseDto> batchResponseDtoList = BatchService.ConvertToResponseDto(batchStock);
        InboundOrderResponseDto dto = InboundOrderResponseDto.builder().batchStock(batchResponseDtoList).build();
        return dto;
    }

    public static InboundOrder ConvertToObject(InboundOrderRequestDto inboundOrderRequestDto, Section section) {
        InboundOrderDto inboundOrder = inboundOrderRequestDto.getInboundOrder();
        InboundOrder object = InboundOrder.builder()
                .orderNumber(inboundOrder.getOrderNumber())
                .orderDate(inboundOrder.getOrderDate())
                .section(section)
                .build();
        return object;
    }


}
