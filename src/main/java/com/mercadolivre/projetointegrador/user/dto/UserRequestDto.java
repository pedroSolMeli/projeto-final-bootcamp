package com.mercadolivre.projetointegrador.user.dto;

import com.mercadolivre.projetointegrador.enums.UserRole;
import com.mercadolivre.projetointegrador.user.model.User;
import com.mercadolivre.projetointegrador.warehouse.model.Warehouse;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;


@Getter
@Setter
@Builder
public class UserRequestDto implements Serializable {

    private static final long serialVersionUID = -992475568031422702L;

    @NotBlank(message = "cpf must not be empty")
    @CPF//(message = "cpf invalid")
    private String cpf;

    @NotBlank(message = "name must not be empty")
    private String name;

    @NotBlank(message = "username must not be empty")
    private String username;

    @NotBlank(message = "email must not be empty")
    @Email(message = "email invalid")
    private String email;

    @NotBlank(message = "password must not be empty")
    @Size(min = 5, message = "password must be at least 5 characters long")
    private String password;

    //@NotBlank(message = "userRole must not be empty")
    private List<String> roles;

    private String warehouseCode;

    public static User ConvertToObject(UserRequestDto dto, Warehouse warehouseByCode) {
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

        User user = User.builder()
                .cpf(dto.getCpf())
                .name(dto.getName())
                .username(dto.getUsername())
                .email(dto.getEmail())
                .password(encoder.encode(dto.getPassword()))
                .roles(dto.getRoles().stream().map(r -> Enum.valueOf(UserRole.class, r)).collect(Collectors.toList()))
                //.warehouse(warehouseByCode)
                .build();
        return user;
    }


}
