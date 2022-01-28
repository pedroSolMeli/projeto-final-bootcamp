package com.mercadolivre.projetointegrador.order.model;


import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Builder;
import lombok.Data;


@Entity
@Data
@Builder
public class PurchaseOrder implements Serializable{
	
	private static final long serialVersionUID = 8900316972484234225L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private LocalDate orderDate;

//	userId USER
//	orderStatus STRING/enum
//	products[] LISTA
//	totalPrice(response) DOUBLE
//
//	enumOrderStatus

}
