package com.mercadolivre.projetointegrador.batch.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mercadolivre.projetointegrador.inboundorder.model.InboundOrder;
import com.mercadolivre.projetointegrador.product.model.Product;
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
public class Batch implements Serializable {

    private static final long serialVersionUID = 7102445062867907659L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(unique = true)
    private Long batchNumber;

    @ManyToOne
    @JsonIgnoreProperties("batchStock")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private InboundOrder inboundOrder;

    @ManyToOne
    @JsonIgnoreProperties("batch")
    private Product productId;

    private Double currentTemperature;
    private Double minimalTemperature;

    private Integer initialQuantity;
    private Integer currentQuantity;

    private LocalDate manufacturingDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime manufacturingTime;

    private LocalDate dueDate;

}
