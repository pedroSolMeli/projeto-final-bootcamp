package com.mercadolivre.projetointegrador.user.dto;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

import com.mercadolivre.projetointegrador.enums.UserRole;

import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

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


}
