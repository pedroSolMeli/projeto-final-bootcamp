package com.mercadolivre.projetointegrador.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderStatus {

	C("Cart");

    private final String status;
	
}
