package com.mercadolivre.projetointegrador.purchaseorder.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.mercadolivre.projetointegrador.inboundorder.model.InboundOrder;
import com.mercadolivre.projetointegrador.product.model.Product;
import com.mercadolivre.projetointegrador.product.service.ProductService;
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
    
	@Autowired
	ProductService productService;

//	public PurchaseOrder createaPurchaseOrder(PurchaseOrderDto order) {
//		User userObject = User.builder().id(order.getBuyerId()).build();
//		PurchaseOrder orderToSave = order.ConvertToObject(order, userObject);
//		return repository.save(orderToSave);
//	}
	
	
	public BigDecimal createaPurchaseOrder(PurchaseOrderDto order) {
	User userObject = User.builder().id(order.getBuyerId()).build();
	PurchaseOrder orderToSave = order.ConvertToObject(order, userObject);
	PurchaseOrder orderSave = repository.save(orderToSave);
	BigDecimal cartTotalPrice = calculateTotalPriceCart(orderSave.getId());
//	BigDecimal cartTotalPrice = calculateTotalPriceCart(orderSave);
	return cartTotalPrice;
}

	private BigDecimal calculateTotalPriceCart(Long id) {
		PurchaseOrder order = repository.getById(id);
		List<PurchaseProduct> listToCalculate = order.getPurchaseProducts();
		BigDecimal total = new BigDecimal(0.0);
		for (PurchaseProduct purchaseProduct : listToCalculate) {
			Long idProduto = purchaseProduct.getProduct().getId();
			Product product = productService.getProductById(idProduto);
			BigDecimal price =(product.getPrice());
			BigDecimal quantity = new BigDecimal(purchaseProduct.getQuantity());
			
			total = total.add(price.multiply(quantity));
			
			
		}
		return total;
	}

	public List<PurchaseOrder> findAll() {
		List<PurchaseOrder> result = repository.findAll();
		return result;
	}

	public PurchaseOrder findPurchaseOrder(Long id) {
		PurchaseOrder result = repository.getById(id);
		return result;
	}

	public PurchaseOrder updatePurchaseOrder(PurchaseOrder order) {
		PurchaseOrder result = repository.saveAndFlush(order);
		return result;
	}

	public void deleteById(Long id) {
		repository.deleteById(id);
		
	}

}
