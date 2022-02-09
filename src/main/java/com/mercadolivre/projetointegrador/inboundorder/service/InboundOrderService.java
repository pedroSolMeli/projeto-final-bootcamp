package com.mercadolivre.projetointegrador.inboundorder.service;

import com.mercadolivre.projetointegrador.batch.dto.BatchRequestDto;
import com.mercadolivre.projetointegrador.batch.model.Batch;
import com.mercadolivre.projetointegrador.batch.service.BatchService;
import com.mercadolivre.projetointegrador.inboundorder.dto.InboundOrderDto;
import com.mercadolivre.projetointegrador.inboundorder.dto.InboundOrderRequestDto;
import com.mercadolivre.projetointegrador.inboundorder.dto.InboundOrderResponseDto;
import com.mercadolivre.projetointegrador.inboundorder.model.InboundOrder;
import com.mercadolivre.projetointegrador.inboundorder.repository.InboundOrderRepository;
import com.mercadolivre.projetointegrador.product.model.Product;
import com.mercadolivre.projetointegrador.product.service.ProductService;
import com.mercadolivre.projetointegrador.section.dto.SectionDto;
import com.mercadolivre.projetointegrador.section.model.Section;
import com.mercadolivre.projetointegrador.section.service.SectionService;
import com.mercadolivre.projetointegrador.warehouse.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
    ProductService productService;

    @Autowired
    BatchService batchService;

    public InboundOrderResponseDto createInboundOrder(InboundOrderRequestDto inboundOrderRequestDto) {

        InboundOrderDto inboundOrderDto = inboundOrderRequestDto.getInboundOrder();
        SectionDto sectionDto = inboundOrderDto.getSection();

        Section section = sectionService.getSectionBySectionCodeAndWarehouseCode(sectionDto.getSectionCode(), sectionDto.getWarehouseCode());

        checkIfOrderNumberExists(inboundOrderDto.getOrderNumber());
        checkIfBatchNumberExists(inboundOrderDto.getBatchStock());
        checkIfSectionHasEnoughSpace(inboundOrderDto.getBatchStock().size(), section);
        checkIfProductTypeMatchesSectionType(inboundOrderDto.getBatchStock(), section);

        InboundOrder inboundOrder = InboundOrderRequestDto.ConvertToObject(inboundOrderRequestDto, section);

        InboundOrder inboundOrderPopulated = inboundOrderRepository.save(inboundOrder);

        List<Batch> batchListWithInboundOrder = batchService.populateBatchListWithInboundOrder(inboundOrderRequestDto, inboundOrderPopulated);
        inboundOrderPopulated.setBatchStock(batchListWithInboundOrder);

        InboundOrder result = inboundOrderRepository.saveAndFlush(inboundOrderPopulated);
        InboundOrderResponseDto inboundOrderResponseDto = InboundOrderResponseDto.ConvertToDto(result);

        return inboundOrderResponseDto;
    }

    private void checkIfBatchNumberExists(List<BatchRequestDto> batchStock) {
        batchStock.stream().forEach(b -> {
            batchService.checkIfBatchNumberExists(b.getBatchNumber());
        });
    }

    public List<InboundOrderResponseDto> findAllInboundOrders() {
        List<InboundOrder> result = inboundOrderRepository.findAll();
        List<InboundOrderResponseDto> response = InboundOrderResponseDto.ConvertToDto(result);
        return response;
    }

    public InboundOrderResponseDto updateInboundOrder(InboundOrderRequestDto inboundOrderRequestDto) {
    	InboundOrderResponseDto result = createInboundOrder(inboundOrderRequestDto);
        return result;
    }

    public void checkIfOrderNumberExists(Long orderNumber) {
        InboundOrder inboundOrder = inboundOrderRepository.getInboundOrderByOrderNumber(orderNumber);
        if (inboundOrder != null) {
            ResponseStatusException responseStatusException = new ResponseStatusException(HttpStatus.CONFLICT, "orderNumber already exists");
            throw responseStatusException;
        }
    }

    private void checkIfProductTypeMatchesSectionType(List<BatchRequestDto> batchStock, Section section) {
        for (BatchRequestDto b : batchStock) {
            Product product = productService.getProductById(b.getProductId());
            if (!product.getProductType().equals(section.getSectionType())) {
                throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "product type does not match section type");
            }
        }
    }

    private void checkIfSectionHasEnoughSpace(Integer batchStockSize, Section section) {
        Integer totalBatchesInSection = countBatchesInSection(section);
        Integer availableSpace = section.getMaxCapacity() - totalBatchesInSection;
        if (batchStockSize > availableSpace) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "batchStock size is bigger than section available capacity");
        }
    }

    private Integer countBatchesInSection(Section section) {
        Integer totalBatch = 0;
        for (InboundOrder y : section.getInboundOrder()) {
            totalBatch += y.getBatchStock().size();
        }
        return totalBatch;
    }
}
