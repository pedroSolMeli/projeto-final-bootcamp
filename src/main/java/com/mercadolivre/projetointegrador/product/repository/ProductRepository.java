package com.mercadolivre.projetointegrador.product.repository;


import com.mercadolivre.projetointegrador.enums.ProductType;
import com.mercadolivre.projetointegrador.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("ProductRepository")
public interface ProductRepository extends JpaRepository<Product, Long> {

    Product getProductById(Long id);

    List<Product> getAllByProductType(Optional<ProductType> productType);
}
