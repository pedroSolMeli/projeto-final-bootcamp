package com.mercadolivre.projetointegrador.controller;

import com.mercadolivre.projetointegrador.security.JwtProvider;
import com.mercadolivre.projetointegrador.user.dto.UserDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class SectionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    JwtProvider jwtProvider;

    @Test
    public void shouldReturnSectionList() throws Exception {
        UserDto user = UserDto.builder().id(3l).username("Agent3").roles(List.of("ROLE_A")).build();
        String token = jwtProvider.createToken(user, user.getRoles());

        mockMvc.perform(MockMvcRequestBuilders
                .get("/section")
                .header("Authorization",  "Bearer "  + token))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @Test
    public void shouldCreateSectionUnsucess() throws Exception {
        UserDto user = UserDto.builder().id(3l).username("Agent3").roles(List.of("ROLE_A")).build();
        String token = jwtProvider.createToken(user, user.getRoles());

        String payload = "{\n" +
                "\t\"sectionCode\": \"1891906\",\n" +
                "\t\"sectionType\": \"frozen\",\n" +
                "\t\"maxCapacity\": 10,\n" +
                "\t\"warehouseCode\": \"RJ09\"\n" +
                "}";
        mockMvc.perform(MockMvcRequestBuilders
                .post("/section")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization",  "Bearer "  + token)
                .content(payload))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

}