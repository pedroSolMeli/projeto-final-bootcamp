package com.mercadolivre.projetointegrador.service;

import java.util.Optional;

import com.mercadolivre.projetointegrador.batch.service.BatchService;


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

import com.mercadolivre.projetointegrador.batch.model.Batch;
import com.mercadolivre.projetointegrador.batch.repository.BatchRepository;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class BatchServiceTests {
	
	@Mock
	BatchRepository batchRepository;

	@InjectMocks
	BatchService batchService;


	
	@Test
	public void shouldReturnBatchByIdSuccessfully() {
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
