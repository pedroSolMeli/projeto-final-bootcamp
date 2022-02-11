package com.mercadolivre.projetointegrador.section.dto;

import com.mercadolivre.projetointegrador.section.model.Section;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@Data
public class SectionResponseDto {

    private Long id;
    private String sectionCode;
    private String sectionType;
    private Integer maxCapacity;
    private String warehouseCode;

    public static List<SectionResponseDto> ConvertToResponseDto(List<Section> sectionList) {
        if (sectionList == null)
            return new ArrayList<SectionResponseDto>();
        List<SectionResponseDto> SectionResponseDtoList = sectionList.stream().map(s -> ConvertToResponseDto(s)).collect(Collectors.toList());
        return SectionResponseDtoList;
    }

    public static SectionResponseDto ConvertToResponseDto(Section section) {
        SectionResponseDto sectionResponseDto = SectionResponseDto.builder()
                .id(section.getId())
                .sectionCode(section.getCode())
                .sectionType(section.getSectionType().getDescription())
                .maxCapacity(section.getMaxCapacity())
                .warehouseCode(section.getWarehouse().getCode())
                .build();
        return sectionResponseDto;
    }

}
