package com.mercadolivre.projetointegrador.stock.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mercadolivre.projetointegrador.stock.model.Section;

@Repository
public interface SectionRepository extends JpaRepository<Section, Long>{

}
