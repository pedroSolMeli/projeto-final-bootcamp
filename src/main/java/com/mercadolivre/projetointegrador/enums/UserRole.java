package com.mercadolivre.projetointegrador.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserRole {
	B("Builder"),
    S("Seller"),
    L("Agent");

    private final String description;

}
