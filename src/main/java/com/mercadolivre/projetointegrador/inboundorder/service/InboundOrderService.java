package com.mercadolivre.projetointegrador.inboundorder.service;

import com.mercadolivre.projetointegrador.batch.model.Batch;
import com.mercadolivre.projetointegrador.batch.service.BatchService;
import com.mercadolivre.projetointegrador.inboundorder.dto.InboundOrderRequestDto;
import com.mercadolivre.projetointegrador.inboundorder.dto.InboundOrderResponseDto;
import com.mercadolivre.projetointegrador.inboundorder.model.InboundOrder;
import com.mercadolivre.projetointegrador.inboundorder.repository.InboundOrderRepository;
import com.mercadolivre.projetointegrador.section.dto.SectionDto;
import com.mercadolivre.projetointegrador.section.model.Section;
import com.mercadolivre.projetointegrador.section.service.SectionService;
import com.mercadolivre.projetointegrador.warehouse.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class InboundOrderService {

    @Qualifier("InboundOrderRepository")
    @Autowired
    InboundOrderRepository inboundOrderRepository;

    @Autowired
    WarehouseService warehouseService;

    @Autowired
    SectionService sectionService;

    @Autowired
    BatchService batchService;

    public InboundOrderResponseDto createInboundOrder(InboundOrderRequestDto inboundOrderRequestDto) {

        SectionDto sectionDto = inboundOrderRequestDto.getInboundOrder().getSection();
        Section section = sectionService.getSectionBySectionCodeAndWarehouseCode(sectionDto.getSectionCode(), sectionDto.getWarehouseCode());

        InboundOrder inboundOrder = InboundOrderRequestDto.ConvertToObject(inboundOrderRequestDto, section);
        //aqui salva sem a lista batch
        InboundOrder inboundOrderPopulated = inboundOrderRepository.save(inboundOrder);
//        aqui converte a lista no jeito q é para ficar no registro
        List<Batch> batchListWithInboundOrder = batchService.populateBatchListWithInboundOrder(inboundOrderRequestDto, inboundOrderPopulated);
        //aqui popula mas não salva, pode seer isso??? q na primeira salva uma lista agregada vazia e depois seta outra??? ver objetos e dtos melhor
        inboundOrderPopulated.setBatchStock(batchListWithInboundOrder);
        //para q usa o soma batch??
        int somaBatch = 0;
        for (InboundOrder y : section.getInboundOrder()) {
            somaBatch = somaBatch + y.getBatchStock().size();
        }

        
        //o inboundOrder objeto aqui é só o objeto, a lista de batch foi setada no outro ali, não nele
        
        for (Batch i : inboundOrder.getBatchStock()) {
            if (inboundOrder.getSection().getSectionType() == i.getProduct().getProductType()) {
                if (inboundOrder.getSection().getMaxCapacity() >= somaBatch) {
                	//aqui dá o if e o noll sem fazer nada, tipo variavel local e sem return??? acho q entendi q é para inicializar e usar o método convert
                InboundOrderResponseDto inboundOrderResponseDto = null;
                try {
                    InboundOrder result = inboundOrderRepository.saveAndFlush(inboundOrderPopulated);
                    inboundOrderResponseDto = InboundOrderResponseDto.ConvertToDto(result);
                } catch (DataIntegrityViolationException ex) {
//                	ver se tá ok... testei e apareceu, só q a gente não salva ali antes??? salva sim, tá registrando o inbound order mesmo , pois ele estoura esse erro depois de  salvo
                    ResponseStatusException responseStatusException = new ResponseStatusException(HttpStatus.BAD_REQUEST, "batchNumber/orderNumber already exists");
                    throw responseStatusException;
                }
                    return inboundOrderResponseDto;
                } else {
                	// o mesmo q anterior, já fez o registo
                    throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "could not add batch, section is full");
                }
            }
        }

        throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "product type does not match section type");

    }
    public List<InboundOrderResponseDto> findAllInboundOrders() {
        List<InboundOrder> result = inboundOrderRepository.findAll();
        List<InboundOrderResponseDto> response = InboundOrderResponseDto.ConvertToDto(result);
        return response;
    }

    public InboundOrder updateInboundOrder(InboundOrder inboundOrder) {
    	//TODO ajustar para chamar o create
        return inboundOrderRepository.saveAndFlush(inboundOrder);
    }

}
