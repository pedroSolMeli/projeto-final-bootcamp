package com.mercadolivre.projetointegrador.purchaseorder.dto;

import com.mercadolivre.projetointegrador.batch.dto.UnavailableProductDto;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PurschaseOrderResponseDto {
    private BigDecimal price;
    private List<UnavailableProductDto> unavaibleProducts;
}
