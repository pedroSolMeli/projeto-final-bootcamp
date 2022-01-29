package com.mercadolivre.projetointegrador.product.service;

import com.mercadolivre.projetointegrador.enums.ProductType;
import com.mercadolivre.projetointegrador.product.dto.ProductRequestDto;
import com.mercadolivre.projetointegrador.product.dto.ProductResponseDto;
import com.mercadolivre.projetointegrador.product.model.Product;
import com.mercadolivre.projetointegrador.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Qualifier("ProductRepository")
    @Autowired
    ProductRepository repository;

    public ProductResponseDto create(ProductRequestDto productRequestDto) {
        Product product = ConvertToObject(productRequestDto);
        Product result = repository.saveAndFlush(product);
        ProductResponseDto response = ConvertToResponseDto(result);
        return response;
    }

    public List<ProductResponseDto> findAllProducts() {
        List<Product> result = repository.findAll();
        List<ProductResponseDto> response = ConvertToResponseDto(result);
        return response;
    }

    private static Product ConvertToObject(ProductRequestDto dto) {
        ProductType productType = ProductType.ConvertToEnum(dto.getProductType());
        Product product = Product.builder()
                .name(dto.getName())
                .productType(productType)
                .price(dto.getPrice())
                .build();
        return product;
    }

    private static ProductResponseDto ConvertToResponseDto(Product result) {
        ProductResponseDto responseDto = ProductResponseDto.builder()
                .id(result.getId())
                .name(result.getName())
                .productType(result.getProductType())
                .price(result.getPrice())
                .build();
        return responseDto;
    }

    private static List<ProductResponseDto> ConvertToResponseDto(List<Product> productList) {
        if (productList == null)
            return new ArrayList<ProductResponseDto>();
        List<ProductResponseDto> productResponseDtoList = productList.stream().map(s -> ConvertToResponseDto(s)).collect(Collectors.toList());
        return productResponseDtoList;
    }


}
