package com.mercadolivre.projetointegrador.inboundorder.dto;

import com.mercadolivre.projetointegrador.batch.model.Batch;
import com.mercadolivre.projetointegrador.inboundorder.model.InboundOrder;

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
