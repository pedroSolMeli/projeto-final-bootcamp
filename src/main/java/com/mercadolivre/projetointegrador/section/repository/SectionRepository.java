package com.mercadolivre.projetointegrador.section.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mercadolivre.projetointegrador.section.model.Section;

@Repository("SectionRepository")
public interface SectionRepository extends JpaRepository<Section, Long>{

}
