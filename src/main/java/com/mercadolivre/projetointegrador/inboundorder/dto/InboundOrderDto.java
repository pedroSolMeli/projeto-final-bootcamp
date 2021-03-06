package com.mercadolivre.projetointegrador.inboundorder.dto;

import com.mercadolivre.projetointegrador.batch.dto.BatchRequestDto;
import com.mercadolivre.projetointegrador.section.dto.SectionDto;
import lombok.Builder;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class InboundOrderDto implements Serializable{

	private static final long serialVersionUID = 1468401630009655948L;

	@NotNull(message = "orderNumber cannot be null")
    private Long orderNumber;

    @NotNull(message = "orderNumber cannot be null")
    private LocalDate orderDate;

    @Valid
    private SectionDto section;

    @Valid
    private List<BatchRequestDto> batchStock;


}
