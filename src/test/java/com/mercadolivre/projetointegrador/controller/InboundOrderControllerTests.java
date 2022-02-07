package com.mercadolivre.projetointegrador.controller;

import com.mercadolivre.projetointegrador.enums.ProductType;
import com.mercadolivre.projetointegrador.inboundorder.repository.InboundOrderRepository;
import com.mercadolivre.projetointegrador.section.model.Section;
import com.mercadolivre.projetointegrador.section.repository.SectionRepository;
import com.mercadolivre.projetointegrador.warehouse.model.Warehouse;
import com.mercadolivre.projetointegrador.warehouse.repository.WarehouseRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
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
public class InboundOrderControllerTests {

    @Mock
    InboundOrderRepository inboundOrderRepository;

    @Mock
    WarehouseRepository warehouseRepository;

    @Mock
    SectionRepository sectionRepository;

    @Autowired
    private MockMvc mockMvc;

    Section section;
    Warehouse warehouse = Warehouse.builder().id(1l).code("RJ1").build();

    @Test
    public void shouldCreateInboundOrder() throws Exception {
        section = Section.builder().id(1l).code("FZ01")
                .sectionType(ProductType.FROZEN).maxCapacity(85)
                .warehouse(warehouse).build();

        String payload = "{" +
                "\"inboundOrder\":{" +
                "\"orderNumber\": 987," +
                "\"orderDate\": \"2001-01-09\"," +
                "\"section\":{" +
                "\"sectionCode\":\"FZ2\"," +
                "\"warehouseCode\":\"RJ1\"" +
                "}," +
                "\"batchStock\":[" +
                "{" +
                "\"batchNumber\": 1," +
                "\"productId\": 1," +
                "\"currentTemperature\": 11.3," +
                "\"minimalTemperature\": 15.0," +
                "\"initialQuantity\": 10," +
                "\"currentQuantity\": 8," +
                "\"manufacturingDate\": \"2022-01-15\"," +
                "\"manufacturingTime\": \"2022-01-15 13:10\"," +
                "\"dueDate\": \"2022-02-25\"" +
                "}]}}";

        mockMvc.perform(MockMvcRequestBuilders
                .post("/inboundorder")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }


    @Test
    public void shouldReturnSectionCodeNotFound() throws Exception {
        String payload = "{" +
                "\"inboundOrder\":{" +
                "\"orderNumber\": 987," +
                "\"orderDate\": \"2001-01-09\"," +
                "\"section\":{" +
                "\"sectionCode\":\"FZ2\"," +
                "\"warehouseCode\":\"RJ1\"" +
                "}," +
                "\"batchStock\":[" +
                "{" +
                "\"batchNumber\": 1," +
                "\"productId\": 1," +
                "\"currentTemperature\": 11.3," +
                "\"minimalTemperature\": 15.0," +
                "\"initialQuantity\": 10," +
                "\"currentQuantity\": 8," +
                "\"manufacturingDate\": \"2022-01-15\"," +
                "\"manufacturingTime\": \"2022-01-15 13:10\"," +
                "\"dueDate\": \"2022-02-25\"" +
                "}]}}";
        mockMvc.perform(MockMvcRequestBuilders
                .post("/inboundorder")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void shouldReturnWarehouseCodeNotFoundWhenCreatingInboundOrder() throws Exception {
        String payload = "{" +
                "\"inboundOrder\":{" +
                "\"orderNumber\": 987," +
                "\"orderDate\": \"2001-01-09\"," +
                "\"section\":{" +
                "\"sectionCode\":\"FZ2\"," +
                "\"warehouseCode\":\"RJ1\"" +
                "}," +
                "\"batchStock\":[" +
                "{" +
                "\"batchNumber\": 1," +
                "\"productId\": 1," +
                "\"currentTemperature\": 11.3," +
                "\"minimalTemperature\": 15.0," +
                "\"initialQuantity\": 10," +
                "\"currentQuantity\": 8," +
                "\"manufacturingDate\": \"2022-01-15\"," +
                "\"manufacturingTime\": \"2022-01-15 13:10\"," +
                "\"dueDate\": \"2022-02-25\"" +
                "}]}}";
        mockMvc.perform(MockMvcRequestBuilders
                .post("/inboundorder")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}