package com.mercadolivre.projetointegrador.product.controller;

import com.mercadolivre.projetointegrador.product.dto.ProductRequestDto;
import com.mercadolivre.projetointegrador.product.dto.ProductResponseDto;
import com.mercadolivre.projetointegrador.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
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

}
