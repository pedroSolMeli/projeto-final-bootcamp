package com.mercadolivre.projetointegrador.user.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import com.mercadolivre.projetointegrador.user.dto.UserRequestDto;
import com.mercadolivre.projetointegrador.user.dto.UserResponseDto;
import com.mercadolivre.projetointegrador.user.service.UserService;

@RestController("UserController")
@RequestMapping()
public class UserController {
	
	@Autowired
	UserService service;

	@PostMapping("/user")
	public ResponseEntity<?> create(@Valid @RequestBody UserRequestDto user) {
	   UserResponseDto result = service.createUser(user);
	   return new ResponseEntity<>(result, HttpStatus.CREATED);
	}

	@GetMapping("/user")
	public ResponseEntity<?> findAll() {
		List<UserResponseDto> result = service.findAllUsers();
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@GetMapping("/user/{id}")
	public ResponseEntity<?> findUser(@PathVariable Long id) {
		UserResponseDto result = service.findUser(id);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}



}
