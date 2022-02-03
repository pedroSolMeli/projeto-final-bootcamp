package com.mercadolivre.projetointegrador.auth.controller;

import com.mercadolivre.projetointegrador.auth.dto.AuthDto;
import com.mercadolivre.projetointegrador.auth.dto.LoginDto;
import com.mercadolivre.projetointegrador.auth.service.AuthService;
import com.mercadolivre.projetointegrador.user.dto.UserRequestDto;
import com.mercadolivre.projetointegrador.user.dto.UserResponseDto;
import com.mercadolivre.projetointegrador.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController("AuthController")
@RequestMapping()
public class AuthController {

	@Autowired
	AuthService authService;

	@PostMapping("/auth")
	public ResponseEntity<?> authenticate(@RequestBody LoginDto loginDto) throws AuthenticationException {
		AuthDto authenticate = authService.authenticate(loginDto.getUsername(), loginDto.getPassword());
		return new ResponseEntity<>(authenticate, HttpStatus.OK);
	}

}
