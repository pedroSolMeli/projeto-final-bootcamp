package com.mercadolivre.projetointegrador.batch.dto;

import com.mercadolivre.projetointegrador.batch.model.Batch;
import com.mercadolivre.projetointegrador.enums.ProductType;
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
public class BatchResponseDateLimitDto {

    private Long batchNumber;
    private Long productId;
    private ProductType productType;
    private Integer currentQuantity;
    private LocalDate dueDate;


    public static BatchResponseDateLimitDto ConvertToBatchResponseDateLimitDto(Batch batch) {
        BatchResponseDateLimitDto response = BatchResponseDateLimitDto.builder()
                .batchNumber(batch.getBatchNumber())
                .productId(batch.getProduct().getId())
                .productType(batch.getProduct().getProductType())
                .currentQuantity(batch.getCurrentQuantity())
                .currentQuantity(batch.getCurrentQuantity())
                .dueDate(batch.getDueDate())
                .build();
        return response;
    }

    public static List<BatchResponseDateLimitDto> ConvertToBatchResponseDateLimitDto(List<Batch> batchList) {
        if (batchList == null)
            return new ArrayList<BatchResponseDateLimitDto>();
        List<BatchResponseDateLimitDto> productResponseDtoList = batchList.stream().map(s -> ConvertToBatchResponseDateLimitDto(s)).collect(Collectors.toList());
        return productResponseDtoList;
    }

}
