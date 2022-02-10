package com.mercadolivre.projetointegrador.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import com.mercadolivre.projetointegrador.batch.service.BatchService;
import com.mercadolivre.projetointegrador.enums.ProductType;
import com.mercadolivre.projetointegrador.product.model.Product;
import com.mercadolivre.projetointegrador.product.service.ProductService;
import com.mercadolivre.projetointegrador.product.repository.ProductRepository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.server.ResponseStatusException;

import com.mercadolivre.projetointegrador.batch.dto.BatchRequestDto;
import com.mercadolivre.projetointegrador.batch.dto.BatchResponseDto;
import com.mercadolivre.projetointegrador.batch.model.Batch;
import com.mercadolivre.projetointegrador.batch.repository.BatchRepository;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class BatchServiceTests {
	
	@Mock
	BatchRepository batchRepository;

	@Mock
	ProductService productService;
	
	@InjectMocks
	BatchService batchService;
	
	@Test
	public void shouldCreateBatchWithSucces() {
		// given
		Product productBuild = Product.builder()
				.id(1l)
				.name("batata")
				.productType(ProductType.REFRIGERATED)
				.price(BigDecimal.valueOf(10))
				.build();

		BatchRequestDto batchRequestDtoBuild = BatchRequestDto.builder()
				.batchNumber(1l)
				.currentQuantity(1)
				.productId(1l)
				.currentTemperature(10.3)
				.minimalTemperature(15.0)
       			.initialQuantity(60)
       			.manufacturingDate(LocalDate.of(2022, 01, 15))
       			.manufacturingTime(LocalDateTime.of(2022, 01, 15, 13, 10, 00))
       			.dueDate(LocalDate.of(2022, 10, 15))
       			.build();
		
		Batch batch = Batch.builder()
				.batchNumber(1l)
				.currentQuantity(1)
				.currentTemperature(10.3)
				.minimalTemperature(15.0)
       			.initialQuantity(60)
       			.manufacturingDate(LocalDate.of(2022, 01, 15))
       			.manufacturingTime(LocalDateTime.of(2022, 01, 15, 13, 10, 00))
       			.dueDate(LocalDate.of(2022, 10, 15))
       			.id(1L)
       			.product(productBuild)
       			.build();

		Mockito.when(productService.getProductById(Mockito.any())).thenReturn(productBuild);
		Mockito.when(batchRepository.saveAndFlush(Mockito.any())).thenReturn(batch);
		BatchResponseDto responseDto = batchService.createBatch(batchRequestDtoBuild);

		Assertions.assertEquals(batch.getBatchNumber(), responseDto.getBatchNumber());
	}

	@Test
	public void shouldReturnBatchByIdWithSucces() {
		//given
		Batch batch = Batch.builder().batchNumber(1l).currentQuantity(2).dueDate(null).currentTemperature(null).id(2L).product(null).build();
		//when
		Mockito.when(batchRepository.findById(2L)).thenReturn(Optional.ofNullable(batch));
		Batch batchReturn = batchService.getById(2L);
		//then
		Assertions.assertEquals(2L, batchReturn.getId());
	}
	
	@Test
	public void shouldReturnBatchByIdAException() {
		 Assertions.assertThrows(ResponseStatusException.class, () -> batchService.getById(1L));
	}

}
