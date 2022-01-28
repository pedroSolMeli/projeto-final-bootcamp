package com.mercadolivre.projetointegrador.warehouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mercadolivre.projetointegrador.warehouse.model.Warehouse;


@Repository("WarehouseRepository")
public interface WarehouseRepository extends JpaRepository<Warehouse, Long>{

}
