package com.mercadolivre.projetointegrador.section.service;

import com.mercadolivre.projetointegrador.enums.ProductType;
import com.mercadolivre.projetointegrador.section.dto.SectionDto;
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
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SectionService {

    @Qualifier("SectionRepository")
    @Autowired
    SectionRepository sectionRepository;

    @Autowired
    WarehouseService warehouseService;

    public SectionResponseDto createSection(SectionRequestDto sectionRequestDto) {
        checkIfSectionCodeExists(sectionRequestDto.getSectionCode());
        Warehouse warehouse = warehouseService.getWarehouseByCode(sectionRequestDto.getWarehouseCode());
        Section section = ConvertToObject(sectionRequestDto, warehouse);
        Section result = sectionRepository.saveAndFlush(section);
        SectionResponseDto response = ConvertToResponseDto(result);
        return response;
    }

    public List<SectionResponseDto> findAllSections() {
        List<Section> result = sectionRepository.findAll();
        List<SectionResponseDto> response = ConvertToResponseDto(result);
        return response;
    }

    public Section getSectionBySectionCode(String code) {
        Section section = sectionRepository.getSectionByCode(code);
        if (section == null) {
            ResponseStatusException responseStatusException = new ResponseStatusException(HttpStatus.NOT_FOUND, "Section with code " + code + " not found");
            throw responseStatusException;
        }
        return section;
    }

    public Section getSectionBySectionCodeAndWarehouseCode(String sectionCode, String warehouseCode) {
        Section section = sectionRepository.getSectionByCodeAndWarehouse_Code(sectionCode, warehouseCode);
        if (section == null) {
            ResponseStatusException responseStatusException = new ResponseStatusException(HttpStatus.NOT_FOUND, "Section with sectionCode " + sectionCode + " and warehouseCode " + warehouseCode + " not found");
            throw responseStatusException;
        }
        return section;
    }

    public void checkIfSectionCodeExists(String code) {
        Optional<Section> section = sectionRepository.findSectionByCode(code);
        if (section.isPresent()) {
            ResponseStatusException responseStatusException = new ResponseStatusException(HttpStatus.CONFLICT, "Section code already exists");
            throw responseStatusException;
        }
    }

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

    public static Section ConvertToObject(SectionDto dto, Warehouse warehouse) {
        Section section = Section.builder()
                .code(dto.getSectionCode())
                .warehouse(warehouse)
                .build();
        return section;
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

    public static List<SectionResponseDto> ConvertToResponseDto(List<Section> sectionList) {
        if (sectionList == null)
            return new ArrayList<SectionResponseDto>();
        List<SectionResponseDto> SectionResponseDtoList = sectionList.stream().map(s -> ConvertToResponseDto(s)).collect(Collectors.toList());
        return SectionResponseDtoList;
    }


}
