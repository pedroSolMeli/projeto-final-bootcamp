package com.mercadolivre.projetointegrador.batch.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UnavailableProductDto {
    private Long productId;
    private Integer availableQuantity;
    private String message;
}
