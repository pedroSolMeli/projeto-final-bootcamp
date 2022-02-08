package com.mercadolivre.projetointegrador.warehouse.dto;

import com.mercadolivre.projetointegrador.section.dto.SectionResponseDto;
import com.mercadolivre.projetointegrador.user.dto.UserResponseDto;
import com.mercadolivre.projetointegrador.user.model.User;
import com.mercadolivre.projetointegrador.warehouse.model.Warehouse;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Builder
@Data
public class WarehouseResponseDto {

    private Long id;
    private String code;
    private List<SectionResponseDto> sections;
    private List<UserResponseDto> users;

    public static WarehouseResponseDto ConvertToResponseDto(Warehouse warehouse, ArrayList<User> userList) {
        WarehouseResponseDto warehouseResponseDto = WarehouseResponseDto.builder()
                .id(warehouse.getId())
                .code(warehouse.getCode())
                .users(UserResponseDto.ConvertToResponseDto(userList))
                .build();
        return warehouseResponseDto;
    }
}
