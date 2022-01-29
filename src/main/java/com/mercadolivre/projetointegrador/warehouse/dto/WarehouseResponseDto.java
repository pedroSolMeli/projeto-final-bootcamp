package com.mercadolivre.projetointegrador.warehouse.dto;

import com.mercadolivre.projetointegrador.section.dto.SectionResponseDto;
import lombok.Builder;

import java.util.ArrayList;
import java.util.List;

@Builder
public class WarehouseResponseDto {

    private Long id;
    private String code;
    private List<SectionResponseDto> sections;
}
