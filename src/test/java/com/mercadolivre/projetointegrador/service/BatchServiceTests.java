package com.mercadolivre.projetointegrador.service;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.mercadolivre.projetointegrador.batch.model.Batch;
import com.mercadolivre.projetointegrador.batch.repository.BatchRepository;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class BatchServiceTests {
	
	@Mock
	BatchRepository batchRepository;
	
	
	
	@Test
	public void shouldReturnBatchByIdSuccessfully() {
		//given
		Batch batch = Batch.builder().batchNumber(1l).currentQuantity(2).dueDate(null).currentTemperature(null).id(2l).product(null).build();
		//when
		Mockito.when(batchRepository.findById(2l)).thenReturn(Optional.ofNullable(batch));
		//then
		
	}
	

}
