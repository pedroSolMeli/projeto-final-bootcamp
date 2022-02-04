package com.mercadolivre.projetointegrador.section.service;

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

import java.util.List;
import java.util.Optional;

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
        Section section = SectionRequestDto.ConvertToObject(sectionRequestDto, warehouse);
        Section result = sectionRepository.saveAndFlush(section);
        SectionResponseDto response = SectionResponseDto.ConvertToResponseDto(result);
        return response;
    }

    public List<SectionResponseDto> findAllSections() {
        List<Section> result = sectionRepository.findAll();
        List<SectionResponseDto> response = SectionResponseDto.ConvertToResponseDto(result);
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

}
