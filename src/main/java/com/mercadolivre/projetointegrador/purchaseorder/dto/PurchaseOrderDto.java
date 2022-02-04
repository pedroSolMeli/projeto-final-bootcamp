package com.mercadolivre.projetointegrador.purchaseorder.dto;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mercadolivre.projetointegrador.enums.OrderStatus;
import com.mercadolivre.projetointegrador.purchaseProduct.dto.PurchaseProductDto;
import com.mercadolivre.projetointegrador.purchaseProduct.model.PurchaseProduct;
import com.mercadolivre.projetointegrador.purchaseorder.model.PurchaseOrder;

import com.mercadolivre.projetointegrador.user.model.User;
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

    @NotNull(message = "orderNumber cannot be null")
	private LocalDate orderDate;

    private Long buyerId;
    private OrderStatus orderStatus;
    private List<PurchaseProductDto> products;
    
    public PurchaseOrder ConvertToObject(PurchaseOrderDto purchaseOrderDto, User user) {
    	PurchaseProductDto productDto = PurchaseProductDto.builder().build();
    	List<PurchaseProduct> productsObjects = productDto.ConvertToObject(purchaseOrderDto.getProducts());
        PurchaseOrder object = PurchaseOrder.builder()
    			.orderDate(purchaseOrderDto.getOrderDate())
    			.buyer(user)
    			.orderStatus(purchaseOrderDto.getOrderStatus())
    			.purchaseProducts(productsObjects)
                .build();
        return object;
    }

}
