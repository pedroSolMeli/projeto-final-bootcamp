package com.mercadolivre.projetointegrador.section.dto;

import com.mercadolivre.projetointegrador.enums.ProductType;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
public class SectionRequestDto {
    @NotBlank(message = "sectionCode must not be empty")
    private String sectionCode;

    @NotBlank(message = "sectionType must not be empty")
    private ProductType sectionType;

    @NotBlank(message = "maxCapacity must not be empty")
    @Min(value = 0, message = "maxCapacity must be higher than zero")
    private Integer maxCapacity;

    private String warehouseCode;
}
