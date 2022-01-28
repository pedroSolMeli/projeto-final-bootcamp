package com.mercadolivre.projetointegrador.stock.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Batch implements Serializable{

	private static final long serialVersionUID = 7102445062867907659L;

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long batchNumber;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_inbound", nullable = false)
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
