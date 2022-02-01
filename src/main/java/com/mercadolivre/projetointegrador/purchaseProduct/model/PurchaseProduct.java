package com.mercadolivre.projetointegrador.purchaseProduct.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mercadolivre.projetointegrador.product.model.Product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseProduct {
	
	  	@Id
	    @GeneratedValue(strategy = GenerationType.SEQUENCE)
	    private Long id;
	  	
	  	private int quantity;
	  	
	    @ManyToOne
	    @JsonIgnoreProperties("purchaseProduct")
	    private Product productId;
	

}
