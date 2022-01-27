package com.mercadolivre.projetointegrador.service;

import com.mercadolivre.projetointegrador.model.Section;
import com.mercadolivre.projetointegrador.repository.SectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SectionService {

    @Autowired
    SectionRepository repository;

    public Section createSection(Section section) {
        return repository.saveAndFlush(section);
    }

    public List<Section> findAllSections() {
        return repository.findAll();
    }
}
