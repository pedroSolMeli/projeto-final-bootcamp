package com.mercadolivre.projetointegrador.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mercadolivre.projetointegrador.model.Warehouse;


@Repository
public interface WareouseRepository extends JpaRepository<Warehouse, Long>{

}
