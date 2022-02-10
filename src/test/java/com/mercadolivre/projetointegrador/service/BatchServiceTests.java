package com.mercadolivre.projetointegrador.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import com.mercadolivre.projetointegrador.batch.service.BatchService;
import com.mercadolivre.projetointegrador.product.model.Product;
import com.mercadolivre.projetointegrador.product.service.ProductService;

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

	@Mock
	Product product;
	
	@Test
	public void shouldCreateBatchWithSucces() {
		// given
		BatchRequestDto batchRequestDto = BatchRequestDto.builder()
				.batchNumber(1l)
				.currentQuantity(1)
				.productId(1L)
				.currentTemperature(10.3)
				.minimalTemperature(15.0)
       			.initialQuantity(60)
       			.manufacturingDate(LocalDate.of(2022, 01, 15))
       			.manufacturingTime(LocalDateTime.of(2022, 01, 15, 13, 10, 00))
       			.dueDate(LocalDate.of(2022, 10, 15))
       			.build();
		product.setId(1L);
		
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
       			.product(product)
       			.build();
		
		
//		when
//		Mockito.doNothing().when(batchService).checkIfBatchNumberExists(batchRequestDto.getBatchNumber());
//		Mockito.doNothing().when(batchService).checkIfManufacturingDateAndTimeAreTheSame(batchRequestDto.getManufacturingDate(), batchRequestDto.getManufacturingTime());
//		Mockito.doNothing().when(batchService).validateDueDate(batchRequestDto.getDueDate());
		Mockito.when(productService.getProductById(batchRequestDto.getProductId())).thenReturn(product);
		Mockito.when(BatchRequestDto.ConvertToObject(batchRequestDto, product)).thenReturn(batch);
		Mockito.when(batchRepository.saveAndFlush(batch)).thenReturn(batch);
		
		
		BatchResponseDto responseDto = batchService.createBatch(batchRequestDto); 
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
