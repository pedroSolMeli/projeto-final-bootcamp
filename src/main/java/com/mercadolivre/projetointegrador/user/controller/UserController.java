package com.mercadolivre.projetointegrador.user.controller;

import java.util.List;

import javax.validation.Valid;

import com.mercadolivre.projetointegrador.warehouse.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.mercadolivre.projetointegrador.user.dto.UserRequestDto;
import com.mercadolivre.projetointegrador.user.dto.UserResponseDto;
import com.mercadolivre.projetointegrador.user.service.UserService;

@RestController("UserController")
@RequestMapping()
public class UserController {
	
	@Autowired
	UserService userService;

	@Autowired
	WarehouseService warehouseService;

	@PostMapping("/user")
	public ResponseEntity<?> create(@Valid @RequestBody UserRequestDto user) {
	   UserResponseDto result = userService.createUser(user);
	   return new ResponseEntity<>(result, HttpStatus.CREATED);
	}

	@GetMapping("/user")
	public ResponseEntity<?> findAll() {
		List<UserResponseDto> result = userService.findAllUsers();
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@GetMapping("/user/{id}")
	public ResponseEntity<?> findUser(@PathVariable Long id) {
		UserResponseDto result = userService.findUser(id);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@GetMapping("/user/cpf/{cpf}")
	public ResponseEntity<?> findUserByCpf(@PathVariable String cpf) {
		UserResponseDto result = userService.findUserByCpf(cpf);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@GetMapping("/user/role/{role}")
	public ResponseEntity<?> findUserByRole(@PathVariable String role) {
		List<UserResponseDto> result = userService.findUserByRole(role);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@DeleteMapping("/user/{id}")
	public ResponseEntity<?> deleteUserById(@PathVariable Long id) {
		userService.deleteUserById(id);
		return new ResponseEntity<>("Delete user id: "+ id, HttpStatus.OK);
	}

	@GetMapping("/user/warehouse/{code}")
	public ResponseEntity<?> getNumberOfUserByWarehouseCode(@PathVariable String code) {
		String result = warehouseService.getNumberOfUserByWarehouseCode(code);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

}
