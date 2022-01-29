package com.mercadolivre.projetointegrador.warehouse.service;

import com.mercadolivre.projetointegrador.section.dto.SectionResponseDto;
import com.mercadolivre.projetointegrador.section.service.SectionService;
import com.mercadolivre.projetointegrador.warehouse.dto.WarehouseRequestDto;
import com.mercadolivre.projetointegrador.warehouse.dto.WarehouseResponseDto;
import com.mercadolivre.projetointegrador.warehouse.model.Warehouse;
import com.mercadolivre.projetointegrador.warehouse.repository.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class WarehouseService {

    @Qualifier("WarehouseRepository")
    @Autowired
    WarehouseRepository repository;


    public Warehouse createWarehouse(WarehouseRequestDto warehouseRequestDto) {
        Warehouse warehouse = ConvertToObject(warehouseRequestDto);

        Warehouse result = repository.saveAndFlush(warehouse);

        WarehouseResponseDto response = ConvertToResponseDto(result);
        return result;

    }

    public List<Warehouse> findAll() {
        return repository.findAll();
    }

    public static Warehouse ConvertToObject(WarehouseRequestDto dto) {
        Warehouse warehouse = Warehouse.builder()
                .code(dto.getCode())
                .build();
        return warehouse;
    }

    public static WarehouseResponseDto ConvertToResponseDto(Warehouse warehouse) {
        List<SectionResponseDto> sectionResponseDtos = SectionService.ConvertToResponseDto(warehouse.getSections());
        WarehouseResponseDto warehouseResponseDto = WarehouseResponseDto.builder()
                .id(warehouse.getId())
                .code(warehouse.getCode())
                .sections(sectionResponseDtos)
                .build();
        return warehouseResponseDto;
    }

    public Warehouse getWarehouseById(Long id) {
        Warehouse warehouse = repository.findById(id).orElse(null);
        if (warehouse == null) {
            ResponseStatusException responseStatusException = new ResponseStatusException(HttpStatus.NOT_FOUND, "Warehouse not found");
            throw responseStatusException;
        }
        return warehouse;
    }

    public Warehouse getWarehouseByCode(String code) {

        Warehouse warehouse = repository.findWarehouseByCode(code);
        if (warehouse == null) {
            ResponseStatusException responseStatusException = new ResponseStatusException(HttpStatus.NOT_FOUND, "Warehouse not found");
            throw responseStatusException;
        }
        return warehouse;
    }

}
