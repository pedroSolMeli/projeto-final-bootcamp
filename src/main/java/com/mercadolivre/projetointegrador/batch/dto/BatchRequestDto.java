package com.mercadolivre.projetointegrador.batch.dto;

import javax.validation.constraints.NotNull;

public class BatchRequestDto {

    @NotNull(message = "productId must no be null")
    private Long productId;

    


}
