package com.mercadolivre.projetointegrador.user.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;

import org.hibernate.validator.constraints.br.CPF;

import com.mercadolivre.projetointegrador.enums.UserRole;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRequestDto implements Serializable {

	private static final long serialVersionUID = -992475568031422702L;

	@CPF
	private String cpf;

	private String name;

	@Email
	private String email;

	private String password;

	private UserRole userRole;
	
	
	

}
