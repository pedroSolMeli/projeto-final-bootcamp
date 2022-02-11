package com.mercadolivre.projetointegrador.purchaseProduct.dto;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.mercadolivre.projetointegrador.product.model.Product;
import com.mercadolivre.projetointegrador.product.service.ProductService;
import com.mercadolivre.projetointegrador.purchaseProduct.model.PurchaseProduct;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseProductDto {

	private int quantity;

	private Long productId;

	public PurchaseProduct ConvertToObject(PurchaseProductDto dto) {
		Product product = Product.builder().id(dto.productId).build();
		PurchaseProduct object = PurchaseProduct.builder().quantity(dto.getQuantity()).product(product).build();
		return object;
	}

	public List<PurchaseProduct> ConvertToObject(List<PurchaseProductDto> dtos) {
		return dtos.stream().map(dto -> ConvertToObject(dto)).collect(Collectors.toList());
	}

}
