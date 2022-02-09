package com.mercadolivre.projetointegrador.batch.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatTypes;
import com.mercadolivre.projetointegrador.batch.model.Batch;
import com.mercadolivre.projetointegrador.product.model.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class BatchRequestDto implements Serializable {

	private static final long serialVersionUID = -2685868728841596914L;

	@NotNull(message = "productId must no be null")
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

//    @NotNull(message = "manufacturingDate must not be empty")
//    @JsonFormat( pattern = "yyyy-MM-dd HH:mm")
//    private LocalDateTime manufacturingTime;

    @NotNull(message = "manufacturingDate must not be empty")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dueDate;



    public static Batch ConvertToObject(BatchRequestDto dto, Product product) {
        Batch batch = Batch.builder()
                .batchNumber(dto.getBatchNumber())
                .product(product)
                .currentTemperature(dto.getCurrentTemperature())
                .minimalTemperature(dto.getMinimalTemperature())
                .initialQuantity(dto.getInitialQuantity())
                .currentQuantity(dto.getCurrentQuantity())
                .manufacturingDate(dto.getManufacturingDate())
//                .manufacturingTime(dto.getManufacturingTime())
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
//                .manufacturingTime(dto.getManufacturingTime())
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

}
