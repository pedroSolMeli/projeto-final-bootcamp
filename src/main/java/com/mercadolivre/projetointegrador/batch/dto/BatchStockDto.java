package com.mercadolivre.projetointegrador.batch.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BatchStockDto {

    private Long batchNumber;
    private Integer currentQuantity;
    private LocalDate dueDate;

}
