package com.mercadolivre.projetointegrador.batch.dto;

import com.mercadolivre.projetointegrador.batch.model.Batch;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
public class BatchResponseDto {
    private Long id;
    private Long batchNumber;
    //private Long inboundOrder;
    private Long productId;
    private Double currentTemperature;
    private Double minimalTemperature;
    private Integer initialQuantity;
    private Integer currentQuantity;
    private LocalDate manufacturingDate;
    private LocalDateTime manufacturingTime;
    private LocalDate dueDate;


    public static List<BatchResponseDto> ConvertToResponseDto(List<Batch> batchList) {
        if (batchList == null)
            return new ArrayList<BatchResponseDto>();
        List<BatchResponseDto> batchResponseDtoList = batchList.stream().map(s -> ConvertToResponseDto(s)).collect(Collectors.toList());
        return batchResponseDtoList;
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

}

