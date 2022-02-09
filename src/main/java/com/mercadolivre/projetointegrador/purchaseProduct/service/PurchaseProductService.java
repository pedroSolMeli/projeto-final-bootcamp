package com.mercadolivre.projetointegrador.purchaseProduct.service;

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

    //TODO TESTAR PRA VER SE TA FUNCIONANDO A REMOÇÃO DO ESTOQUE
    public void subtractProductFromStock(PurchaseProduct purchaseProduct) {
        Product product = purchaseProduct.getProduct();
        AtomicReference<Integer> quantity = new AtomicReference<>((int) purchaseProduct.getQuantity());
        List<Batch> batchList = batchService.getBatchesByProductId(product.getId());
        while (quantity.get() > 0) {
            batchList.stream().forEach(batch -> {
                int diference = batch.getCurrentQuantity() - purchaseProduct.getQuantity();
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
