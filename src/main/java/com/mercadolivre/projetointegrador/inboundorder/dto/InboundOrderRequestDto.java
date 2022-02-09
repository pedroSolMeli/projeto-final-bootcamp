package com.mercadolivre.projetointegrador.inboundorder.dto;

import com.mercadolivre.projetointegrador.inboundorder.model.InboundOrder;
import com.mercadolivre.projetointegrador.section.model.Section;
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

    public static InboundOrder ConvertToObject(InboundOrderRequestDto inboundOrderRequestDto, Section section) {
        InboundOrderDto inboundOrder = inboundOrderRequestDto.getInboundOrder();
        InboundOrder object = InboundOrder.builder()
        		.id(inboundOrder.getId())
                .orderNumber(inboundOrder.getOrderNumber())
                .orderDate(inboundOrder.getOrderDate())
                .section(section)
                .build();
        return object;
    }
}
