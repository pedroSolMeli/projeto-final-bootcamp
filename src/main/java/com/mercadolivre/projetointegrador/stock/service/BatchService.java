package com.mercadolivre.projetointegrador.stock.service;

import com.mercadolivre.projetointegrador.stock.model.Batch;
import com.mercadolivre.projetointegrador.stock.repository.BatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class BatchService {

    @Autowired
    BatchRepository repository;

    public Batch createBatch(Batch batch){
        return repository.saveAndFlush(batch);
    }

    public List<Batch> findAllBatch(){
        return repository.findAll();
    }

    public Batch updateBatch(Batch batch){
        return repository.saveAndFlush(batch);
    }

    public void deleteBatch(Long id){
        getById(id);
        repository.deleteById(id);
    }

    public Batch getById(Long id){
        Batch batch = repository.findById(id).orElse(null);

        if(batch == null){
            ResponseStatusException responseStatusException = new ResponseStatusException(HttpStatus.NOT_FOUND, "Batch not found");
            throw responseStatusException;
        }

        return batch;
    }

}
