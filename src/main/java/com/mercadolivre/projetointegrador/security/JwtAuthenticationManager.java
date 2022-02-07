package com.mercadolivre.projetointegrador.security;

import com.mercadolivre.projetointegrador.user.dto.UserDto;
import com.mercadolivre.projetointegrador.user.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtAuthenticationManager implements AuthenticationManager {

    private final UserService userService;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    public JwtAuthenticationManager(UserService userService, JwtProvider jwtProvider, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.jwtProvider = jwtProvider;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();

        UserDto user = userService.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("The user '" + username + "' cannot be found!"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("Bad credentials for user '" + username + "'");
        }

        List<String> authorities = user.getRoles().stream()
                .map(role -> "ROLE_" + role)
                .collect(Collectors.toList());
        String credentials = jwtProvider.createToken(user, authorities);

        return new UsernamePasswordAuthenticationToken(user, credentials, authentication.getAuthorities());
    }

}
