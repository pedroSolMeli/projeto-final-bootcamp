package com.mercadolivre.projetointegrador.batch.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mercadolivre.projetointegrador.batch.dto.BatchResponseDateLimitDto;
import com.mercadolivre.projetointegrador.batch.dto.BatchResponseDto;
import com.mercadolivre.projetointegrador.batch.service.BatchService;
import com.mercadolivre.projetointegrador.enums.ProductType;

@RestController("BatchController")
@RequestMapping("/batch")
public class BatchController {

    @Autowired
    BatchService service;


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

}
