package com.mercadolivre.projetointegrador.inboundorder.dto;

import com.mercadolivre.projetointegrador.section.dto.SectionResponseDto;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@Builder
public class InboundOrderDto {

    @NotNull
    private Long orderNumber;
    private LocalDate orderDate;





}
