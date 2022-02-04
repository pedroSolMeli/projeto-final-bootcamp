package com.mercadolivre.projetointegrador.controller;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class WarehouseControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldCreateWarehouse() throws Exception {
        String payload = "{ \"code\": \"RJ59\" }";
        mockMvc.perform(MockMvcRequestBuilders
                .post("/warehouse")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void shouldReturnConflictCreatingWarehouse() throws Exception {
        String payload = "{ \"code\": \"RJ2\" }";
        mockMvc.perform(MockMvcRequestBuilders
                .post("/warehouse")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload))
                .andExpect(MockMvcResultMatchers.status().isConflict());
    }

    @Test
    public void shouldReturnWarehouseList() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/warehouse"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}