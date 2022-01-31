package com.mercadolivre.projetointegrador.batch.service;

import com.mercadolivre.projetointegrador.batch.dto.BatchRequestDto;
import com.mercadolivre.projetointegrador.batch.dto.BatchResponseDto;
import com.mercadolivre.projetointegrador.batch.model.Batch;
import com.mercadolivre.projetointegrador.batch.repository.BatchRepository;
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
import java.util.List;

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
        checkIfManufacturingDateAndTimeAreTheSame(batchRequestDto.getManufacturingDate(), batchRequestDto.getManufacturingTime());
        validateDueDate(batchRequestDto.getDueDate());
        Product product = productService.getProductById(batchRequestDto.getProductId());
        Batch batch = ConvertToObject(batchRequestDto, product);
        Batch result = batchRepository.saveAndFlush(batch);
        BatchResponseDto response = ConvertToResponseDto(result);
        return response;
    }

    public List<Batch> findAllBatch() {
        return batchRepository.findAll();
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
                //.productId(product)
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

    public static BatchResponseDto ConvertToResponseDto(Batch batch) {
        BatchResponseDto response = BatchResponseDto.builder()
                .id(batch.getId())
                .batchNumber(batch.getBatchNumber())
                //.productId(batch.getProductId().getId())
                .currentQuantity(batch.getCurrentQuantity())
                .minimalTemperature(batch.getMinimalTemperature())
                .initialQuantity(batch.getInitialQuantity())
                .currentQuantity(batch.getCurrentQuantity())
                .manufacturingDate(batch.getManufacturingDate())
                .manufacturingTime(batch.getManufacturingTime())
                .dueDate(batch.getDueDate())
                .build();
        return response;
    }

}
