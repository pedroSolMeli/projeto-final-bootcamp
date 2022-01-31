package com.mercadolivre.projetointegrador.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Locale;

@Getter
@AllArgsConstructor
public enum ProductType {
    FRESH("FR", "Fresh"),
    REFRIGERATED("RF", "Refrigerated"),
    FROZEN("FZ", "Frozen");

    private final String code;
    private final String description;

    public static ProductType ConvertToEnum(String enumDescription) {
        try {
            ProductType productType = ProductType.valueOf(enumDescription.toUpperCase(Locale.ROOT));
            return productType;
        } catch (Exception e) {
            ResponseStatusException responseStatusException = new ResponseStatusException(HttpStatus.NOT_FOUND, "ProductType with description" + enumDescription + " not found");
            throw responseStatusException;
        }
    }
}
