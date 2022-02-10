package com.mercadolivre.projetointegrador.controller;


import com.mercadolivre.projetointegrador.security.JwtProvider;
import com.mercadolivre.projetointegrador.user.dto.UserDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class WarehouseControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    JwtProvider jwtProvider;

    @Test
    public void shouldCreateWarehouse() throws Exception {
        UserDto user = UserDto.builder().id(2l).username("Agent1").roles(List.of("ROLE_A")).build();
        String token = jwtProvider.createToken(user, user.getRoles());

        String payload = "{\"code\":\"RJ001\",\"users\":[2]}";

        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post( "/warehouse")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization",  "Bearer "  + token).content(payload))
                .andDo(print())
                .andReturn();

        assertEquals(HttpStatus.CREATED.value(), mvcResult.getResponse().getStatus());
    }

//    @Test
//    public void shouldReturnConflictCreatingWarehouse() throws Exception {
//        String payload = "{\"code\":\"RJ001\",\"users\":[1]}";
//        mockMvc.perform(MockMvcRequestBuilders
//                .post("/warehouse")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(payload))
//                .andExpect(MockMvcResultMatchers.status().isConflict());
//    }
//
//    @Test
//    public void shouldReturnWarehouseList() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders
//                .get("/warehouse"))
//                .andExpect(MockMvcResultMatchers.status().isOk());
//    }
}