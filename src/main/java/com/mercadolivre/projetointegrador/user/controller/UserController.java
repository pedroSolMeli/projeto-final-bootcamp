package com.mercadolivre.projetointegrador.user.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mercadolivre.projetointegrador.section.dto.SectionRequestDto;
import com.mercadolivre.projetointegrador.section.dto.SectionResponseDto;
import com.mercadolivre.projetointegrador.user.dto.UserRequestDto;
import com.mercadolivre.projetointegrador.user.dto.UserResponseDto;
import com.mercadolivre.projetointegrador.user.model.User;
import com.mercadolivre.projetointegrador.user.service.UserService;

@RestController("UserController")
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	UserService service;
	
	
	 	@PostMapping()
	    public ResponseEntity<?> create(@Valid @RequestBody User user) {
	       User result = service.createUser(user);
	        return new ResponseEntity<>(result, HttpStatus.CREATED);
	    }

	    @GetMapping()
	    public ResponseEntity<?> findAll() {
	        List<User> result = service.findAllUsers();
	        return new ResponseEntity<>(result, HttpStatus.OK);
	    }
	    
	    @GetMapping("/{id}")
	    public ResponseEntity<?> findUser(@PathVariable Long id) {
	        User result = service.findUser(id);
	        return new ResponseEntity<>(result, HttpStatus.OK);
	    }

	
	
	
	
	
	

}
