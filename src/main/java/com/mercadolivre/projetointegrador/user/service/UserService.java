package com.mercadolivre.projetointegrador.user.service;

import com.mercadolivre.projetointegrador.user.dto.UserRequestDto;
import com.mercadolivre.projetointegrador.user.dto.UserResponseDto;
import com.mercadolivre.projetointegrador.user.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.mercadolivre.projetointegrador.warehouse.model.Warehouse;
import com.mercadolivre.projetointegrador.warehouse.service.WarehouseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

	@Qualifier("UserRepository")
	@Autowired
	UserRepository userRepository;


	public UserResponseDto createUser(UserRequestDto user) {
		return UserResponseDto.ConvertToResponseDto(userRepository.saveAndFlush(UserRequestDto.ConvertToObject(user)));
	}

	public List<UserResponseDto> findAllUsers() {
		return UserResponseDto.ConvertToResponseDto(userRepository.findAll());
	}

	public UserResponseDto findUser(Long id) {
		return UserResponseDto.ConvertToResponseDto(userRepository.getUserById(id));
	}

}