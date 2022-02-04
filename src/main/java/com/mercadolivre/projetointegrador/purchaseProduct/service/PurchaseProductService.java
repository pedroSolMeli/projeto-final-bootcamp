package com.mercadolivre.projetointegrador.purchaseProduct.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mercadolivre.projetointegrador.product.model.Product;
import com.mercadolivre.projetointegrador.product.service.ProductService;
import com.mercadolivre.projetointegrador.purchaseProduct.model.PurchaseProduct;

@Service
public class PurchaseProductService {
	
	@Autowired
	ProductService productService;
	
	private Boolean verifyProductInPurchase(List<PurchaseProduct> productsPurchase) {
		for (PurchaseProduct purchaseProduct : productsPurchase) {
			Long idProduct = purchaseProduct.getProduct().getId();
			Product product = productService.getProductById(idProduct);
			
		}
		
		return null;
		
	}
	

} 
