package com.mercadolivre.projetointegrador.inboundorder.dto;

import com.mercadolivre.projetointegrador.batch.dto.BatchResponseDto;
import com.mercadolivre.projetointegrador.batch.model.Batch;
import com.mercadolivre.projetointegrador.batch.service.BatchService;
import com.mercadolivre.projetointegrador.inboundorder.model.InboundOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InboundOrderResponseDto {

    private List<BatchResponseDto> batchStock;

    public static InboundOrderResponseDto ConvertToDto(InboundOrder inboundOrder) {
        List<Batch> batchStock = inboundOrder.getBatchStock();
        List<BatchResponseDto> batchResponseDtoList = BatchResponseDto.ConvertToResponseDto(batchStock);
        InboundOrderResponseDto dto = InboundOrderResponseDto.builder().batchStock(batchResponseDtoList).build();
        return dto;
    }

    public static List<InboundOrderResponseDto> ConvertToDto(List<InboundOrder> inboundOrderList) {
        List<InboundOrderResponseDto> result = inboundOrderList.stream().map(i -> ConvertToDto(i)).collect(Collectors.toList());
        return result;
    }

}
