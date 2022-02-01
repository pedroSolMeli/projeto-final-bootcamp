package com.mercadolivre.projetointegrador.warehouse.dto;

import com.mercadolivre.projetointegrador.section.dto.SectionResponseDto;
import com.mercadolivre.projetointegrador.warehouse.model.Warehouse;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class WarehouseResponseDto {

    private Long id;
    private String code;
    private List<SectionResponseDto> sections;

    public static WarehouseResponseDto ConvertToResponseDto(Warehouse warehouse) {
        //List<SectionResponseDto> sectionResponseDtos = SectionService.ConvertToResponseDto(warehouse.getSections());
        WarehouseResponseDto warehouseResponseDto = WarehouseResponseDto.builder()
                .id(warehouse.getId())
                .code(warehouse.getCode())
                // .sections(sectionResponseDtos)
                .build();
        return warehouseResponseDto;
    }
}
