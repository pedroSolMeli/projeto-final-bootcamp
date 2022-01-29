package com.mercadolivre.projetointegrador.batch.service;

import com.mercadolivre.projetointegrador.batch.model.Batch;
import com.mercadolivre.projetointegrador.batch.repository.BatchRepository;
import com.mercadolivre.projetointegrador.product.model.Product;
import com.mercadolivre.projetointegrador.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class BatchService {

    @Qualifier("BatchRepository")
    @Autowired
    BatchRepository repository;

    @Autowired
    ProductService productService;

    public Batch createBatch(Batch batch) {
        //Product product = productService.getProductById(batch.getProductId().getId());

        return repository.saveAndFlush(batch);
    }

    public List<Batch> findAllBatch() {
        return repository.findAll();
    }

    public Batch updateBatch(Batch batch) {
        return repository.saveAndFlush(batch);
    }

    public void deleteBatch(Long id) {
        getById(id);
        repository.deleteById(id);
    }

    public Batch getById(Long id) {
        Batch batch = repository.findById(id).orElse(null);

        if (batch == null) {
            ResponseStatusException responseStatusException = new ResponseStatusException(HttpStatus.NOT_FOUND, "Batch not found");
            throw responseStatusException;
        }

        return batch;
    }

}
