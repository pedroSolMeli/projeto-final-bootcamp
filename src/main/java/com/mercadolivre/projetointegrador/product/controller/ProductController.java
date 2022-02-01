package com.mercadolivre.projetointegrador.product.controller;

import com.mercadolivre.projetointegrador.enums.ProductType;
import com.mercadolivre.projetointegrador.product.dto.ProductRequestDto;
import com.mercadolivre.projetointegrador.product.dto.ProductResponseDto;
import com.mercadolivre.projetointegrador.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

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
    //TODO refatorar type para aceitar lower case
    public ResponseEntity<?> findAll(@RequestParam(required = false) Optional<ProductType> type) {
        List<ProductResponseDto> result = service.findAllProducts(type);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
