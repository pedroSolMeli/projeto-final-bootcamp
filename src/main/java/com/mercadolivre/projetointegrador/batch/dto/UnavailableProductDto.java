package com.mercadolivre.projetointegrador.batch.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UnavailableProductDto {
    //TODO RESPOSNE DE PRODUTO INDÍPONÍVEL
    private Long productId;
    private Integer availableQuantity;
    private String message;
}
