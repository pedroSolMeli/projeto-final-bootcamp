package com.mercadolivre.projetointegrador.product.dto;

import com.mercadolivre.projetointegrador.batch.dto.BatchStockDto;
import com.mercadolivre.projetointegrador.section.dto.SectionDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class FindProductReponseDto {

    private SectionDto sectionDto;
    private Long productId;
    private List<BatchStockDto> batchStockDto;

}