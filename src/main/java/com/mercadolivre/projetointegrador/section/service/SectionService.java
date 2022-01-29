package com.mercadolivre.projetointegrador.section.service;

import com.mercadolivre.projetointegrador.enums.ProductType;
import com.mercadolivre.projetointegrador.section.dto.SectionRequestDto;
import com.mercadolivre.projetointegrador.section.dto.SectionResponseDto;
import com.mercadolivre.projetointegrador.section.model.Section;
import com.mercadolivre.projetointegrador.section.repository.SectionRepository;
import com.mercadolivre.projetointegrador.warehouse.model.Warehouse;
import com.mercadolivre.projetointegrador.warehouse.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SectionService {

    @Qualifier("SectionRepository")
    @Autowired
    SectionRepository repository;

    @Autowired
    WarehouseService warehouseService;

    public SectionResponseDto createSection(SectionRequestDto sectionRequestDto) {
        checkIfSectionCodeExists(sectionRequestDto.getSectionCode());
        Warehouse warehouse = warehouseService.getWarehouseByCode(sectionRequestDto.getWarehouseCode());
        Section section = ConvertToObject(sectionRequestDto, warehouse);
        Section result = repository.saveAndFlush(section);
        SectionResponseDto response = ConvertToResponseDto(result);
        return response;
    }

    public List<SectionResponseDto> findAllSections() {
        List<Section> result = repository.findAll();
        List<SectionResponseDto> response = ConvertToResponseDto(result);
        return response;
    }

    public Section getSectionByCode(String code) {
        Section section = repository.getSectionByCode(code);
        if (section == null) {
            ResponseStatusException responseStatusException = new ResponseStatusException(HttpStatus.NOT_FOUND, "Section with code " + code + " not found");
            throw responseStatusException;
        }
        return section;
    }

    public void checkIfSectionCodeExists(String code) {
        Optional<Section> section = repository.findSectionByCode(code);
        if (section.isPresent()) {
            ResponseStatusException responseStatusException = new ResponseStatusException(HttpStatus.CONFLICT, "Section code already exists");
            throw responseStatusException;
        }
    }

    public static Section ConvertToObject(SectionRequestDto dto, Warehouse warehouse) {
        ProductType productType = ConvertToEnum(dto.getSectionType());
        Section section = Section.builder()
                .code(dto.getSectionCode())
                .sectionType(productType)
                .maxCapacity(dto.getMaxCapacity())
                .warehouseCode(warehouse)
                .build();
        return section;
    }

    public static SectionResponseDto ConvertToResponseDto(Section section) {
        SectionResponseDto sectionResponseDto = SectionResponseDto.builder()
                .id(section.getId())
                .sectionCode(section.getCode())
                .sectionType(section.getSectionType().getDescription())
                .maxCapacity(section.getMaxCapacity())
                .warehouseCode(section.getWarehouseCode().getCode())
                .build();
        return sectionResponseDto;
    }

    public static List<SectionResponseDto> ConvertToResponseDto(List<Section> sectionList) {
        if (sectionList == null)
            return new ArrayList<SectionResponseDto>();
        List<SectionResponseDto> SectionResponseDtoList = sectionList.stream().map(s -> ConvertToResponseDto(s)).collect(Collectors.toList());
        return SectionResponseDtoList;
    }

    public static ProductType ConvertToEnum(String enumDescription) {
        try {
            ProductType productType = ProductType.valueOf(enumDescription.toUpperCase(Locale.ROOT));
            return productType;
        } catch (Exception e) {
            ResponseStatusException responseStatusException = new ResponseStatusException(HttpStatus.NOT_FOUND, "ProductType with description" + enumDescription + " not found");
            throw responseStatusException;
        }
    }


}
