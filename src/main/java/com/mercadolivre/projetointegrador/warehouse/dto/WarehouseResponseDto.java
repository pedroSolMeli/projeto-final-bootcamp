package com.mercadolivre.projetointegrador.warehouse.dto;

import com.mercadolivre.projetointegrador.section.dto.SectionResponseDto;
import com.mercadolivre.projetointegrador.section.model.Section;
import com.mercadolivre.projetointegrador.user.dto.UserResponseDto;
import com.mercadolivre.projetointegrador.user.model.User;
import com.mercadolivre.projetointegrador.warehouse.model.Warehouse;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@Data
public class WarehouseResponseDto {

    private Long id;
    private String code;
    private List<SectionResponseDto> sections;
    private List<UserResponseDto> users;

    public static WarehouseResponseDto ConvertToResponseDto(Warehouse warehouse, List<User> userList) {
        WarehouseResponseDto warehouseResponseDto = WarehouseResponseDto.builder()
                .id(warehouse.getId())
                .code(warehouse.getCode())
                .users(UserResponseDto.ConvertToResponseDto(userList))
                .build();
        return warehouseResponseDto;
    }

    public static List<WarehouseResponseDto> ConvertToResponseDto(List<Warehouse> warehouses) {
        if (warehouses == null)
            return new ArrayList<WarehouseResponseDto>();
        List<WarehouseResponseDto> WarehouseResponseDtoList = warehouses.stream().map(s -> ConvertToResponseDto(s, s.getUsers())).collect(Collectors.toList());
        return WarehouseResponseDtoList;
    }

}
