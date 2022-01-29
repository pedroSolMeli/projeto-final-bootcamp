package com.mercadolivre.projetointegrador.section.dto;

import com.mercadolivre.projetointegrador.enums.ProductType;
import lombok.Builder;

@Builder
public class SectionResponseDto {

    private String sectionCode;
    private String sectionType;
    private Integer maxCapacity;
    private String warehouseCode;

}
