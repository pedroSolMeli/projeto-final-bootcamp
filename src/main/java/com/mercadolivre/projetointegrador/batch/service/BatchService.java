package com.mercadolivre.projetointegrador.batch.service;

import com.mercadolivre.projetointegrador.batch.dto.BatchRequestDto;
import com.mercadolivre.projetointegrador.batch.dto.BatchResponseDto;
import com.mercadolivre.projetointegrador.batch.model.Batch;
import com.mercadolivre.projetointegrador.batch.repository.BatchRepository;
import com.mercadolivre.projetointegrador.enums.ProductType;
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
        Batch batch = BatchRequestDto.ConvertToObject(batchRequestDto, product);
        Batch result = batchRepository.saveAndFlush(batch);
        BatchResponseDto response = BatchResponseDto.ConvertToResponseDto(result);
        return response;
    }

    public List<BatchResponseDto> findAllBatch() {
        List<Batch> result = batchRepository.findAll();
        List<BatchResponseDto> response = BatchResponseDto.ConvertToResponseDto(result);
        return response;
    }

    public List<BatchResponseDto> findAllBatchBySectionAndDateLimit(Long sectionId, int numberOfDays, ProductType category) {

        List<Batch> resultByDateLimit = new ArrayList<>();

        if(sectionId != null && category != null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Filtering by sectionId and category together is not allowed");
        }

        if(sectionId != null){
            List<Batch> result = batchRepository.getBatchsByinboundOrder_Section_Id(sectionId);
            for (Batch batch : result) {
                if (productService.isValidDate(batch.getDueDate())){
                    if(!productService.isDueDateValid(batch.getDueDate(), numberOfDays)){
                        resultByDateLimit.add(batch);
                    }
                }
            }
        }

        if(category != null){
            List<Batch> result = batchRepository.getBatchesByProduct_ProductType(category);
            for (Batch batch : result) {
                if (productService.isValidDate(batch.getDueDate())){
                    if(!productService.isDueDateValid(batch.getDueDate(), numberOfDays)){
                        resultByDateLimit.add(batch);
                    }
                }
            }
        }

        //Todo - Ajustar dto de retorno
        List<BatchResponseDto> response = BatchResponseDto.ConvertToResponseDto(resultByDateLimit);
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
        List<Batch> batchList = BatchRequestDto.ConvertToObjectList(batchStock);

        for (int i = 0; i < batchStock.size(); i++) {
            Product product = productService.getProductById(batchStock.get(i).getProductId());
            batchList.get(i).setInboundOrder(inbound);
            batchList.get(i).setProduct(product);
        }

        return batchList;
    }

}
