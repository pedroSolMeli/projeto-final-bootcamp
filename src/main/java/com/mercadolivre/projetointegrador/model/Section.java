package com.mercadolivre.projetointegrador.model;

import com.mercadolivre.projetointegrador.enums.ProductType;
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
    private Warehouse warehouseCode;

    private Integer maxCapacity;

}
