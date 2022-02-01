package com.mercadolivre.projetointegrador.purchaseorder.model;


import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.mercadolivre.projetointegrador.enums.OrderStatus;
import com.mercadolivre.projetointegrador.purchaseProduct.model.PurchaseProduct;
import com.mercadolivre.projetointegrador.user.model.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseOrder implements Serializable{
	
	private static final long serialVersionUID = 8900316972484234225L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private LocalDate orderDate;
    
    @OneToMany(cascade = CascadeType.ALL)
    private User buyerId;
    
    private OrderStatus status;
    
    @OneToMany(cascade = CascadeType.ALL)
    private List<PurchaseProduct> products;

// TODO COLOCAR TOTAL 
//	totalPrice(response) DOUBLE


}
