package com.mercadolivre.projetointegrador.security;

import com.mercadolivre.projetointegrador.user.dto.UserDto;
import com.mercadolivre.projetointegrador.user.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import java.util.*;

@Component
public class JwtProvider {

    private final String AUTHORITIES_KEY = "authorities";

    @Value("${security.jwt.secret}")
    private String secret;
    @Value("${security.jwt.expiration}")
    private Long expiration = 600000L;

    private SecretKey key;

    @PostConstruct
    public void init() {
        String base64Key = Base64.getEncoder().encodeToString(secret.getBytes());
        this.key = Keys.hmacShaKeyFor(base64Key.getBytes());
    }

    public String createToken(UserDto user, List<String> authorities) {
        Date issueTime = new Date(System.currentTimeMillis());
        Date expirationTIme = new Date(issueTime.getTime() + expiration);
        String authoritiesStr = String.join(",", authorities);

        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim(AUTHORITIES_KEY, authoritiesStr)
                .claim("userId", user.getId())
                .setIssuedAt(issueTime)
                .setExpiration(expirationTIme)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }


    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(this.key).build().parseClaimsJws(token).getBody();
        Object authoritiesClaim = claims.get(AUTHORITIES_KEY);
        Collection<? extends GrantedAuthority> authorities = AuthorityUtils.NO_AUTHORITIES;
        if (Objects.nonNull(authoritiesClaim)) {
            authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(authoritiesClaim.toString());
        }
        return new UsernamePasswordAuthenticationToken(claims.getSubject(), token, authorities);
    }


    public User getUser(String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        Claims claims = Jwts.parserBuilder().setSigningKey(this.key).build().parseClaimsJws(token).getBody();
        User user = new User();
        user.setId(claims.get("userId", Long.class));
        user.setUsername(claims.getSubject());
        return user;
    }

}
