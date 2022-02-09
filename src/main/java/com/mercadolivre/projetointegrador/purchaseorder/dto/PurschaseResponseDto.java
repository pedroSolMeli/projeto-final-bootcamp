package com.mercadolivre.projetointegrador.purchaseorder.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PurschaseResponseDto {

    @Valid
    private PurchaseOrderDto purchaseOrder;

}
