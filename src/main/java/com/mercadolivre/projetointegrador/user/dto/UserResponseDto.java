package com.mercadolivre.projetointegrador.user.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mercadolivre.projetointegrador.enums.UserRole;
import com.mercadolivre.projetointegrador.user.model.User;
import com.mercadolivre.projetointegrador.warehouse.dto.WarehouseResponseDto;
import com.mercadolivre.projetointegrador.warehouse.dto.WarehousesDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
public class UserResponseDto implements Serializable {

    private static final long serialVersionUID = -8019060760451294727L;

    private String cpf;

    private String name;

    private String email;

    private List<String> roles;

    @JsonIgnore
    private String password;

    List<WarehouseResponseDto>  warehouse;

    public static List<UserResponseDto> ConvertToResponseDto(List<User> userlist) {
        if (userlist == null)
            return new ArrayList<>();
        List<UserResponseDto> userResponseDtoList = userlist.stream()
                .map(s -> ConvertToResponseDto(s))
                .collect(Collectors.toList());
        return userResponseDtoList;
    }

    public static UserResponseDto ConvertToResponseDto(User user) {
        UserResponseDto userResponseDto = UserResponseDto.builder()
                .cpf(user.getCpf())
                .name(user.getName())
                .email(user.getEmail())
                .roles(user.getRoles().stream().map(UserRole::name).collect(Collectors.toList()))
                .password(user.getPassword())
                .warehouse(WarehouseResponseDto.ConvertToResponseDto(user.getWarehouses()))
                .build();
        return userResponseDto;
    }

}
