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
public class SectionControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturnSectionList() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/section"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @Test
    public void shouldCreateSection() throws Exception {

        String payload = "{\n" +
                "\t\"sectionCode\": \"1891906\",\n" +
                "\t\"sectionType\": \"frozen\",\n" +
                "\t\"maxCapacity\": 10,\n" +
                "\t\"warehouseCode\": \"RJ09\"\n" +
                "}";
        mockMvc.perform(MockMvcRequestBuilders
                .post("/section")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

}