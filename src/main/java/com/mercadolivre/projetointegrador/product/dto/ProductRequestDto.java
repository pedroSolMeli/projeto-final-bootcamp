package com.mercadolivre.projetointegrador.product.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Builder
public class ProductRequestDto {

    @NotBlank(message = "message must not be empty")
    private String name;

    @NotBlank(message = "productType must not be empty")
    private String productType;

    @NotNull(message = "price must not be null")
    @Min(value = 0, message = "price must not be lower than zero")
    private BigDecimal price;
}
