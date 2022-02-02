package com.mercadolivre.projetointegrador.purchaseorder.dto;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mercadolivre.projetointegrador.enums.OrderStatus;
import com.mercadolivre.projetointegrador.purchaseProduct.model.PurchaseProduct;
import com.mercadolivre.projetointegrador.purchaseorder.model.PurchaseOrder;

import com.mercadolivre.projetointegrador.user.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseOrderDto {
	
	@Autowired()
    UserService service;

    @NotNull(message = "orderNumber cannot be null")
	private LocalDate orderDate;
	
    private String buyerId;
    
    private OrderStatus orderStatus;
  
    private List<PurchaseProduct> products;
    
    public PurchaseOrder ConvertToObject(PurchaseOrderDto purchaseOrdertDto) {
    	PurchaseOrder object = PurchaseOrder.builder()
    			.orderDate(purchaseOrdertDto.getOrderDate())
    			.buyerId(service.getUserIdString(purchaseOrdertDto.getBuyerId()))
    			.orderStatus(purchaseOrdertDto.getOrderStatus())
    			.products(purchaseOrdertDto.getProducts())
                .build();
        return object;
    }
}
