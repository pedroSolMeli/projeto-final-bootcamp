package com.mercadolivre.projetointegrador.warehouse.dto;

import com.mercadolivre.projetointegrador.warehouse.model.Warehouse;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class WarehouseRequestDto {

    @NotBlank(message = "code must not be empty")
    private String code;

    public static Warehouse ConvertToObject(WarehouseRequestDto dto) {
        Warehouse warehouse = Warehouse.builder().code(dto.getCode()).build();
        return warehouse;
    }

}
