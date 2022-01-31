package com.mercadolivre.projetointegrador.user.service;

import com.mercadolivre.projetointegrador.user.dto.UserRequestDto;
import com.mercadolivre.projetointegrador.user.dto.UserResponseDto;
import com.mercadolivre.projetointegrador.user.model.User;
import com.mercadolivre.projetointegrador.user.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	@Qualifier("UserRepository")
	@Autowired
	UserRepository userRepository;

	public UserResponseDto createUser(UserRequestDto user) {
		return ConvertToResponseDto(userRepository.saveAndFlush(ConvertToObject(user)));
	}

	public List<UserResponseDto> findAllUsers() {
		return ConvertToResponseDto(userRepository.findAll());
	}

	public UserResponseDto findUser(Long id) {
//		User user = userRepository.getUserById(id);
//		return ConvertToResponseDto(user);
		return ConvertToResponseDto(userRepository.getUserById(id));
	}


	public static User ConvertToObject(UserRequestDto dto){
		User user =  User.builder()
				.cpf(dto.getCpf())
				.name(dto.getName())
				.email(dto.getEmail())
				.password(dto.getPassword())
				.userRole(dto.getUserRole())
				.build();
		return user;
	}

	public static UserResponseDto ConvertToResponseDto(User user){
		UserResponseDto userResponseDto = UserResponseDto.builder()
				.cpf(user.getCpf())
				.name(user.getName())
				.email(user.getEmail())
				.userRole(user.getUserRole())
				.build();
		return userResponseDto;
	}

	private static List<UserResponseDto> ConvertToResponseDto(List<User> userlist) {
		if (userlist == null)
			return new ArrayList<>();
		List<UserResponseDto> userResponseDtoList = userlist.stream()
				.map(s -> ConvertToResponseDto(s))
				.collect(Collectors.toList());
		return userResponseDtoList;
	}



}
