package com.mercadolivre.projetointegrador.section.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class SectionRequestDto {
    @NotBlank(message = "sectionCode must not be empty")
    private String sectionCode;

    @NotBlank(message = "sectionType must not be empty")
    private String sectionType;

    @NotNull(message = "maxCapacity must not be empty")
    @Min(value = 0, message = "maxCapacity must be higher than zero")
    private int maxCapacity;

    private String warehouseCode;
}
