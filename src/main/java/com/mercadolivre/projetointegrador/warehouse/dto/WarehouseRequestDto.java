package com.mercadolivre.projetointegrador.warehouse.dto;

import com.mercadolivre.projetointegrador.user.model.User;
import com.mercadolivre.projetointegrador.warehouse.model.Warehouse;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
public class WarehouseRequestDto {

    @NotBlank(message = "code must not be empty")
    private String code;

    @NotNull(message = "code must not be empty")
    private List<Long> users;

    public static Warehouse ConvertToObject(WarehouseRequestDto dto, ArrayList<User> userList) {
        Warehouse warehouse = Warehouse.builder().code(dto.getCode()).users(userList).build();
        return warehouse;
    }



}
