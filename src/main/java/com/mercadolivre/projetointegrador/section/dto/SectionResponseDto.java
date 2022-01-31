package com.mercadolivre.projetointegrador.section.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SectionResponseDto {

    private Long id;
    private String sectionCode;
    private String sectionType;
    private Integer maxCapacity;
    private String warehouseCode;

}
