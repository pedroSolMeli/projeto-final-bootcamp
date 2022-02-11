package com.mercadolivre.projetointegrador.purchaseorder.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mercadolivre.projetointegrador.enums.OrderStatus;
import com.mercadolivre.projetointegrador.purchaseProduct.dto.PurchaseProductDto;
import com.mercadolivre.projetointegrador.purchaseProduct.model.PurchaseProduct;
import com.mercadolivre.projetointegrador.purchaseorder.model.PurchaseOrder;
import com.mercadolivre.projetointegrador.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseOrderDto {

    @JsonIgnoreProperties("id")
    private Long id;

    @NotNull(message = "orderNumber cannot be null")
    private LocalDate orderDate;

    private Long buyerId;

    private OrderStatus orderStatus;

    private List<PurchaseProductDto> products;

    public PurchaseOrder ConvertToObject(PurchaseOrderDto purchaseOrderDto, User user) {
        PurchaseProductDto productDto = PurchaseProductDto.builder().build();
        List<PurchaseProduct> productsObjects = productDto.ConvertToObject(purchaseOrderDto.getProducts());
        PurchaseOrder object = PurchaseOrder.builder()
                .id(purchaseOrderDto.getId())
                .orderDate(purchaseOrderDto.getOrderDate())
                .buyer(user)
                .orderStatus(purchaseOrderDto.getOrderStatus())
                .purchaseProducts(productsObjects)
                .build();
        return object;
    }

}
