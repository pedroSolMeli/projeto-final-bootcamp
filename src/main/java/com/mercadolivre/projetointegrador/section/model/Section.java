package com.mercadolivre.projetointegrador.section.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mercadolivre.projetointegrador.enums.ProductType;
import com.mercadolivre.projetointegrador.inboundorder.model.InboundOrder;
import com.mercadolivre.projetointegrador.warehouse.model.Warehouse;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Section implements Serializable {

    private static final long serialVersionUID = 4446300291155815773L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String code;

    @Enumerated(EnumType.STRING)
    private ProductType sectionType;

    @ManyToOne
    @JsonIgnoreProperties("sections")
    private Warehouse warehouse;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "section")
    @JsonIgnoreProperties("section")
    private List<InboundOrder> inboundOrder;

    private Integer maxCapacity;

}
