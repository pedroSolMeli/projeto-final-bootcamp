package com.mercadolivre.projetointegrador.batch.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BatchRequestDto implements Serializable {

    //@NotNull(message = "productId must no be null")
    private Long productId;

    @NotNull(message = "batchNumber must not be null")
    @Min(value = 0, message = "batchNumber cannot be lower than zero")
    private Long batchNumber;

    @NotNull(message = "currentTemperature must no be null")
    private Double currentTemperature;

    @NotNull(message = "currentTemperature must no be null")
    private Double minimalTemperature;

    @NotNull(message = "currentTemperature must no be null")
    @Min(value = 0, message = "initialQuantity cannot be lower than zero")
    private Integer initialQuantity;

    @NotNull(message = "currentTemperature must no be null")
    @Min(value = 0, message = "currentQuantity cannot be lower than zero")
    private Integer currentQuantity;

    @NotNull(message = "manufacturingDate must not be empty")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate manufacturingDate;

    @NotNull(message = "manufacturingDate must not be empty")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime manufacturingTime;

    @NotNull(message = "manufacturingDate must not be empty")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dueDate;

}
