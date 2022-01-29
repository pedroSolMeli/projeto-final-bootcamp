package com.mercadolivre.projetointegrador.section.controller;

import com.mercadolivre.projetointegrador.section.dto.SectionRequestDto;
import com.mercadolivre.projetointegrador.section.dto.SectionResponseDto;
import com.mercadolivre.projetointegrador.section.model.Section;
import com.mercadolivre.projetointegrador.section.service.SectionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("SectionController")
@RequestMapping("/section")
public class SectionController {

    @Autowired
    SectionService service;

    @PostMapping()
    public ResponseEntity<?> create(@RequestBody SectionRequestDto sectionRequestDto) {
        SectionResponseDto result = service.createSection(sectionRequestDto);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<?> findAll() {
        List<Section> result = service.findAllSections();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
