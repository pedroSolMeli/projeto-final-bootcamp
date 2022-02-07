package com.mercadolivre.projetointegrador.warehouse.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class WarehousesByProductResponseDto {

    private Long productId;
    List<WarehousesDto> warehouses;

}
