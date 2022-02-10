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

    public void subtractProductFromStock(PurchaseProduct purchaseProduct) {
        Product product = purchaseProduct.getProduct();
        int quantity = purchaseProduct.getQuantity();
        List<Batch> batchList = batchService.getBatchesByProductId(product.getId());
        while (quantity > 0) {
            for (int i = 0; i < batchList.size() ; i++) {
                int diference = batchList.get(i).getCurrentQuantity() - quantity;
                if (diference < 0) {
                    batchList.get(i).setCurrentQuantity(0);
                    quantity = diference * (-1);
                    batchService.saveBatch(batchList.get(i));
                } else {
                    batchList.get(i).setCurrentQuantity(diference);
                    quantity = 0;
                    batchService.saveBatch(batchList.get(i));
                    break;
                }
            }
        }
    }
}
