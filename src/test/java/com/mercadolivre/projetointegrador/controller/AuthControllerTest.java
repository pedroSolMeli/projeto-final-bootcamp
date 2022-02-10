package com.mercadolivre.projetointegrador.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolivre.projetointegrador.auth.dto.AuthDto;
import com.mercadolivre.projetointegrador.security.JwtProvider;
import com.mercadolivre.projetointegrador.user.dto.UserDto;
import com.mercadolivre.projetointegrador.user.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AuthControllerTest{

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @Autowired
     JwtProvider jwtProvider;

    @Test
    void authFailUsernameNotFoundException() throws Exception {
        String payload = "{\"username\":\"buyerNotExist\",\"password\":\"success\"}";

       this.mockMvc.perform(MockMvcRequestBuilders.post( "/auth")
               .contentType(MediaType.APPLICATION_JSON)
               .content(payload))
               .andDo(print())
               .andExpect(status().isForbidden())
               .andReturn();
    }

    @Test
    void authFailInvalidPassword() throws Exception {
        String payload = "{\"username\":\"buyer1\",\"password\":\"successErr\"}";

        this.mockMvc.perform(MockMvcRequestBuilders.post("/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload))
                .andDo(print())
                .andExpect(status().isForbidden())
                .andReturn();
    }

    @Test
    void authSuccess() throws Exception {
        String payload = "{\"username\":\"buyer1\",\"password\":\"success\"}";
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post("/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        AuthDto authResDTO = mapper.readValue(mvcResult.getResponse().getContentAsString(), AuthDto.class);
        Authentication authentication = jwtProvider.getAuthentication(authResDTO.getToken());

        Assertions.assertEquals("buyer1", authentication.getPrincipal());
        Assertions.assertNotNull(authentication.getCredentials());
    }

    @Test
    void endpointWithInvalidRole() throws Exception {
        UserDto user = UserDto.builder().username("test").roles(List.of("ROLE_B")).build();
        String token = jwtProvider.createToken(user, user.getRoles());

        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get( "/section")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token))
                .andDo(print())
                .andReturn();

        assertEquals(HttpStatus.FORBIDDEN.value(), mvcResult.getResponse().getStatus());
    }

    @Test
    void endpointWithValidRole() throws Exception {
        UserDto user = UserDto.builder().username("admin").roles(List.of("ROLE_A")).build();
        String token = jwtProvider.createToken(user, user.getRoles());

        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/section")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token))
                .andDo(print())
                .andReturn();

        assertNotEquals(HttpStatus.UNAUTHORIZED.value(), mvcResult.getResponse().getStatus());
    }

    @Test
    void obtainUserFromAuthHeader() {
        User expected = new User();
        expected.setId(1L);
        expected.setUsername("buyer1");

        List<String> authorities = List.of("ROLE_B");
        UserDto user = UserDto.builder()
                .id(expected.getId())
                .username(expected.getUsername())
                .build();
        String token = jwtProvider.createToken(user, authorities);
        User obtained = jwtProvider.getUser("Bearer " + token);

        assertEquals(expected.getId(), obtained.getId());
    }

}
