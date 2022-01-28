package com.mercadolivre.projetointegrador.stock.dto;

import com.mercadolivre.projetointegrador.stock.model.Batch;
import com.mercadolivre.projetointegrador.stock.model.InboundOrder;
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
