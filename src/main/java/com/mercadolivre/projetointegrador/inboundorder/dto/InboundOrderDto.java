package com.mercadolivre.projetointegrador.inboundorder.dto;

import com.mercadolivre.projetointegrador.section.dto.SectionDto;
import lombok.Builder;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@Builder
public class InboundOrderDto {

    @NotNull(message = "orderNumber cannot be null")
    private Long orderNumber;

    @NotNull(message = "orderNumber cannot be null")
    private LocalDate orderDate;

    @Valid
    private SectionDto section;




}
