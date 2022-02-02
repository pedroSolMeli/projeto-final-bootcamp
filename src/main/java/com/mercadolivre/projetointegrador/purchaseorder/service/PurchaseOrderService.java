package com.mercadolivre.projetointegrador.purchaseorder.service;

import com.mercadolivre.projetointegrador.purchaseorder.dto.PurchaseOrderDto;
import com.mercadolivre.projetointegrador.purchaseorder.model.PurchaseOrder;
import com.mercadolivre.projetointegrador.purchaseorder.repository.PurchaseOrderRepository;
import com.mercadolivre.projetointegrador.user.model.User;
import com.mercadolivre.projetointegrador.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class PurchaseOrderService {

    @Qualifier("PurchaseOrderRepository")
    @Autowired
    PurchaseOrderRepository repository;

	public PurchaseOrder createaPurchaseOrder(PurchaseOrderDto order) {
		User userObject = User.builder().id(order.getBuyerId()).build();
		PurchaseOrder orderToSave = order.ConvertToObject(order, userObject);
		return repository.save(orderToSave);
	}

}
