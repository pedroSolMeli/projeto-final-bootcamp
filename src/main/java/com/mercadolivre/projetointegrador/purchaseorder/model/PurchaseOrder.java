package com.mercadolivre.projetointegrador.purchaseorder.model;


import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mercadolivre.projetointegrador.enums.OrderStatus;
import com.mercadolivre.projetointegrador.purchaseProduct.model.PurchaseProduct;
import com.mercadolivre.projetointegrador.user.model.User;

import lombok.*;


@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseOrder {
	
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

	@JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate orderDate;
    
	@ManyToOne
    @JsonIgnoreProperties("orders")
    private User buyer;
    
    private OrderStatus orderStatus;
    
    @OneToMany(cascade = CascadeType.ALL)
    private List<PurchaseProduct> purchaseProducts;

// TODO COLOCAR TOTAL 
//	totalPrice(response) DOUBLE


}
