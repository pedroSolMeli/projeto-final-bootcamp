package com.mercadolivre.projetointegrador.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mercadolivre.projetointegrador.model.Section;
import com.mercadolivre.projetointegrador.model.Warehouse;
import com.mercadolivre.projetointegrador.service.SectionService;

@RestController
@RequestMapping("/section")
public class SectionController {
	
	@Autowired
	SectionService service;
	
	@PostMapping()
    public ResponseEntity<?> create(@RequestBody Section section){
        Section result =service.createSection(section);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

}
