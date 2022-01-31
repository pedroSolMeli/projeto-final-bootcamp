package com.mercadolivre.projetointegrador.user.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.mercadolivre.projetointegrador.enums.UserRole;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder

@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "cpf" }))
public class User implements Serializable {


	private static final long serialVersionUID = -83702385008718071L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	
	//@CPF para usar no dto
	@Column(nullable = false, length = 11)
	private String cpf;
	
	private String name;
	
	//adicionar validação email no dto
	private String email;
	
	private String password;
 
	private UserRole userRole;

}
