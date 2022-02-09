package com.mercadolivre.projetointegrador.product.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.mercadolivre.projetointegrador.security.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.mercadolivre.projetointegrador.enums.ProductType;
import com.mercadolivre.projetointegrador.product.dto.FindProductReponseDto;
import com.mercadolivre.projetointegrador.product.dto.ProductRequestDto;
import com.mercadolivre.projetointegrador.product.dto.ProductResponseDto;
import com.mercadolivre.projetointegrador.product.service.ProductService;

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
        if(result.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @GetMapping("/list")
    public ResponseEntity<?> findProductsAndBatchs(@RequestParam(name= "productId") Long productId,
                                                   @RequestParam(name= "orderBy", required = false) String orderBy,
                                                   @RequestHeader(value = "Authorization") String authHeader) {
        FindProductReponseDto productsAndBatchs = service.getProductsAndBatchs(productId, orderBy, authHeader);
        return new ResponseEntity<>(productsAndBatchs, HttpStatus.OK);
    }

}
