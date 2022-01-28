package com.mercadolivre.projetointegrador.user.model;


import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable{
	
	private static final long serialVersionUID = -83702385008718071L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
//	cpf STRING
//	nome STRING
//	email STRING
//	senha STRING
//	tipo CHAR
//
//	enumTipoUser


}
