package com.mercadolivre.projetointegrador.product.repository;


import com.mercadolivre.projetointegrador.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("ProductRepository")
public interface ProductRepository extends JpaRepository<Product, Long> {

    Product getProductById(Long id);
}
