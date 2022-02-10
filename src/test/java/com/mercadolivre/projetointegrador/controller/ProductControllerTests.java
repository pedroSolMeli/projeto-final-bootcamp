package com.mercadolivre.projetointegrador.controller;


import com.mercadolivre.projetointegrador.enums.ProductType;
import com.mercadolivre.projetointegrador.product.dto.ProductRequestDto;
import com.mercadolivre.projetointegrador.product.dto.ProductResponseDto;
import com.mercadolivre.projetointegrador.product.model.Product;
import com.mercadolivre.projetointegrador.product.repository.ProductRepository;
import com.mercadolivre.projetointegrador.product.service.ProductService;
import com.mercadolivre.projetointegrador.security.JwtProvider;
import com.mercadolivre.projetointegrador.user.dto.UserDto;
import com.mercadolivre.projetointegrador.warehouse.model.Warehouse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ProductControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    ProductService productService;

    @Mock
    ProductRepository productRepository;

    @Test
    public void shouldCreateProduct(){
        Product product = Product.builder()
                .name("batata")
                .productType(ProductType.REFRIGERATED)
                .price(BigDecimal.valueOf(10))
                .build();

        ProductRequestDto request = ProductRequestDto.builder()
                .name("batata")
                .productType("refrigerated")
                .price(BigDecimal.valueOf(10))
                .build();


        Mockito.when(productRepository.saveAndFlush(product)).thenReturn(product);
        ProductResponseDto response = productService.create(request);

        Assertions.assertEquals(response.getName(), product.getName());
        Assertions.assertEquals(response.getProductType(), product.getProductType());
        Assertions.assertEquals(response.getPrice(), product.getPrice());
    }

    @Test
    public void shouldReturnProductListWithSucces() {
        List<Product> productList = new ArrayList<>();
        Product product = Product.builder().id(1l).productType(ProductType.REFRIGERATED).name("batata").price(BigDecimal.valueOf(10)).build();
        productList.add(product);

        Mockito.when(productRepository.findAll()).thenReturn(productList);
    }
}
