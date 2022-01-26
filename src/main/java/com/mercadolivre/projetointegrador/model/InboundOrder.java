package com.mercadolivre.projetointegrador.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity

public class InboundOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long orderNumber;

    private LocalDate orderDate;

    @ManyToOne
    private Section section;

    @OneToMany
    private List<Batch> batchStock;

}
