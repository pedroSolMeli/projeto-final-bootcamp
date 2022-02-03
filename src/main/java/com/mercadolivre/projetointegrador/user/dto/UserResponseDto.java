package com.mercadolivre.projetointegrador.user.dto;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.Email;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mercadolivre.projetointegrador.warehouse.controller.WarehouseController;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

import com.mercadolivre.projetointegrador.enums.UserRole;

import lombok.Builder;
import lombok.Data;

@Getter
@Setter
@Builder
public class UserResponseDto implements Serializable{

	private static final long serialVersionUID = -8019060760451294727L;

	private String cpf;

	private String name;

	private String email;

	private List<String> roles;

	@JsonIgnore
	private String password;

}
