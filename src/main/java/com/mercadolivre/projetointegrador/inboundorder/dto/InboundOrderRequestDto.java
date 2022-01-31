package com.mercadolivre.projetointegrador.inboundorder.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InboundOrderRequestDto {

    @Valid
    private InboundOrderDto inboundOrder;


}
