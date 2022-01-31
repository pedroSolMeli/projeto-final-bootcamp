package com.mercadolivre.projetointegrador.batch.service;

import com.mercadolivre.projetointegrador.batch.dto.BatchRequestDto;
import com.mercadolivre.projetointegrador.batch.dto.BatchResponseDto;
import com.mercadolivre.projetointegrador.batch.model.Batch;
import com.mercadolivre.projetointegrador.batch.repository.BatchRepository;
import com.mercadolivre.projetointegrador.inboundorder.dto.InboundOrderRequestDto;
import com.mercadolivre.projetointegrador.inboundorder.model.InboundOrder;
import com.mercadolivre.projetointegrador.product.model.Product;
import com.mercadolivre.projetointegrador.product.repository.ProductRepository;
import com.mercadolivre.projetointegrador.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BatchService {

    @Autowired
    @Qualifier("BatchRepository")
    BatchRepository batchRepository;

    @Autowired
    @Qualifier("ProductRepository")
    ProductRepository productRepository;

    @Autowired
    ProductService productService;

    public BatchResponseDto createBatch(BatchRequestDto batchRequestDto) {
        checkIfBatchNumberExists(batchRequestDto.getBatchNumber());
        checkIfManufacturingDateAndTimeAreTheSame(batchRequestDto.getManufacturingDate(), batchRequestDto.getManufacturingTime());
        validateDueDate(batchRequestDto.getDueDate());
        Product product = productService.getProductById(batchRequestDto.getProductId());
        Batch batch = ConvertToObject(batchRequestDto, product);
        Batch result = batchRepository.saveAndFlush(batch);
        BatchResponseDto response = ConvertToResponseDto(result);
        return response;
    }

    public List<BatchResponseDto> findAllBatch() {
        List<Batch> result = batchRepository.findAll();
        List<BatchResponseDto> response = ConvertToResponseDto(result);
        return response;
    }

    public Batch updateBatch(Batch batch) {
        return batchRepository.saveAndFlush(batch);
    }

    public Batch getById(Long id) {
        Batch batch = batchRepository.findById(id).orElse(null);
        if (batch == null) {
            ResponseStatusException responseStatusException = new ResponseStatusException(HttpStatus.NOT_FOUND, "Batch not found");
            throw responseStatusException;
        }
        return batch;
    }

    private void checkIfBatchNumberExists(Long batchNumber) {
        Batch batch = batchRepository.getBatchByBatchNumber(batchNumber);
        if (batch != null) {
            ResponseStatusException responseStatusException = new ResponseStatusException(HttpStatus.CONFLICT, "batchNumber already exists");
            throw responseStatusException;
        }
    }

    private void checkIfManufacturingDateAndTimeAreTheSame(LocalDate manufacturingDate, LocalDateTime manufacturingTime) {
        boolean equalDates = manufacturingDate.equals(manufacturingTime.toLocalDate());
        if (!equalDates) {
            ResponseStatusException responseStatusException = new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "manufacturingDate and manufacturingTime are not the same");
            throw responseStatusException;
        }
        boolean validDate = manufacturingDate.isBefore(LocalDate.now());
        if (!validDate) {
            ResponseStatusException responseStatusException = new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "manufacturingDate cannot be a future date");
            throw responseStatusException;
        }
    }

    public void validateDueDate(LocalDate dueDate) {
        boolean validDate = dueDate.isAfter(LocalDate.now());
        if (!validDate) {
            ResponseStatusException responseStatusException = new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "dueDate cannot be a past date");
            throw responseStatusException;
        }
    }

    public static Batch ConvertToObject(BatchRequestDto dto, Product product) {
        Batch batch = Batch.builder()
                .batchNumber(dto.getBatchNumber())
                .product(product)
                .currentTemperature(dto.getCurrentTemperature())
                .minimalTemperature(dto.getMinimalTemperature())
                .initialQuantity(dto.getInitialQuantity())
                .currentQuantity(dto.getCurrentQuantity())
                .manufacturingDate(dto.getManufacturingDate())
                .manufacturingTime(dto.getManufacturingTime())
                .dueDate(dto.getDueDate())
                .build();
        return batch;
    }

    private static Batch ConvertToObject(BatchRequestDto dto) {
        Batch batch = Batch.builder()
                .batchNumber(dto.getBatchNumber())
                .currentTemperature(dto.getCurrentTemperature())
                .minimalTemperature(dto.getMinimalTemperature())
                .initialQuantity(dto.getInitialQuantity())
                .currentQuantity(dto.getCurrentQuantity())
                .manufacturingDate(dto.getManufacturingDate())
                .manufacturingTime(dto.getManufacturingTime())
                .dueDate(dto.getDueDate())
                .build();
        return batch;
    }

    public static List<Batch> ConvertToObjectList(List<BatchRequestDto> batchRequestDtoList) {
        if (batchRequestDtoList == null)
            return new ArrayList<Batch>();
        List<Batch> batchList = batchRequestDtoList.stream().map(s -> ConvertToObject(s)).collect(Collectors.toList());

        return batchList;
    }

    public static BatchResponseDto ConvertToResponseDto(Batch batch) {
        BatchResponseDto response = BatchResponseDto.builder()
                .id(batch.getId())
                .batchNumber(batch.getBatchNumber())
                .productId(batch.getProduct().getId())
                .currentQuantity(batch.getCurrentQuantity())
                .currentTemperature(batch.getCurrentTemperature())
                .minimalTemperature(batch.getMinimalTemperature())
                .initialQuantity(batch.getInitialQuantity())
                .currentQuantity(batch.getCurrentQuantity())
                .manufacturingDate(batch.getManufacturingDate())
                .manufacturingTime(batch.getManufacturingTime())
                .dueDate(batch.getDueDate())
                .build();
        return response;
    }

    public static List<BatchResponseDto> ConvertToResponseDto(List<Batch> batchList) {
        if (batchList == null)
            return new ArrayList<BatchResponseDto>();
        List<BatchResponseDto> batchResponseDtoList = batchList.stream().map(s -> ConvertToResponseDto(s)).collect(Collectors.toList());
        return batchResponseDtoList;
    }

    public List<Batch> populateBatchWithProduct(List<BatchRequestDto> dtoList, List<Batch> batchList) {
        for (int i = 0; i < dtoList.size(); i++) {
            Long productId = dtoList.get(i).getProductId();
            Product product = productService.getProductById(productId);
            batchList.get(i).setProduct(product);
        }
        return batchList;
    }

    public List<Batch> populateBatchListWithInboundOrder(InboundOrderRequestDto inboundOrderRequestDto, InboundOrder inbound) {
        List<BatchRequestDto> batchStock = inboundOrderRequestDto.getInboundOrder().getBatchStock();
        List<Batch> batchList = BatchService.ConvertToObjectList(batchStock);

        for (int i = 0; i < batchStock.size(); i++) {
            Product product = productService.getProductById(batchStock.get(i).getProductId());
            batchList.get(i).setInboundOrder(inbound);
            batchList.get(i).setProduct(product);
        }

        return batchList;
    }

}
