package com.mercadolivre.projetointegrador.dto;

import com.mercadolivre.projetointegrador.model.Batch;
import com.mercadolivre.projetointegrador.model.InboundOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InboundOrderResponseDto {

    private List<Batch> batchStock;

    public InboundOrderResponseDto(InboundOrder inboundOrder){
        this.batchStock = inboundOrder.getBatchStock();
    }

}
