package com.mercadolivre.projetointegrador.purchaseorder.dto;

import javax.validation.Valid;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseOrderRequestDto {
	
	@Valid
	private PurchaseOrderDto purchaseOrder;

}
