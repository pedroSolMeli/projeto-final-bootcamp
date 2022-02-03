package com.mercadolivre.projetointegrador.purchaseorder.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.mercadolivre.projetointegrador.product.model.Product;
import com.mercadolivre.projetointegrador.purchaseProduct.model.PurchaseProduct;
import com.mercadolivre.projetointegrador.purchaseorder.dto.PurchaseOrderDto;
import com.mercadolivre.projetointegrador.purchaseorder.model.PurchaseOrder;
import com.mercadolivre.projetointegrador.purchaseorder.repository.PurchaseOrderRepository;
import com.mercadolivre.projetointegrador.user.model.User;

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
	
	
//	public BigDecimal createaPurchaseOrder(PurchaseOrderDto order) {
//	User userObject = User.builder().id(order.getBuyerId()).build();
//	PurchaseOrder orderToSave = order.ConvertToObject(order, userObject);
//	PurchaseOrder orderSave = repository.save(orderToSave);
//	BigDecimal cartTotalPrice = calculateTotalPriceCart(orderSave.getId());
////	BigDecimal cartTotalPrice = calculateTotalPriceCart(orderSave);
//	return cartTotalPrice;
//}

//	private BigDecimal calculateTotalPriceCart(Long id) {
//		PurchaseOrder order = repository.getById(id);
//		List<PurchaseProduct> listToCalculate = order.getPurchaseProducts();
//		BigDecimal total = new BigDecimal(0);
//		for (PurchaseProduct purchaseProduct : listToCalculate) {
//			Product product = purchaseProduct.getProduct();
//			BigDecimal quantity = new BigDecimal(purchaseProduct.getQuantity());
//			total.add((product.getPrice()).multiply(quantity));
//			
//		}
//		return total;
//	}

}
