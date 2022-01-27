package com.mercadolivre.projetointegrador.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mercadolivre.projetointegrador.model.Batch;


@Repository
public interface BatchRepository extends JpaRepository<Batch, Long>{

}
