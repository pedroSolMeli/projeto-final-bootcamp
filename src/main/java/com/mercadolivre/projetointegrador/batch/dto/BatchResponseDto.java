package com.mercadolivre.projetointegrador.batch.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
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
}
