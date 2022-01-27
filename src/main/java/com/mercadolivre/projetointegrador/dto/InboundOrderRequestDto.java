package com.mercadolivre.projetointegrador.dto;

import com.mercadolivre.projetointegrador.model.InboundOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InboundOrderRequestDto {

    private InboundOrder inboundOrder;

}
