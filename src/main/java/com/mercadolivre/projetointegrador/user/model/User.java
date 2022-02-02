package com.mercadolivre.projetointegrador.user.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mercadolivre.projetointegrador.batch.model.Batch;
import com.mercadolivre.projetointegrador.enums.UserRole;
import com.mercadolivre.projetointegrador.purchaseorder.model.PurchaseOrder;

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
	
	private String password;
 
	private UserRole userRole;
	
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "buyerId")
    @JsonIgnoreProperties("buyerId")
    private List<PurchaseOrder> orders;


}
