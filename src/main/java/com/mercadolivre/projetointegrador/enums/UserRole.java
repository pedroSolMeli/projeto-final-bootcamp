package com.mercadolivre.projetointegrador.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserRole {
	B("Buyer"),
    S("Seller"),
    A("Agent");

    private final String description;

}
