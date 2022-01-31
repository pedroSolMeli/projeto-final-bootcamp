package com.mercadolivre.projetointegrador.section.repository;

import com.mercadolivre.projetointegrador.section.model.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("SectionRepository")
public interface SectionRepository extends JpaRepository<Section, Long> {

    Section getSectionByCode(String code);

    Optional<Section> findSectionByCode(String code);
}
