package com.mercadolivre.projetointegrador.purchaseorder.service;

import com.mercadolivre.projetointegrador.batch.dto.UnavailableProductDto;
import com.mercadolivre.projetointegrador.batch.service.BatchService;
import com.mercadolivre.projetointegrador.product.model.Product;
import com.mercadolivre.projetointegrador.product.service.ProductService;
import com.mercadolivre.projetointegrador.purchaseProduct.model.PurchaseProduct;
import com.mercadolivre.projetointegrador.purchaseProduct.service.PurchaseProductService;
import com.mercadolivre.projetointegrador.purchaseorder.dto.PurchaseOrderDto;
import com.mercadolivre.projetointegrador.purchaseorder.dto.PurschaseOrderResponseDto;
import com.mercadolivre.projetointegrador.purchaseorder.dto.PurschaseResponseDto;
import com.mercadolivre.projetointegrador.purchaseorder.model.PurchaseOrder;
import com.mercadolivre.projetointegrador.purchaseorder.repository.PurchaseOrderRepository;
import com.mercadolivre.projetointegrador.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class PurchaseOrderService {

	@Qualifier("PurchaseOrderRepository")
	@Autowired
	PurchaseOrderRepository repository;

	@Autowired
	ProductService productService;

	@Autowired
	PurchaseProductService purchaseProductService;

	@Autowired
	BatchService batchService;


	public PurschaseOrderResponseDto createaPurchaseOrder(PurchaseOrderDto order) {
		User userObject = User.builder().id(order.getBuyerId()).build();
		PurchaseOrder orderToSave = order.ConvertToObject(order, userObject);
		//TODO colocar a validaçao aqui
		List<UnavailableProductDto> unavailableProductDtoList = new ArrayList<>();

		for (PurchaseProduct purchaseProduct : orderToSave.getPurchaseProducts()) {

			Product product = productService.getProductById(purchaseProduct.getProduct().getId());

			UnavailableProductDto unavailableProductDto = batchService.validateIfProductIsAvailableInStock(purchaseProduct.getQuantity(), product.getId());

			if (unavailableProductDto == null){
				purchaseProductService.subtractProductFromStock(purchaseProduct);
				purchaseProduct.setHaveStock(true);
			}else {
				unavailableProductDtoList.add(unavailableProductDto);
				purchaseProduct.setHaveStock(false);
			}
		}

		BigDecimal cartTotalPrice = calculateTotalPriceCart(orderToSave.getPurchaseProducts());

		repository.saveAndFlush(orderToSave);

		return PurschaseOrderResponseDto.builder().price(cartTotalPrice).unavaibleProducts(unavailableProductDtoList).build();
	}

	private BigDecimal calculateTotalPriceCart(List<PurchaseProduct> purchasesProductList) {
		BigDecimal total = new BigDecimal(0.0);
		for (PurchaseProduct purchaseProduct : purchasesProductList) {
			if (purchaseProduct.isHaveStock()) {
				Product product = productService.getProductById(purchaseProduct.getProduct().getId());
				BigDecimal price = (product.getPrice());
				BigDecimal quantity = new BigDecimal(purchaseProduct.getQuantity());

				total = total.add(price.multiply(quantity));
			}
		}
		return total;
	}

	public List<PurchaseOrder> findAll() {
		List<PurchaseOrder> result = repository.findAll();
		return result;
	}

	public PurchaseOrder findPurchaseOrder(Long id) {
		PurchaseOrder result = repository.getById(id);

		//todo- realizar converte para retornar o dto correto
//		PurchaseOrderDto.builder().orderDate(result.getOrderDate()).orderStatus(result.getOrderStatus()).buyerId(result.getBuyer().getId()).products(result.getPurchaseProducts()).build();
//		PurschaseResponseDto.builder().purchaseOrder().build();
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
