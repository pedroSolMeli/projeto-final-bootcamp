package com.mercadolivre.projetointegrador.section.repository;

import com.mercadolivre.projetointegrador.section.model.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("SectionRepository")
public interface SectionRepository extends JpaRepository<Section, Long> {

}
