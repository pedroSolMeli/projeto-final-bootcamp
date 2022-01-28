package com.mercadolivre.projetointegrador.stock.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mercadolivre.projetointegrador.stock.enums.ProductType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Section implements Serializable {

    private static final long serialVersionUID = 4446300291155815773L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String sectionCode;

    @Enumerated(EnumType.STRING)
    private ProductType sectionType;

    @ManyToOne
    @JsonIgnoreProperties("sections")
    private Warehouse warehouseCode;


    private Integer maxCapacity;

}
