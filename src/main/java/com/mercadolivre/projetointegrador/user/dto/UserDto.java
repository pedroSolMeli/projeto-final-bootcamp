package com.mercadolivre.projetointegrador.user.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Builder
public class UserDto {

    private Long id;
    private String username;
    @JsonIgnore
    private String password;
    private List<String> roles;
    private String warehouseCode;

}
