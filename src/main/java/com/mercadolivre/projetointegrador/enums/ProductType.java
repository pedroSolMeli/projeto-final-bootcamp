package com.mercadolivre.projetointegrador.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProductType {
    FRESH("FR", "Fresh"),
    REFRIGERATED("RF", "Refrigerated"),
    FROZEN("FZ", "Frozen");

    private final String code;
    private final String description;
}
