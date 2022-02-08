package com.mercadolivre.projetointegrador.inboundorder.service;

import com.mercadolivre.projetointegrador.batch.model.Batch;
import com.mercadolivre.projetointegrador.batch.service.BatchService;
import com.mercadolivre.projetointegrador.inboundorder.dto.InboundOrderRequestDto;
import com.mercadolivre.projetointegrador.inboundorder.dto.InboundOrderResponseDto;
import com.mercadolivre.projetointegrador.inboundorder.model.InboundOrder;
import com.mercadolivre.projetointegrador.inboundorder.repository.InboundOrderRepository;
import com.mercadolivre.projetointegrador.section.dto.SectionDto;
import com.mercadolivre.projetointegrador.section.model.Section;
import com.mercadolivre.projetointegrador.section.service.SectionService;
import com.mercadolivre.projetointegrador.warehouse.service.WarehouseService;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

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

        InboundOrderResponseDto response = null;
        try {
            SectionDto sectionDto = inboundOrderRequestDto.getInboundOrder().getSection();
            Section section = sectionService.getSectionBySectionCodeAndWarehouseCode(sectionDto.getSectionCode(), sectionDto.getWarehouseCode());

            InboundOrder inboundOrder = InboundOrderRequestDto.ConvertToObject(inboundOrderRequestDto, section);
            InboundOrder inboundOrderPopulated = inboundOrderRepository.save(inboundOrder);
            List<Batch> batchListWithInboundOrder = batchService.populateBatchListWithInboundOrder(inboundOrderRequestDto, inboundOrderPopulated);
            inboundOrderPopulated.setBatchStock(batchListWithInboundOrder);

            InboundOrder result = inboundOrderRepository.saveAndFlush(inboundOrderPopulated);
             response = InboundOrderResponseDto.ConvertToDto(result);

        }catch (DataIntegrityViolationException ex){
            ResponseStatusException responseStatusException = new ResponseStatusException(HttpStatus.BAD_REQUEST, "Please, validate the information entered");
            throw responseStatusException;
        }
        return response;

    }

    public List<InboundOrder> getInboundOrderBySectionWarehouseId(Long warehouseId){
       return inboundOrderRepository.getInboundOrderBySection_Warehouse_Id(warehouseId);
    }

    public List<InboundOrderResponseDto> findAllInboundOrders() {
        List<InboundOrder> result = inboundOrderRepository.findAll();
        List<InboundOrderResponseDto> response = InboundOrderResponseDto.ConvertToDto(result);
        return response;
    }

    public InboundOrder updateInboundOrder(InboundOrder inboundOrder) {
        return inboundOrderRepository.saveAndFlush(inboundOrder);
    }


}
