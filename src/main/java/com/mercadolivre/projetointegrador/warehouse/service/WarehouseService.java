package com.mercadolivre.projetointegrador.warehouse.service;

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
import java.util.Optional;

@Service
public class WarehouseService {

    @Qualifier("WarehouseRepository")
    @Autowired
    WarehouseRepository repository;

    public WarehouseResponseDto createWarehouse(WarehouseRequestDto warehouseRequestDto) {
        checkIfWarehouseCodeExists(warehouseRequestDto.getCode());
        Warehouse warehouse = ConvertToObject(warehouseRequestDto);
        Warehouse result = repository.saveAndFlush(warehouse);
        WarehouseResponseDto response = ConvertToResponseDto(result);
        return response;
    }

    public List<Warehouse> findAllWarehouses() {
        return repository.findAll();
    }

    public Warehouse getWarehouseByCode(String code) {
        Warehouse warehouse = repository.getWarehouseByCode(code);
        if (warehouse == null) {
            ResponseStatusException responseStatusException = new ResponseStatusException(HttpStatus.NOT_FOUND, "Warehouse not found");
            throw responseStatusException;
        }
        return warehouse;
    }

    public Warehouse getWarehouseById(Long id) {
        Warehouse warehouse = repository.findById(id).orElse(null);
        if (warehouse == null) {
            ResponseStatusException responseStatusException = new ResponseStatusException(HttpStatus.NOT_FOUND, "Warehouse not found");
            throw responseStatusException;
        }
        return warehouse;
    }

    public void checkIfWarehouseCodeExists(String code) {
        Optional<Warehouse> warehouse = repository.findWarehouseByCode(code);
        if (warehouse.isPresent()) {
            ResponseStatusException responseStatusException = new ResponseStatusException(HttpStatus.CONFLICT, "Warehouse code already exists");
            throw responseStatusException;
        }
    }

    public static Warehouse ConvertToObject(WarehouseRequestDto dto) {
        Warehouse warehouse = Warehouse.builder()
                .code(dto.getCode())
                .build();
        return warehouse;
    }

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
