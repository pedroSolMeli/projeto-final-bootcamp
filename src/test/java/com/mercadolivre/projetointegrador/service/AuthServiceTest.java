package com.mercadolivre.projetointegrador.service;

import com.mercadolivre.projetointegrador.auth.dto.AuthDto;
import com.mercadolivre.projetointegrador.auth.service.AuthService;
import com.mercadolivre.projetointegrador.security.JwtAuthenticationManager;
import com.mercadolivre.projetointegrador.user.dto.UserDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
public class AuthServiceTest {

    @InjectMocks
    private AuthService authService;

    @Mock
    private JwtAuthenticationManager authenticationManager;

    @Test
    void authFail() {
        String username = "user";
        String password = "fail";

        when(authenticationManager.authenticate(Mockito.any())).thenThrow(new BadCredentialsException("Bad credentials"));

        assertThrows(BadCredentialsException.class, () -> authService.authenticate(username, password));
    }

    @Test
    void authSuccess() {
        String username = "username";
        String password = "password";

        UserDto userDTO = UserDto.builder()
                .username(username)
                .password(password)
                .build();
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDTO, null, null);
        when(authenticationManager.authenticate(Mockito.any())).thenReturn(authentication);

        AuthDto authResDTO = authService.authenticate(username, password);

        Assertions.assertEquals(username, authResDTO.getUsername());
        assertNull(authResDTO.getToken());
    }

}
