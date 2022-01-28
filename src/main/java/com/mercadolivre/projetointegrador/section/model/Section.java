package com.mercadolivre.projetointegrador.section.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mercadolivre.projetointegrador.enums.ProductType;
import com.mercadolivre.projetointegrador.inboundorder.model.InboundOrder;
import com.mercadolivre.projetointegrador.warehouse.model.Warehouse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

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

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "section")
    @JsonIgnoreProperties("section")
    private List<InboundOrder> inboundOrder;

    private Integer maxCapacity;

}
