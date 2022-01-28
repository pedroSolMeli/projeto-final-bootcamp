package com.mercadolivre.projetointegrador.stock.dto;

import com.mercadolivre.projetointegrador.stock.model.InboundOrder;
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
