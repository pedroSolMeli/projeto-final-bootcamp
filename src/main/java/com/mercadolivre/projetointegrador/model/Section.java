package com.mercadolivre.projetointegrador.model;

import com.mercadolivre.projetointegrador.enums.ProductType;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Section {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String sectionCode;
    private ProductType sectionType;

    @ManyToOne
    private Warehouse warehouseCode;

    private Integer maxCapacity;

}
