package com.mercadolivre.projetointegrador.user.model;

import java.io.Serializable;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mercadolivre.projetointegrador.enums.UserRole;

import com.mercadolivre.projetointegrador.warehouse.controller.WarehouseController;
import com.mercadolivre.projetointegrador.warehouse.model.Warehouse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "cpf" }))
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
	private String login;

	private String password;
 
	private UserRole userRole;

	@ManyToOne
	@JsonIgnoreProperties("user")
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private Warehouse warehouse;

}
