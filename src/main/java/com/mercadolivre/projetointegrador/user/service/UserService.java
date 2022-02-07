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
import org.springframework.stereotype.Service;

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
        User user1 = userRepository.saveAndFlush(UserRequestDto.ConvertToObject(user, warehouseByCode));
        User userById = userRepository.getUserById(user1.getId());
        return UserResponseDto.ConvertToResponseDto(userById);
    }

    public List<UserResponseDto> findAllUsers() {
        return UserResponseDto.ConvertToResponseDto(userRepository.findAll());
    }

    public UserResponseDto findUser(Long id) {
        return UserResponseDto.ConvertToResponseDto(userRepository.getUserById(id));
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


}
