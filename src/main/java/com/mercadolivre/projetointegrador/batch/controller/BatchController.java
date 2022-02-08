package com.mercadolivre.projetointegrador.batch.controller;

import com.mercadolivre.projetointegrador.batch.dto.BatchRequestDto;
import com.mercadolivre.projetointegrador.batch.dto.BatchResponseDateLimitDto;
import com.mercadolivre.projetointegrador.batch.dto.BatchResponseDto;
import com.mercadolivre.projetointegrador.batch.model.Batch;
import com.mercadolivre.projetointegrador.batch.service.BatchService;
import com.mercadolivre.projetointegrador.enums.ProductType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController("BatchController")
@RequestMapping("/batch")
public class BatchController {

    @Autowired
    BatchService service;

    @PostMapping()
    public ResponseEntity<?> create(@Valid @RequestBody BatchRequestDto batchRequestDto){
        BatchResponseDto result = service.createBatch(batchRequestDto);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> findAll(){
        List<BatchResponseDto> result = service.findAllBatch();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/duedate")
    public ResponseEntity<?> findAlByDueDate(@RequestParam(required = false) Long sectionId, @RequestParam int numberOfDays, @RequestParam(required = false) ProductType category){
        List<BatchResponseDateLimitDto> result = service.findAllBatchBySectionAndDateLimit(sectionId, numberOfDays, category);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity<?> update(@RequestBody Batch batch){
        Batch result = service.updateBatch(batch);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

}
