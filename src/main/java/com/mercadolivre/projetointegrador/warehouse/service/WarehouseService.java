package com.mercadolivre.projetointegrador.warehouse.service;

import com.mercadolivre.projetointegrador.batch.model.Batch;
import com.mercadolivre.projetointegrador.inboundorder.model.InboundOrder;
import com.mercadolivre.projetointegrador.section.model.Section;
import com.mercadolivre.projetointegrador.warehouse.dto.WarehouseRequestDto;
import com.mercadolivre.projetointegrador.warehouse.dto.WarehouseResponseDto;
import com.mercadolivre.projetointegrador.warehouse.dto.WarehousesByProductResponseDto;
import com.mercadolivre.projetointegrador.warehouse.dto.WarehousesDto;
import com.mercadolivre.projetointegrador.warehouse.model.Warehouse;
import com.mercadolivre.projetointegrador.warehouse.repository.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("WarehouseService")
public class WarehouseService {

    @Qualifier("WarehouseRepository")
    @Autowired
    WarehouseRepository repository;

    public WarehouseResponseDto createWarehouse(WarehouseRequestDto warehouseRequestDto) {
        checkIfWarehouseCodeExists(warehouseRequestDto.getCode());
        Warehouse warehouse = WarehouseRequestDto.ConvertToObject(warehouseRequestDto);
        Warehouse result = repository.saveAndFlush(warehouse);
        WarehouseResponseDto response = WarehouseResponseDto.ConvertToResponseDto(result);
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

    public WarehousesByProductResponseDto getWarehousesByProducts(Long productId){

        List<Warehouse> warehouses = findAllWarehouses();
        int quantity;

        List<WarehousesDto> warehousesDtoList = new ArrayList<>();
        for (Warehouse warehouse: warehouses) {
            quantity = 0;
            WarehousesDto warehousesDto = null;
            List<Section> sections = warehouse.getSections();
            for (Section section : sections){
                List<InboundOrder> inboundOrders = section.getInboundOrder();
                for (InboundOrder inbound: inboundOrders) {
                    List<Batch> batchStocks = inbound.getBatchStock();
                    for (Batch batch: batchStocks) {
                        Long id = batch.getProduct().getId();
                        if (productId == id){
                            quantity = quantity + batch.getCurrentQuantity();
                            warehousesDto = WarehousesDto.builder().warehouseCode(warehouse.getCode()).totalyQuantity(quantity).build();
                        }
                    }
                }
            }
            if (warehousesDto != null){
                warehousesDtoList.add(warehousesDto);
            }
        }

        if(warehousesDtoList.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found a valid product with id: " + productId);
        }
        return WarehousesByProductResponseDto.builder().productId(productId).warehouses(warehousesDtoList).build();

    }

}
