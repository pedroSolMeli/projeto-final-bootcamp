package com.mercadolivre.projetointegrador.purchaseProduct.service;

import com.mercadolivre.projetointegrador.batch.dto.UnavailableProductDto;
import com.mercadolivre.projetointegrador.batch.model.Batch;
import com.mercadolivre.projetointegrador.batch.service.BatchService;
import com.mercadolivre.projetointegrador.product.model.Product;
import com.mercadolivre.projetointegrador.product.service.ProductService;
import com.mercadolivre.projetointegrador.purchaseProduct.model.PurchaseProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class PurchaseProductService {

    @Autowired
    ProductService productService;

    @Autowired
    BatchService batchService;

    private void verifyProductInPurchase(List<PurchaseProduct> productsPurchase) {

        for (PurchaseProduct purchaseProduct : productsPurchase) {
            Long idProduct = purchaseProduct.getProduct().getId();
            Product product = productService.getProductById(idProduct);
            //TODO VERIFICAR VALIDAÇÃO DOS PRODUTOS EM ESTOQUE
            UnavailableProductDto unavailableProductDto = batchService.validateIfProductIsAvailableInStock(purchaseProduct.getQuantity(), product.getId());
            if (unavailableProductDto.equals(null)) {
                subtractProductFromStock(purchaseProduct);
            }

        }
    }
    //TODO TESTAR PRA VER SE TA FUNCIONANDO A REMOÇÃO DO ESTOQUE
    private void subtractProductFromStock(PurchaseProduct purchaseProduct) {
        Product product = purchaseProduct.getProduct();
        AtomicReference<Integer> quantity = new AtomicReference<>((int) 0);
        List<Batch> batchList = batchService.getBatchesByProductId(product.getId());
        while (quantity.get() > 0) {
            batchList.stream().forEach(batch -> {
                int diference = batch.getCurrentQuantity() - quantity.get();
                if (diference < 0) {
                    batch.setCurrentQuantity(0);
                    quantity.set(diference * (-1));
                    batchService.saveBatch(batch);
                } else {
                    batch.setCurrentQuantity(diference);
                    quantity.set(0);
                    batchService.saveBatch(batch);
                    return;
                }
            });
        }
    }
}
