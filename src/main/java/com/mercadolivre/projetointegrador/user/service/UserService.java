package com.mercadolivre.projetointegrador.user.service;

import com.mercadolivre.projetointegrador.enums.UserRole;
import com.mercadolivre.projetointegrador.user.dto.UserDto;
import com.mercadolivre.projetointegrador.user.dto.UserRequestDto;
import com.mercadolivre.projetointegrador.user.dto.UserResponseDto;
import com.mercadolivre.projetointegrador.user.model.User;
import com.mercadolivre.projetointegrador.user.repository.UserRepository;
import com.mercadolivre.projetointegrador.warehouse.model.Warehouse;
import com.mercadolivre.projetointegrador.warehouse.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

	@Qualifier("UserRepository")
	@Autowired
	UserRepository userRepository;

	@Qualifier("WarehouseService")
	@Autowired
	WarehouseService warehouseService;

	public UserResponseDto createUser(UserRequestDto user) {
		Warehouse warehouseByCode = warehouseService.getWarehouseByCode(user.getWarehouseCode());
		User user1 = userRepository.saveAndFlush(ConvertToObject(user, warehouseByCode));
		User userById = userRepository.getUserById(user1.getId());
		return ConvertToResponseDto(userById);
	}

	public List<UserResponseDto> findAllUsers() {
		return ConvertToResponseDto(userRepository.findAll());
	}

	public UserResponseDto findUser(Long id) {
		return ConvertToResponseDto(userRepository.getUserById(id));
	}

	public Optional<UserDto> findUserByUsername(String username) {
		User user = userRepository.findUserByUsername(username).orElse(null);

		if (Objects.isNull(user)) {
			return Optional.empty();
		}

		return Optional.of(UserDto.builder()
				.id(user.getId())
				.username(user.getUsername())
				.password(user.getPassword())
				.roles(user.getRoles().stream().map(UserRole::name).collect(Collectors.toList()))
				.build());
	}


	public static User ConvertToObject(UserRequestDto dto, Warehouse warehouseByCode){
		PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

		User user =  User.builder()
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

	public static UserResponseDto ConvertToResponseDto(User user){
		UserResponseDto userResponseDto = UserResponseDto.builder()
				.cpf(user.getCpf())
				.name(user.getName())
				.email(user.getEmail())
				.roles(user.getRoles().stream().map(UserRole::name).collect(Collectors.toList()))
//				.warehouseCode(user.getWarehouse().getCode())
				.password(user.getPassword())
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
