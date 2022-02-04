package com.mercadolivre.projetointegrador.product.service;

import com.mercadolivre.projetointegrador.batch.dto.BatchStockDto;
import com.mercadolivre.projetointegrador.batch.model.Batch;
import com.mercadolivre.projetointegrador.batch.service.BatchService;
import com.mercadolivre.projetointegrador.enums.ProductType;
import com.mercadolivre.projetointegrador.inboundorder.model.InboundOrder;
import com.mercadolivre.projetointegrador.inboundorder.service.InboundOrderService;
import com.mercadolivre.projetointegrador.product.dto.FindProductReponseDto;
import com.mercadolivre.projetointegrador.product.dto.ProductRequestDto;
import com.mercadolivre.projetointegrador.product.dto.ProductResponseDto;
import com.mercadolivre.projetointegrador.product.model.Product;
import com.mercadolivre.projetointegrador.product.repository.ProductRepository;
import com.mercadolivre.projetointegrador.section.dto.SectionDto;
import com.mercadolivre.projetointegrador.section.model.Section;
import com.mercadolivre.projetointegrador.warehouse.model.Warehouse;
import com.mercadolivre.projetointegrador.warehouse.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Qualifier("ProductRepository")
    @Autowired
    ProductRepository repository;

    @Autowired
    WarehouseService warehouseService;

    public ProductResponseDto create(ProductRequestDto productRequestDto) {
        Product product = ConvertToObject(productRequestDto);
        Product result = repository.saveAndFlush(product);
        ProductResponseDto response = ConvertToResponseDto(result);
        return response;
    }

    public List<ProductResponseDto> findAllProducts(Optional<ProductType> type) {
        if(!type.isPresent()) {
            List<Product> result = repository.findAll();
            List<ProductResponseDto> response = ConvertToResponseDto(result);
            return response;
        }

        List<Product> result = repository.getAllByProductType(type);
        List<ProductResponseDto> response = ConvertToResponseDto(result);
        return response;
    }

    public static Product ConvertToObject(ProductRequestDto dto) {
        ProductType productType = ProductType.ConvertToEnum(dto.getProductType());
        Product product = Product.builder()
                .name(dto.getName())
                .productType(productType)
                .price(dto.getPrice())
                .build();
        return product;
    }

    public static ProductResponseDto ConvertToResponseDto(Product result) {
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

    public Product getProductById(Long id) {
        Product product = repository.getProductById(id);
        if (product == null) {
            ResponseStatusException responseStatusException = new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
            throw responseStatusException;
        }
        return product;
    }

    public FindProductReponseDto getProductsAndBatchs(Long productId, String orderBy){
        //Todo - pegar o codigo armazen do representante
        Warehouse warehouse = warehouseService.getWarehouseById(2l);
        List<Section> sections = warehouse.getSections();

        List<Batch> listBatch = new ArrayList<>();
        Section sectionOp = null;

        for (Section section : sections){
            List<InboundOrder> inboundOrders = section.getInboundOrder();
            for (InboundOrder inbound: inboundOrders) {
                List<Batch> batchStocks = inbound.getBatchStock();
                for (Batch batch: batchStocks) {
                    Long id = batch.getProduct().getId();
                    if (productId == id){
                        if (isDueDateValid(batch.getDueDate())) {
                            sectionOp = section;
                            listBatch.add(batch);
                        }
                    }
                }
            }
        }

        if(listBatch.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found a valid product with id: " + productId);
        }

        SectionDto sectionDto = SectionDto.builder().sectionCode(sectionOp.getCode()).warehouseCode(sectionOp.getWarehouse().getCode()).build();
        List<BatchStockDto> batchStockDtos = BatchService.ConvertToListBatchStockDto(listBatch);

        List<BatchStockDto> batchStock = orderBy !=  null ? sortList(orderBy, batchStockDtos) : batchStockDtos;

        FindProductReponseDto findProductReponseDto = FindProductReponseDto.builder().sectionDto(sectionDto).productId(productId).batchStockDto(batchStock).build();
        return  findProductReponseDto;
    }

    public List<BatchStockDto> orderByLote(List<BatchStockDto> batchStockDtos){
        batchStockDtos.sort(Comparator.comparing(BatchStockDto::getBatchNumber));
        return batchStockDtos;
    }

    public List<BatchStockDto> orderByCurrentQuantity(List<BatchStockDto> batchStockDtos){
        batchStockDtos.sort(Comparator.comparing(BatchStockDto::getCurrentQuantity));
        return batchStockDtos;
    }

    public List<BatchStockDto> orderByDueDate(List<BatchStockDto> batchStockDtos){
        batchStockDtos.sort(Comparator.comparing(BatchStockDto::getDueDate));
        return batchStockDtos;
    }

    public  List<BatchStockDto> sortList(String paramOrder, List<BatchStockDto>  batchStockDtos) {

        List<BatchStockDto> orderList;

        switch (paramOrder) {
            case "L":
            case"l":
                orderList = orderByLote(batchStockDtos);
                break;
            case "C":
            case"c":
                orderList = orderByCurrentQuantity(batchStockDtos);
                break;
            case "F":
            case "f":
                orderList = orderByDueDate(batchStockDtos);
                break;
            default:
                orderList = batchStockDtos;
        }

        return orderList;
    }

    public boolean isDueDateValid(LocalDate dueDate){
        Period diff = Period.between(LocalDate.now(), dueDate);

        if (dueDate.isAfter(LocalDate.now())) {
            if ((diff.getMonths() == 0 && diff.getDays() > 21) || diff.getMonths() > 0) {
                return true;
            }
        }

        return false;
    }

}