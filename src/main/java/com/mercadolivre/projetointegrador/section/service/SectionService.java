package com.mercadolivre.projetointegrador.section.service;

import com.mercadolivre.projetointegrador.section.dto.SectionRequestDto;
import com.mercadolivre.projetointegrador.section.dto.SectionResponseDto;
import com.mercadolivre.projetointegrador.section.model.Section;
import com.mercadolivre.projetointegrador.section.repository.SectionRepository;
import com.mercadolivre.projetointegrador.warehouse.model.Warehouse;
import com.mercadolivre.projetointegrador.warehouse.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SectionService {

    @Qualifier("SectionRepository")
    @Autowired
    SectionRepository repository;

    @Autowired
    WarehouseService warehouseService;

    public SectionResponseDto createSection(SectionRequestDto sectionRequestDto) {

        Warehouse warehouse = warehouseService.getWarehouseByCode(sectionRequestDto.getWarehouseCode());

        Section section = ConvertToObject(sectionRequestDto, warehouse);

        Section result = repository.saveAndFlush(section);

        SectionResponseDto response = ConvertToResponseDto(result);
        return response;
    }

    public List<Section> findAllSections() {
        return repository.findAll();
    }

    public static Section ConvertToObject(SectionRequestDto dto, Warehouse warehouse){
        Section section = Section.builder()
                .sectionCode(dto.getSectionCode())
                .sectionType(dto.getSectionType())
                .maxCapacity(dto.getMaxCapacity())
                .warehouseCode(warehouse)
                .build();
        return section;
    }


    public static SectionResponseDto ConvertToResponseDto(Section section) {
        SectionResponseDto sectionResponseDto = SectionResponseDto.builder()
                .sectionCode(section.getSectionCode())
                .sectionType(section.getSectionType().getDescription())
                .maxCapacity(section.getMaxCapacity())
                .warehouseCode(section.getWarehouseCode().getCode())
                .build();
        return sectionResponseDto;
    }

    public static List<SectionResponseDto> ConvertToResponseDto(List<Section> sectionList) {
        if (sectionList == null)
            return new ArrayList<SectionResponseDto>();

        return sectionList.stream().map(s -> ConvertToResponseDto(s)).collect(Collectors.toList());
    }


}
