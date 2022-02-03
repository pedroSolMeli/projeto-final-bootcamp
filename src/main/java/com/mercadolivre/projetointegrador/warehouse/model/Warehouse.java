package com.mercadolivre.projetointegrador.warehouse.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mercadolivre.projetointegrador.section.model.Section;

import com.mercadolivre.projetointegrador.user.model.User;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Warehouse implements Serializable {

    private static final long serialVersionUID = 7463206541614851461L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String code;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "warehouse")
    @JsonIgnoreProperties("warehouse")
    private List<Section> sections;

    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "warehouse")
    @JsonIgnoreProperties("User")
    private List<User> users;

}
