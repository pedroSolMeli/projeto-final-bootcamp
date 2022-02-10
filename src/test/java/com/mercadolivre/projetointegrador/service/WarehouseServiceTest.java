package com.mercadolivre.projetointegrador.service;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.mercadolivre.projetointegrador.batch.dto.BatchStockDto;
import com.mercadolivre.projetointegrador.batch.model.Batch;
import com.mercadolivre.projetointegrador.enums.ProductType;
import com.mercadolivre.projetointegrador.enums.UserRole;
import com.mercadolivre.projetointegrador.inboundorder.model.InboundOrder;
import com.mercadolivre.projetointegrador.product.model.Product;
import com.mercadolivre.projetointegrador.section.model.Section;
import com.mercadolivre.projetointegrador.user.model.User;
import com.mercadolivre.projetointegrador.user.service.UserService;
import com.mercadolivre.projetointegrador.warehouse.dto.WarehouseRequestDto;
import com.mercadolivre.projetointegrador.warehouse.dto.WarehouseResponseDto;
import com.mercadolivre.projetointegrador.warehouse.model.Warehouse;
import com.mercadolivre.projetointegrador.warehouse.repository.WarehouseRepository;
import com.mercadolivre.projetointegrador.warehouse.service.WarehouseService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.server.ResponseStatusException;

import javax.management.relation.Role;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class WarehouseServiceTest {

    @Mock
    UserService userService;

    @Mock
    WarehouseRepository repository;

    @InjectMocks
    WarehouseService warehouseService;

    @Test
    public void shouldReturnWarehouseListWithSucces() {
        List<Warehouse> warehouseList = new ArrayList<>();
        Warehouse warehouse = Warehouse.builder().id(1L).code("RJ1").build();
        warehouseList.add(warehouse);

        Mockito.when(repository.findAll()).thenReturn(warehouseList);
        List<Warehouse> allWarehouses = warehouseService.findAllWarehouses();

        Assertions.assertEquals(allWarehouses, warehouseList);
    }

    @Test
    public void shouldReturnWarehouseByCodeWithSuccess() {
        Warehouse warehouse = Warehouse.builder().id(1L).code("RJ1").build();

        Mockito.when(repository.getWarehouseByCode("RJ1")).thenReturn(warehouse);

        Warehouse result = warehouseService.getWarehouseByCode("RJ1");

        Assertions.assertEquals(warehouse, result);
    }

    @Test
    public void shouldCreateWarehouseWithUnssuccess() {
        ArrayList<Long> arrayUserIds = new ArrayList<>();
        arrayUserIds.add(1l);
        WarehouseRequestDto warehouseRequestDto = WarehouseRequestDto.builder().code("RJ1").users(arrayUserIds).build();

        ArrayList<UserRole> arrayRoles = new ArrayList<>();
        arrayRoles.add(UserRole.A);

        User user = User.builder()
                .cpf("86545843001")
                .name("pedro")
                .username("pedro")
                .email("pedro@email.com")
                .password("pedro123")
                .roles(arrayRoles)
                .build();

        ArrayList<User> arrayUser = new ArrayList<>();
        arrayUser.add(user);

        Warehouse warehouse = Warehouse.builder().id(1L).code("RJ1").users(arrayUser).build();

        Mockito.when(userService.findUserWithoutConvert(1l)).thenReturn(user);
        Mockito.when(repository.saveAndFlush(Mockito.any())).thenReturn(warehouse);

        WarehouseResponseDto result = warehouseService.createWarehouse(warehouseRequestDto);

        Assertions.assertEquals(warehouse.getId(), result.getId());
    }

    @Test
    public void shouldCreateWarehouseWithSuccess() {
        ArrayList<Long> arrayUserIds = new ArrayList<>();
        arrayUserIds.add(1l);
        WarehouseRequestDto warehouseRequestDto = WarehouseRequestDto.builder().code("RJ1").users(arrayUserIds).build();

        ArrayList<UserRole> arrayRoles = new ArrayList<>();
        arrayRoles.add(UserRole.A);

        User user = User.builder()
                .cpf("86545843001")
                .name("pedro")
                .username("pedro")
                .email("pedro@email.com")
                .password("pedro123")
                .roles(arrayRoles)
                .build();

        Mockito.when(userService.findUserWithoutConvert(1l)).thenReturn(user);
        Mockito.when(repository.saveAndFlush(Mockito.any())).thenThrow(new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "User already exists in other warehouse"));

        Assertions.assertThrows(ResponseStatusException.class, () -> warehouseService.createWarehouse(warehouseRequestDto));
    }

    @Test
    public void shouldgetWarehousesByProductsWithSuccess() {
        Product product = Product.builder()
                .id(1l)
                .name("batata")
                .productType(ProductType.REFRIGERATED)
                .price(BigDecimal.valueOf(10))
                .build();

        Batch batchRequestDto = Batch.builder()
                .batchNumber(1l)
                .product(product)
                .currentQuantity(1)
                .currentTemperature(10.3)
                .minimalTemperature(15.0)
                .initialQuantity(60)
                .manufacturingDate(LocalDate.of(2022, 01, 15))
                .manufacturingTime(LocalDateTime.of(2022, 01, 15, 13, 10, 00))
                .dueDate(LocalDate.of(2022, 10, 15))
                .build();

        Section section = Section.builder().id(1L).code("FZ01").sectionType(ProductType.FROZEN).maxCapacity(35).build();

        InboundOrder inboundOrder = InboundOrder.builder().id(1l).orderNumber(4l).section(section).batchStock(List.of(batchRequestDto)).build();

        Section section2 = Section.builder().id(1L).code("FZ01").sectionType(ProductType.FROZEN).inboundOrder(List.of(inboundOrder)).maxCapacity(35).build();

        List<Warehouse> warehouseList = new ArrayList<>();
        Warehouse warehouse = Warehouse.builder().id(1L).code("RJ1").sections(List.of(section2)).build();
        warehouseList.add(warehouse);

        Mockito.when(repository.findAll()).thenReturn(warehouseList);

        warehouseService.getWarehousesByProducts(1l);
    }

    @Test
    public void shouldgetWarehousesByProductsWithUnsuccess() {
        Assertions.assertThrows(ResponseStatusException.class, () -> warehouseService.getWarehousesByProducts(1l));
    }

}
