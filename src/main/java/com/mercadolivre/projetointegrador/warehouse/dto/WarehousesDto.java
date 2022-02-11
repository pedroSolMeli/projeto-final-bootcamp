package com.mercadolivre.projetointegrador.warehouse.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class WarehousesDto {

    private String warehouseCode;
    private  int totalyQuantity;

}
