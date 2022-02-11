package com.mercadolivre.projetointegrador.section.dto;

import com.mercadolivre.projetointegrador.section.model.Section;
import com.mercadolivre.projetointegrador.warehouse.model.Warehouse;
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

    public static Section ConvertToObject(SectionDto dto, Warehouse warehouse) {
        Section section = Section.builder()
                .code(dto.getSectionCode())
                .warehouse(warehouse)
                .build();
        return section;
    }

}
