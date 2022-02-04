package com.mercadolivre.projetointegrador.service;

import com.mercadolivre.projetointegrador.warehouse.model.Warehouse;
import com.mercadolivre.projetointegrador.warehouse.service.WarehouseService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class WarehouseServiceTests {

    @Mock
    WarehouseService warehouseService;

    @Test
    public void shouldReturnWarehouseListWithSucces() {
        List<Warehouse> warehouseList = new ArrayList<>();
        Warehouse warehouse = Warehouse.builder().id(1L).code("RJ1").build();
        warehouseList.add(warehouse);

        Mockito.when(warehouseService.findAllWarehouses()).thenReturn(warehouseList);

    }

    @Test
    public void shouldReturnWarehouseByCodeWithSucces() {

    }


}
