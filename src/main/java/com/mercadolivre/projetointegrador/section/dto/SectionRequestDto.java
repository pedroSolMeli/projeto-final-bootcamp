package com.mercadolivre.projetointegrador.section.dto;

import com.mercadolivre.projetointegrador.enums.ProductType;
import com.mercadolivre.projetointegrador.section.model.Section;
import com.mercadolivre.projetointegrador.warehouse.model.Warehouse;
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

    @NotBlank(message = "wareHouseCode must not be empty")
    private String warehouseCode;

    public static Section ConvertToObject(SectionRequestDto dto, Warehouse warehouse) {
        ProductType productType = ProductType.ConvertToEnum(dto.getSectionType());
        Section section = Section.builder()
                .code(dto.getSectionCode())
                .sectionType(productType)
                .maxCapacity(dto.getMaxCapacity())
                .warehouse(warehouse)
                .build();
        return section;
    }

}
