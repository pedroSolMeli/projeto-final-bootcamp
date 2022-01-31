package com.mercadolivre.projetointegrador.purchaseorder.service;

import com.mercadolivre.projetointegrador.purchaseorder.repository.PurchaseOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class PurchaseOrderService {

    @Qualifier("PurchaseOrderRepository")
    @Autowired
    PurchaseOrderRepository purchaseOrderRepository;

}
