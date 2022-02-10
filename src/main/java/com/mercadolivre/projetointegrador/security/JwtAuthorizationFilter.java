package com.mercadolivre.projetointegrador.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private static final String AUTH_HEADER = HttpHeaders.AUTHORIZATION;
    private static final String AUTH_PREFIX = "Bearer ";

    private final JwtProvider jwtProvider;
    private final ObjectMapper objectMapper;

    public JwtAuthorizationFilter(JwtProvider jwtProvider, ObjectMapper objectMapper) {
        this.jwtProvider = jwtProvider;
        this.objectMapper = objectMapper;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = resolveToken(httpServletRequest);
            SecurityContextHolder.clearContext();
            if (Objects.nonNull(token)) {
                Authentication auth = jwtProvider.getAuthentication(token);

                if (Objects.nonNull(auth)) {
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException ex) {

            ResponseStatusException responseStatusException = new ResponseStatusException(HttpStatus.FORBIDDEN, ex.getMessage());
            String error = objectMapper.writer().writeValueAsString(responseStatusException);

            httpServletResponse.getOutputStream().print(error);
        }
    }


    private String resolveToken(HttpServletRequest request) {
        String barerToken = request.getHeader(AUTH_HEADER);
        if (Objects.nonNull(barerToken) && barerToken.startsWith(AUTH_PREFIX)) {
            return barerToken.substring(AUTH_PREFIX.length());
        }
        return null;
    }

}
