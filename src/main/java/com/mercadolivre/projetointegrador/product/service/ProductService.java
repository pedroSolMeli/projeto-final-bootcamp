package com.mercadolivre.projetointegrador.product.service;

import com.mercadolivre.projetointegrador.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Qualifier("ProductRepository")
    @Autowired
    ProductRepository productRepository;
}
