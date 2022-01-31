package com.mercadolivre.projetointegrador.section.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Builder
public class SectionDto {

    @NotBlank(message = "sectionCode must no be empty")
    private String sectionCode;

    @NotBlank(message = "sectionCode must no be empty")
    private String warehouseCode;

}
