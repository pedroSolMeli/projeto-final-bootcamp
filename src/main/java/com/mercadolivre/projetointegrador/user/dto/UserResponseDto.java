package com.mercadolivre.projetointegrador.user.dto;


import java.io.Serializable;

import javax.validation.constraints.Email;
import com.mercadolivre.projetointegrador.warehouse.controller.WarehouseController;
import org.hibernate.validator.constraints.br.CPF;
import com.mercadolivre.projetointegrador.enums.UserRole;
import com.mercadolivre.projetointegrador.user.model.User;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class UserResponseDto implements Serializable {

	private static final long serialVersionUID = -8019060760451294727L;

	private String cpf;

	private String name;

	private String email;

	private UserRole userRole;

	public static UserResponseDto ConvertToResponseDto(User user) {
		UserResponseDto userResponseDto = UserResponseDto.builder()
				.cpf(user.getCpf())
				.name(user.getName())
				.email(user.getEmail())
				.userRole(user.getUserRole())
				.build();
		return userResponseDto;
	}

	public static List<UserResponseDto> ConvertToResponseDto(List<User> userlist) {
		if (userlist == null)
			return new ArrayList<>();
		List<UserResponseDto> userResponseDtoList = userlist.stream()
				.map(s -> ConvertToResponseDto(s))
				.collect(Collectors.toList());
		return userResponseDtoList;
	}


	private String warehouseCode;

}
