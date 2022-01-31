package com.mercadolivre.projetointegrador.product.dto;

import com.mercadolivre.projetointegrador.enums.ProductType;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ProductResponseDto {
    private Long id;
    private String name;
    private ProductType productType;
    private BigDecimal price;
}
