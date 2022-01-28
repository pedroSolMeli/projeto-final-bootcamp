package com.mercadolivre.projetointegrador.product.repository;

import com.mercadolivre.projetointegrador.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
