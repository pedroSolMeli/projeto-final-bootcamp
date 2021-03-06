package com.mercadolivre.projetointegrador.user.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mercadolivre.projetointegrador.enums.UserRole;
import com.mercadolivre.projetointegrador.purchaseorder.model.PurchaseOrder;
import com.mercadolivre.projetointegrador.warehouse.model.Warehouse;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"cpf"}))
public class User implements Serializable {

    private static final long serialVersionUID = -83702385008718071L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false, length = 11)
    private String cpf;

    private String name;

    private String email;

    @Column(unique = true)
    private String username;

    private String password;

    @ElementCollection(targetClass = UserRole.class)
    @JoinTable(name = "userRole", joinColumns = @JoinColumn(name = "userId"))
    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private List<UserRole> roles;

    @ManyToOne
    @JsonIgnoreProperties("warehouse")
    private Warehouse warehouse;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "buyer")
    @JsonIgnoreProperties("buyer")
    private List<PurchaseOrder> orders;

}
