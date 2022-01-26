package com.mercadolivre.projetointegrador.service;

import com.mercadolivre.projetointegrador.model.InboundOrder;
import com.mercadolivre.projetointegrador.repository.InboundOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InboundOrderService {

    @Autowired
    InboundOrderRepository repository;

    public InboundOrder createInboundOrder(InboundOrder inboundOrder) {
        return repository.saveAndFlush(inboundOrder);
    }

    public InboundOrder updateInboundOrder(InboundOrder inboundOrder) {
        return repository.saveAndFlush(inboundOrder);
    }
}
