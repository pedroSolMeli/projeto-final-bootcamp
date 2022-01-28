package com.mercadolivre.projetointegrador.inboundorder.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mercadolivre.projetointegrador.batch.model.Batch;

import com.mercadolivre.projetointegrador.section.model.Section;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InboundOrder implements Serializable {

	private static final long serialVersionUID = 7379292671521538765L;

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long orderNumber;

	private LocalDate orderDate;

    @ManyToOne
    @JsonIgnoreProperties("inboundOrder")
    private Section section;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "inboundOrder")
    @JsonIgnoreProperties("inboundOrder")
    private List<Batch> batchStock;

}
