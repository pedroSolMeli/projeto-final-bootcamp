package com.mercadolivre.projetointegrador.warehouse.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class WarehouseRequestDto {

    @NotBlank(message = "code must not be empty")
    private String code;
}
