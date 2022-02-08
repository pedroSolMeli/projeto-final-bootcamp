package com.mercadolivre.projetointegrador.batch.dto;


import com.mercadolivre.projetointegrador.batch.model.Batch;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BatchStockDto {

    private Long batchNumber;
    private Integer currentQuantity;
    private LocalDate dueDate;


    public static BatchStockDto ConvertToBatchStockDto(Batch batch) {
        BatchStockDto response = BatchStockDto.builder()
                .batchNumber(batch.getBatchNumber())
                .currentQuantity(batch.getCurrentQuantity())
                .currentQuantity(batch.getCurrentQuantity())
                .dueDate(batch.getDueDate())
                .build();
        return response;
    }

    public static List<BatchStockDto> ConvertToListBatchStockDto(List<Batch> batchList) {
        if (batchList == null)
            return new ArrayList<BatchStockDto>();
        List<BatchStockDto> productResponseDtoList = batchList.stream().map(s -> ConvertToBatchStockDto(s)).collect(Collectors.toList());
        return productResponseDtoList;
    }

}
