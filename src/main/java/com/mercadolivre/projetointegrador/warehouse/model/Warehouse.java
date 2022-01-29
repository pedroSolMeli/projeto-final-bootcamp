package com.mercadolivre.projetointegrador.warehouse.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mercadolivre.projetointegrador.section.model.Section;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "warehouse")
public class Warehouse implements Serializable {

    private static final long serialVersionUID = 7463206541614851461L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String code;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "warehouseCode")
    @JsonIgnoreProperties("warehouseCode")
    private List<Section> sections;

    @Override
    public String toString() {
        return "Warehouse{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", sections=" + sections +
                '}';
    }
}
