package com.mercadolivre.projetointegrador.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
public class Batch {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer batchNumber;

    @ManyToOne
    private InboundOrder inboundOrder;

    private Long productId;
    private Double currentTemperature;
    private Double minimalTemperature;
    private Integer initialQuantity;
    private Integer currentQuantity;
    private LocalDate manufacturingDate;
    private LocalDateTime manufacturingTime;
    private LocalDate dueDate;

}
