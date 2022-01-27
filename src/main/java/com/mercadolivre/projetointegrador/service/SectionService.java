package com.mercadolivre.projetointegrador.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mercadolivre.projetointegrador.model.Section;
import com.mercadolivre.projetointegrador.model.Warehouse;
import com.mercadolivre.projetointegrador.repository.SectionRepository;

@Service
public class SectionService {
	
	@Autowired
	SectionRepository repository;

	public Section createSection(Section section) {
		return repository.saveAndFlush(section);
	}

}
