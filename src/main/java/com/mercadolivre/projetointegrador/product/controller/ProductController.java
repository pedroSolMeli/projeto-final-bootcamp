package com.mercadolivre.projetointegrador.product.controller;

import com.mercadolivre.projetointegrador.batch.model.Batch;
import com.mercadolivre.projetointegrador.product.dto.FindProductReponseDto;
import com.mercadolivre.projetointegrador.product.dto.ProductRequestDto;
import com.mercadolivre.projetointegrador.product.dto.ProductResponseDto;
import com.mercadolivre.projetointegrador.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController("ProductController")
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService service;

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody ProductRequestDto productRequestDto) {
        ProductResponseDto result = service.create(productRequestDto);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> finAll() {
        List<ProductResponseDto> result = service.findAllProducts();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<?> findProductsAndBatchs(@RequestParam(name= "productId") Long productId,
                                                   @RequestParam(name= "orderBy", required = false) String orderBy) {
        FindProductReponseDto productsAndBatchs = service.getProductsAndBatchs(productId, orderBy);
        return new ResponseEntity<>(productsAndBatchs, HttpStatus.OK);
    }
}
