package com.mercadolivre.projetointegrador.model;

import com.mercadolivre.projetointegrador.enums.ProductType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


import javax.persistence.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Section implements Serializable{

	private static final long serialVersionUID = 4446300291155815773L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String sectionCode;
    private ProductType sectionType;

    @ManyToOne
//    @JoinColumn(name = "warehouse_id", nullable = false)
    private Warehouse warehouseCode;

    private Integer maxCapacity;

}
