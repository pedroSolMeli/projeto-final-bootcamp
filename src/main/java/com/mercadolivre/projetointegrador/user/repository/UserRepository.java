package com.mercadolivre.projetointegrador.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mercadolivre.projetointegrador.product.model.Product;
import com.mercadolivre.projetointegrador.user.model.User;

@Repository("UserRepository")
public interface UserRepository extends JpaRepository<User, Long>{
	
	User getUserById(Long id);

}
