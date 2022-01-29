package com.mercadolivre.projetointegrador.batch.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.mercadolivre.projetointegrador.batch.model.Batch;
import com.mercadolivre.projetointegrador.batch.service.BatchService;

import javax.validation.Valid;
import java.util.List;

@RestController("BatchController")
@RequestMapping("/batch")
public class BatchController {

    @Autowired
    BatchService service;

    @PostMapping()
    public ResponseEntity<?> create(@RequestBody Batch batch){
        Batch result = service.createBatch(batch);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> findAll(){
        List<Batch> result = service.findAllBatch();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity<?> update(@RequestBody Batch batch){
        Batch result = service.updateBatch(batch);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }


}
