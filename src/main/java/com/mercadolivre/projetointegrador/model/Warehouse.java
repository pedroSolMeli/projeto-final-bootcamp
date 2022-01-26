package com.mercadolivre.projetointegrador.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Warehouse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;

    @OneToMany
    private List<Section> sections;

}
