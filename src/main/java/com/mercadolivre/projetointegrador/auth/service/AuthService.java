package com.mercadolivre.projetointegrador.auth.service;

import com.mercadolivre.projetointegrador.security.JwtAuthenticationManager;
import com.mercadolivre.projetointegrador.auth.dto.AuthDto;
import com.mercadolivre.projetointegrador.user.dto.UserDto;
import com.mercadolivre.projetointegrador.user.dto.UserResponseDto;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final JwtAuthenticationManager authenticationManager;

    public AuthService(JwtAuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    public AuthDto authenticate(String username, String password) throws AuthenticationException {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

        UserDto user = (UserDto) authentication.getPrincipal();
        String token = (String) authentication.getCredentials();

        return new AuthDto(user.getUsername(), token);
    }

}
